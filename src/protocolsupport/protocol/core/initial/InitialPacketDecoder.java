package protocolsupport.protocol.core.initial;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Future;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.core.ChannelHandlers;
import protocolsupport.protocol.core.IPipeLineBuilder;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.utils.Utils;
import protocolsupport.utils.Utils.Converter;
import protocolsupport.utils.netty.ChannelUtils;
import protocolsupport.utils.netty.ReplayingDecoderBuffer;
import protocolsupport.utils.netty.ReplayingDecoderBuffer.EOFSignal;

public class InitialPacketDecoder extends SimpleChannelInboundHandler<ByteBuf> {

	private static final int ping152delay = Utils.getJavaPropertyValue("protocolsupport.ping152delay", 500, Converter.STRING_TO_INT);
	private static final int pingLegacyDelay = Utils.getJavaPropertyValue("protocolsupport.pinglegacydelay", 1000, Converter.STRING_TO_INT);

	public static void init() {
		ProtocolSupport.logInfo("Assume 1.5.2 ping delay: "+ping152delay);
		ProtocolSupport.logInfo("Assume legacy ping dealy: "+pingLegacyDelay);
	}

	private static final EnumMap<ProtocolVersion, IPipeLineBuilder> pipelineBuilders = new EnumMap<ProtocolVersion, IPipeLineBuilder>(ProtocolVersion.class);
	static {
		IPipeLineBuilder builder = new protocolsupport.protocol.transformer.v_1_8.PipeLineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_FUTURE, builder);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_8, builder);
		IPipeLineBuilder builder17 = new protocolsupport.protocol.transformer.v_1_7.PipeLineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_7_10, builder17);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_7_5, builder17);
		IPipeLineBuilder builder16 = new protocolsupport.protocol.transformer.v_1_6.PipeLineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_6_4, builder16);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_6_2, builder16);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_6_1, builder16);
		IPipeLineBuilder builder15 = new protocolsupport.protocol.transformer.v_1_5.PipeLineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_5_2, builder15);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_5_1, builder15);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_4_7, new protocolsupport.protocol.transformer.v_1_4.PipeLineBuilder());
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_LEGACY, new protocolsupport.protocol.transformer.v_legacy.PipeLineBuilder());
	}

	protected final ByteBuf receivedData = Unpooled.buffer();
	protected final ReplayingDecoderBuffer replayingBuffer = new ReplayingDecoderBuffer(receivedData);

	protected Future<?> responseTask;

	protected void scheduleTask(ChannelHandlerContext ctx, Runnable task, long delay, TimeUnit tu) {
		cancelTask();
		responseTask = ctx.executor().schedule(task, delay, tu);
	}

	protected void cancelTask() {
		if (responseTask != null) {
			responseTask.cancel(true);
			responseTask = null;
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		cancelTask();
		super.channelInactive(ctx);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception  {
		cancelTask();
		super.handlerRemoved(ctx);
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
		if (!buf.isReadable()) {
			return;
		}
		receivedData.writeBytes(buf);
		receivedData.readerIndex(0);
		final Channel channel = ctx.channel();
		if (MinecraftServer.getServer().isDebugging()) {
			System.out.println(
				ChannelUtils.getNetworkManagerSocketAddress(channel) +
				" channel pipeline: " +
				channel.pipeline().toMap()
			);
			System.out.println(
				ChannelUtils.getNetworkManagerSocketAddress(channel) +
				" data: " +
				Arrays.toString(Arrays.copyOf(receivedData.array(), receivedData.readableBytes()))
			);
		}
		ProtocolVersion handshakeversion = null;
		int firstbyte = replayingBuffer.readUnsignedByte();
		switch (firstbyte) {
			case 0xFE: { //old ping or a part of varint length
				try {
					if (replayingBuffer.readableBytes() == 0) {
						//no more data received, it may be old protocol, or we just not received all data yet, so delay assuming as really old protocol for some time
						if (MinecraftServer.getServer().isDebugging()) {
							System.out.println(
								ChannelUtils.getNetworkManagerSocketAddress(channel) +
								" is probably 1.1"
							);
						}
						scheduleTask(ctx, new SetProtocolTask(this, channel, ProtocolVersion.MINECRAFT_LEGACY), pingLegacyDelay, TimeUnit.MILLISECONDS);
					} else if (replayingBuffer.readUnsignedByte() == 1) {
						//1.5-1.6 ping or maybe a finishing byte for 1.7+ packet length
						if (replayingBuffer.readableBytes() == 0) {
							//no more data received, it may be 1.5.2 or we just didn't receive 1.6 or 1.7+ data yet, so delay assuming as 1.5.2 for some time
							if (MinecraftServer.getServer().isDebugging()) {
								System.out.println(
									ChannelUtils.getNetworkManagerSocketAddress(channel) +
									" is probably 1.5.2"
								);
							}
							scheduleTask(ctx, new SetProtocolTask(this, channel, ProtocolVersion.MINECRAFT_1_5_2), ping152delay, TimeUnit.MILLISECONDS);
						} else if (
							(replayingBuffer.readUnsignedByte() == 0xFA) &&
							"MC|PingHost".equals(new String(ChannelUtils.toArray(replayingBuffer.readBytes(replayingBuffer.readUnsignedShort() * 2)), StandardCharsets.UTF_16BE))
						) {
							//definitely 1.6
							if (MinecraftServer.getServer().isDebugging()) {
								System.out.println(
									ChannelUtils.getNetworkManagerSocketAddress(channel) +
									" is 1.6"
								);
							}
							replayingBuffer.readUnsignedShort();
							handshakeversion = ProtocolUtils.get16PingVersion(replayingBuffer.readUnsignedByte());
						} else {
							//it was 1.7+ handshake after all
							//hope that there won't be any handshake packet with id 0xFA in future because it will be more difficult to support it
							if (MinecraftServer.getServer().isDebugging()) {
								System.out.println(
									ChannelUtils.getNetworkManagerSocketAddress(channel) +
									" is >= 1.7, checking data"
								);
							}
							cancelTask();
							handshakeversion = attemptDecodeNettyHandshake(channel, buf);
						}
					} else {
						//1.7+ handshake
						if (MinecraftServer.getServer().isDebugging()) {
							System.out.println(
								ChannelUtils.getNetworkManagerSocketAddress(channel) +
								" is >= 1.7, checking data"
							);
						}
						cancelTask();
						handshakeversion = attemptDecodeNettyHandshake(channel, buf);
					}
				} catch (EOFSignal ex) {
				}
				break;
			}
			case 0x02: { // <= 1.6.4 handshake
				if (MinecraftServer.getServer().isDebugging()) {
					System.out.println(
						ChannelUtils.getNetworkManagerSocketAddress(channel) +
						" is probably < 1.7"
					);
				}
				try {
					handshakeversion = ProtocolUtils.readOldHandshake(replayingBuffer);
				} catch (EOFSignal ex) {
				}
				break;
			}
			default: { // >= 1.7 handshake
				if (MinecraftServer.getServer().isDebugging()) {
					System.out.println(
						ChannelUtils.getNetworkManagerSocketAddress(channel) +
						" is >= 1.7, checking data"
					);
				}
				handshakeversion = attemptDecodeNettyHandshake(channel, buf);
				break;
			}
		}
		//if we detected the protocol than we save it and process data
		if (handshakeversion != null) {
			setProtocol(channel, receivedData, handshakeversion);
		}
	}

	protected volatile boolean protocolSet = false;

	protected void setProtocol(final Channel channel, final ByteBuf input, ProtocolVersion version) throws Exception {
		if (protocolSet) {
			return;
		}
		protocolSet = true;
		if (MinecraftServer.getServer().isDebugging()) {
			System.out.println(ChannelUtils.getNetworkManagerSocketAddress(channel)+ " connected with protocol version "+version);
		}
		ProtocolStorage.setProtocolVersion(ChannelUtils.getNetworkManagerSocketAddress(channel), version);
		channel.pipeline().remove(ChannelHandlers.INITIAL_DECODER);
		pipelineBuilders.get(version).buildPipeLine(channel, version);
		input.readerIndex(0);
		channel.pipeline().firstContext().fireChannelRead(input);
	}

	//handshake packet has more than 3 bytes for sure, so we can simplify splitting logic
	private static ProtocolVersion attemptDecodeNettyHandshake(Channel channel, ByteBuf bytebuf) {
		bytebuf.readerIndex(0);
		if (MinecraftServer.getServer().isDebugging()) {
			System.out.println(
				ChannelUtils.getNetworkManagerSocketAddress(channel) +
				" data readable: "+bytebuf.readableBytes()
			);
		}
		if (bytebuf.readableBytes() < 3) {
			return null;
		}
		int length = ChannelUtils.readVarInt(bytebuf);
		if (MinecraftServer.getServer().isDebugging()) {
			System.out.println(
				ChannelUtils.getNetworkManagerSocketAddress(channel) +
				" data need length: "+length
			);
		}
		if (MinecraftServer.getServer().isDebugging()) {
			System.out.println(
				ChannelUtils.getNetworkManagerSocketAddress(channel) +
				" data readable: "+bytebuf.readableBytes()
			);
		}
		if (bytebuf.readableBytes() < length) {
			return null;
		}
		ByteBuf data = bytebuf.readBytes(length);
		if (MinecraftServer.getServer().isDebugging()) {
			System.out.println(
				ChannelUtils.getNetworkManagerSocketAddress(channel) +
				" HS data: " +
				Arrays.toString(Arrays.copyOf(data.array(), data.readableBytes()))
			);
		}
		return ProtocolUtils.readNettyHandshake(data);
	}

}
