package protocolsupport.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.util.ReferenceCountUtil;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.server.ServerListPingEvent;

import protocolsupport.injector.ProtocolLibFixer;
import protocolsupport.protocol.IPipeLineBuilder.DecoderEncoderTuple;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.protocol.storage.ProtocolStorage.ProtocolVersion;

public class InitialPacketDecoder extends ChannelInboundHandlerAdapter {

	private final Timer pingTimeout = new Timer();

	private ByteBuf receivedData = Unpooled.buffer();

	@Override
	public void channelRead(final ChannelHandlerContext ctx, final Object inputObj) throws Exception {
		try {
			final ByteBuf input = (ByteBuf) inputObj;
			if (!input.isReadable()) {
				return;
			}
			receivedData.writeBytes(input.readBytes(input.readableBytes()));
			//detect protocol
			ProtocolVersion handshakeversion = ProtocolVersion.UNKNOWN;
			//reset reader index to 0
			receivedData.readerIndex(0);
			int firstbyte = receivedData.readUnsignedByte();
			switch (firstbyte) {
				case 0xFE: { //1.6 ping or 1.5 ping (should we check if FE is actually a part of a varint length?)
					try {
						if (receivedData.readableBytes() == 0) { //really old protocol
							pingTimeout.schedule(new TimerTask() {
								@Override
								public void run() {
									try {
										SocketAddress remoteAddress = ctx.channel().remoteAddress();
										if (ProtocolStorage.getVersion(remoteAddress) == ProtocolVersion.UNKNOWN) {
											System.out.println(remoteAddress + " pinged with a really outdated protocol");
											@SuppressWarnings("deprecation")
											ServerListPingEvent event = new ServerListPingEvent(
												((InetSocketAddress) remoteAddress).getAddress(),
												Bukkit.getMotd(), Bukkit.getOnlinePlayers().length,
												Bukkit.getMaxPlayers()
											);
											Bukkit.getPluginManager().callEvent(event);
											String response = ChatColor.stripColor(event.getMotd())+"§"+event.getNumPlayers()+"§"+event.getMaxPlayers();
											ByteBuf buf = Unpooled.buffer();
											buf.writeByte(255);
											buf.writeShort(response.length());
											buf.writeBytes(response.getBytes(StandardCharsets.UTF_16BE));
											ctx.pipeline().firstContext().writeAndFlush(buf);
										}
									} catch (Throwable t) {
										ctx.channel().close();
									}
								}
							}, 1000);
						} else if (receivedData.readUnsignedByte() == 1) {
							if (receivedData.readableBytes() == 0) {
								//1.5.2 or maybe we still didn't receive it all
								pingTimeout.schedule(new TimerTask() {
									@Override
									public void run() {
										try {
											SocketAddress remoteAddress = ctx.channel().remoteAddress();
											if (ProtocolStorage.getVersion(remoteAddress) == ProtocolVersion.UNKNOWN) {
												setProtocol(ctx, receivedData, ProtocolVersion.MINECRAFT_1_5_2);
											}
										} catch (Throwable t) {
											ctx.channel().close();
										}
									}
								}, 500);
							} else if (
								(receivedData.readUnsignedByte() == 0xFA) &&
								"MC|PingHost".equals(new String(receivedData.readBytes(receivedData.readUnsignedShort() * 2).array(), StandardCharsets.UTF_16BE))
							) { //1.6.*
								receivedData.readUnsignedShort();
								handshakeversion = ProtocolVersion.fromId(receivedData.readUnsignedByte());
							}
						}
					} catch (IndexOutOfBoundsException ex) {
					}
					break;
				}
				case 0x02: { //1.6 or 1.5.2 handshake
					try {
						handshakeversion = ProtocolVersion.fromId(receivedData.readUnsignedByte());
					} catch (IndexOutOfBoundsException ex) {
					}
					break;
				}
				default: { //1.7 or 1.8 handshake
					receivedData.readerIndex(0);
					ByteBuf data = getVarIntPrefixedData(receivedData);
					if (data != null) {
						handshakeversion = read1_7_1_8Handshake(data);
					}
					break;
				}
			}
			//if we detected the protocol than we save it and process data
			if (handshakeversion != ProtocolVersion.UNKNOWN) {
				setProtocol(ctx, receivedData, handshakeversion);
			}
		} catch (Throwable t) {
			ctx.channel().close();
		} finally {
			ReferenceCountUtil.release(inputObj);
		}
	}

