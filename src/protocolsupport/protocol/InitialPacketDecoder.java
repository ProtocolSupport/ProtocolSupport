package protocolsupport.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.CorruptedFrameException;

import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import protocolsupport.injector.ProtocolLibFixer;
import protocolsupport.protocol.DataStorage.ProtocolVersion;
import protocolsupport.protocol.IPipeLineBuilder.DecoderEncoderTuple;

public class InitialPacketDecoder extends ChannelInboundHandlerAdapter {

	private final Timer pingTimeout = new Timer();

	@Override
	public void channelRead(final ChannelHandlerContext ctx, final Object inputObj) throws Exception {
		try {
			final ByteBuf input = (ByteBuf) inputObj;
			if (!input.isReadable()) {
				return;
			}
			//detect protocol
			ProtocolVersion handshakeversion = ProtocolVersion.UNKNOWN;
			//reset reader index to 0
			input.readerIndex(0);
			int firstbyte = input.readUnsignedByte();
			switch (firstbyte) {
				case 0xFE: { //1.6 ping or 1.5 ping (should we check if FE is actually a part of a varint length?)
					try {
						if (input.readUnsignedByte() == 1) {
							if (input.readableBytes() == 0) {
								//1.5.2 or maybe we still didn't receive it all
								pingTimeout.schedule(new TimerTask() {
									@Override
									public void run() {
										try {
											SocketAddress remoteAddress = ctx.channel().remoteAddress();
											if (DataStorage.getVersion(remoteAddress) == ProtocolVersion.UNKNOWN) {
												DataStorage.setVersion(remoteAddress, ProtocolVersion.MINECRAFT_1_5_2);
												rebuildPipeLine(ctx, input, ProtocolVersion.MINECRAFT_1_5_2);
											}
										} catch (Throwable t) {
											ctx.channel().close();
										}
									}
								}, 500);
							} else if (
									(input.readUnsignedByte() == 0xFA) &&
									"MC|PingHost".equals(new String(input.readBytes(input.readUnsignedShort() * 2).array(), StandardCharsets.UTF_16BE))
									) { //1.6.*
								input.readUnsignedShort();
								handshakeversion = ProtocolVersion.fromId(input.readUnsignedByte());
							}
						}
					} catch (IndexOutOfBoundsException ex) {
					}
					break;
				}
				case 0x02: { //1.6 or 1.5.2 handshake
					try {
						handshakeversion = ProtocolVersion.fromId(input.readUnsignedByte());
					} catch (IndexOutOfBoundsException ex) {
					}
					break;
				}
				default: { //1.7 or 1.8 handshake
					input.readerIndex(0);
					ByteBuf data = getVarIntPrefixedData(input);
					if (data != null) {
						handshakeversion = read1_7_1_8Handshake(data);
					}
					break;
				}
			}
			//if we detected the protocol than we save it and process data
			if (handshakeversion != ProtocolVersion.UNKNOWN) {
				System.out.println(ctx.channel().remoteAddress()+" connected with protocol version "+handshakeversion);
				DataStorage.setVersion(ctx.channel().remoteAddress(), handshakeversion);
				rebuildPipeLine(ctx, input, handshakeversion);
			}
		} catch (Throwable t) {
			t.printStackTrace();
			ctx.channel().close();
		}
	}

	@SuppressWarnings("serial")
	private static HashMap<ProtocolVersion, IPipeLineBuilder> pipelineBuilders = new HashMap<ProtocolVersion, IPipeLineBuilder>() {{
		put(ProtocolVersion.MINECRAFT_1_8, new protocolsupport.protocol.v_1_8.PipeLineBuilder());
		put(ProtocolVersion.MINECRAFT_1_7_10, new protocolsupport.protocol.v_1_7.PipeLineBuilder());
		put(ProtocolVersion.MINECRAFT_1_7_5, new protocolsupport.protocol.v_1_7.PipeLineBuilder());
		put(ProtocolVersion.MINECRAFT_1_6_4, new protocolsupport.protocol.v_1_6.PipeLineBuilder());
		put(ProtocolVersion.MINECRAFT_1_6_2, new protocolsupport.protocol.v_1_6.PipeLineBuilder());
		put(ProtocolVersion.MINECRAFT_1_5_2, new protocolsupport.protocol.v_1_5.PipeLineBuilder());
	}};

	private void rebuildPipeLine(final ChannelHandlerContext ctx, final ByteBuf input, ProtocolVersion version) throws Exception {
		ctx.channel().pipeline().remove(InitialPacketDecoder.class);
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