	private void setProtocol(final ChannelHandlerContext ctx, final ByteBuf input, ProtocolVersion version) throws Exception {
		System.out.println(ctx.channel().remoteAddress()+" connected with protocol version "+version);
		ProtocolStorage.setVersion(ctx.channel().remoteAddress(), version);
		rebuildPipeLine(ctx, receivedData, version);
	}

	@SuppressWarnings("serial")
	private static EnumMap<ProtocolVersion, IPipeLineBuilder> pipelineBuilders = new EnumMap<ProtocolVersion, IPipeLineBuilder>(ProtocolVersion.class) {{
		put(ProtocolVersion.MINECRAFT_1_8, new protocolsupport.protocol.v_1_8.PipeLineBuilder());
		put(ProtocolVersion.MINECRAFT_1_7_10, new protocolsupport.protocol.v_1_7.PipeLineBuilder());
		put(ProtocolVersion.MINECRAFT_1_7_5, new protocolsupport.protocol.v_1_7.PipeLineBuilder());
		put(ProtocolVersion.MINECRAFT_1_6_4, new protocolsupport.protocol.v_1_6.PipeLineBuilder());
		put(ProtocolVersion.MINECRAFT_1_6_2, new protocolsupport.protocol.v_1_6.PipeLineBuilder());
		put(ProtocolVersion.MINECRAFT_1_5_2, new protocolsupport.protocol.v_1_5.PipeLineBuilder());
	}};

	private void rebuildPipeLine(final ChannelHandlerContext ctx, final ByteBuf input, ProtocolVersion version) throws Exception {
		ctx.channel().pipeline().remove(ChannelHandlers.INITIAL_DECODER);
		DecoderEncoderTuple tuple = pipelineBuilders.get(version).buildPipeLine(ctx);
		ProtocolLibFixer.fixProtocolLib(ctx.channel().pipeline(), tuple.getDecoder(), tuple.getEncoder());
		//set reader index to 0
		input.readerIndex(0);
		//fire data read
		ctx.channel().pipeline().firstContext().fireChannelRead(input);
	}

	private ByteBuf getVarIntPrefixedData(final ByteBuf byteBuf) {
		final byte[] array = new byte[3];
		for (int i = 0; i < array.length; ++i) {
			if (!byteBuf.isReadable()) {
				return null;
			}
			array[i] = byteBuf.readByte();
			if (array[i] >= 0) {
				final int length = readVarInt(Unpooled.wrappedBuffer(array));
				if (byteBuf.readableBytes() < length) {
					return null;
				}
				return byteBuf.readBytes(length);
			}
		}
		throw new CorruptedFrameException("Packet length is wider than 21 bit");
	}

	private ProtocolVersion read1_7_1_8Handshake(ByteBuf data) {
		if (readVarInt(data) == 0x00) {
			return ProtocolVersion.fromId(readVarInt(data));
		}
		return ProtocolVersion.UNKNOWN;
	}

	private int readVarInt(ByteBuf data) {
		int value = 0;
		int length = 0;
		byte b0;
		do {
			b0 = data.readByte();
			value |= (b0 & 0x7F) << (length++ * 7);
			if (length > 5) {
				throw new RuntimeException("VarInt too big");
			}
		} while ((b0 & 0x80) == 0x80);
		return value;
	}

}
