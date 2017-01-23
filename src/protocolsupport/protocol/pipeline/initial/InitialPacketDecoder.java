package protocolsupport.protocol.pipeline.initial;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Future;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPipeLineBuilder;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.Utils;
import protocolsupport.utils.Utils.Converter;
import protocolsupport.utils.netty.ReplayingDecoderBuffer;
import protocolsupport.utils.netty.ReplayingDecoderBuffer.EOFSignal;
import protocolsupport.zplatform.ServerPlatform;

public class InitialPacketDecoder extends SimpleChannelInboundHandler<ByteBuf> {

	private static final int ping152delay = Utils.getJavaPropertyValue("ping152delay", 100, Converter.STRING_TO_INT);
	private static final int pingLegacyDelay = Utils.getJavaPropertyValue("pinglegacydelay", 200, Converter.STRING_TO_INT);

	public static void init() {
		ProtocolSupport.logInfo("Assume 1.5.2 ping delay: "+ping152delay);
		ProtocolSupport.logInfo("Assume legacy ping dealy: "+pingLegacyDelay);
	}

	private static final EnumMap<ProtocolVersion, IPipeLineBuilder> pipelineBuilders = new EnumMap<>(ProtocolVersion.class);
	static {
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_FUTURE, new protocolsupport.protocol.pipeline.version.v_future.PipeLineBuilder());
		IPipeLineBuilder builder111 = new protocolsupport.protocol.pipeline.version.v_1_11.PipeLineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_11_1, builder111);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_11, builder111);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_10, new protocolsupport.protocol.pipeline.version.v_1_10.PipeLineBuilder());
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_9_4, new protocolsupport.protocol.pipeline.version.v_1_9.r2.PipeLineBuilder());
		IPipeLineBuilder builder19r1 = new protocolsupport.protocol.pipeline.version.v_1_9.r1.PipeLineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_9_2, builder19r1);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_9_1, builder19r1);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_9, builder19r1);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_8, new protocolsupport.protocol.pipeline.version.v_1_8.PipeLineBuilder());
		IPipeLineBuilder builder17 = new protocolsupport.protocol.pipeline.version.v_1_7.PipeLineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_7_10, builder17);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_7_5, builder17);
		IPipeLineBuilder builder16 = new protocolsupport.protocol.pipeline.version.v_1_6.PipeLineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_6_4, builder16);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_6_2, builder16);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_6_1, builder16);
		IPipeLineBuilder builder15 = new protocolsupport.protocol.pipeline.version.v_1_5.PipeLineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_5_2, builder15);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_5_1, builder15);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_4_7, new protocolsupport.protocol.pipeline.version.v_1_4.PipeLineBuilder());
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_LEGACY, new protocolsupport.protocol.pipeline.version.v_legacy.PipeLineBuilder());
	}

	protected final ByteBuf receivedData = Unpooled.buffer();
	protected final ReplayingDecoderBuffer replayingBuffer = new ReplayingDecoderBuffer(receivedData);

	protected Future<?> responseTask;

	protected void scheduleTask(ChannelHandlerContext ctx, Runnable task, long delay, TimeUnit tu) {
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
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		cancelTask();
		super.handlerRemoved(ctx);
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, ByteBuf buf)  {
		if (!buf.isReadable()) {
			return;
		}
		receivedData.writeBytes(buf);
		receivedData.readerIndex(0);
		decode(ctx);
	}

	private void decode(ChannelHandlerContext ctx) {
		cancelTask();
		Channel channel = ctx.channel();
		int firstbyte = replayingBuffer.readUnsignedByte();
		try {
			ProtocolVersion handshakeversion = null;
			switch (firstbyte) {
				case 0xFE: { //old ping or a part of varint length
					if (replayingBuffer.readableBytes() == 0) {
						//no more data received, it may be old protocol, or we just not received all data yet, so delay assuming as really old protocol for some time
						scheduleTask(ctx, new SetProtocolTask(this, channel, ProtocolVersion.MINECRAFT_LEGACY), pingLegacyDelay, TimeUnit.MILLISECONDS);
					} else if (replayingBuffer.readUnsignedByte() == 1) {
						//1.5-1.6 ping or maybe a finishing byte for 1.7+ packet length
						if (replayingBuffer.readableBytes() == 0) {
							//no more data received, it may be 1.5.2 or we just didn't receive 1.6 or 1.7+ data yet, so delay assuming as 1.5.2 for some time
							scheduleTask(ctx, new SetProtocolTask(this, channel, ProtocolVersion.MINECRAFT_1_5_2), ping152delay, TimeUnit.MILLISECONDS);
						} else if (
							(replayingBuffer.readUnsignedByte() == 0xFA) &&
							"MC|PingHost".equals(new String(ProtocolSupportPacketDataSerializer.toArray(replayingBuffer.readSlice(replayingBuffer.readUnsignedShort() * 2)), StandardCharsets.UTF_16BE))
						) {
							//definitely 1.6
							replayingBuffer.readUnsignedShort();
							handshakeversion = ProtocolUtils.get16PingVersion(replayingBuffer.readUnsignedByte());
						} else {
							//it was 1.7+ handshake after all
							//hope that there won't be any handshake packet with id 0xFA in future because it will be more difficult to support it
							handshakeversion = attemptDecodeNettyHandshake(replayingBuffer);
						}
					} else {
						//1.7+ handshake
						handshakeversion = attemptDecodeNettyHandshake(replayingBuffer);
					}
					break;
				}
				case 0x02: { // <= 1.6.4 handshake
					handshakeversion = ProtocolUtils.readOldHandshake(replayingBuffer);
					break;
				}
				default: { // >= 1.7 handshake
					handshakeversion = attemptDecodeNettyHandshake(replayingBuffer);
					break;
				}
			}
			//if we detected the protocol than we save it and process data
			if (handshakeversion != null) {
				setProtocol(channel, handshakeversion);
			}
		} catch (EOFSignal ex) {
		}
	}

	protected void setProtocol(final Channel channel, ProtocolVersion version) {
		ConnectionImpl connection = ConnectionImpl.getFromChannel(channel);
		if (ServerPlatform.get().getMiscUtils().isDebugging()) {
			ProtocolSupport.logInfo(MessageFormat.format("{0} connected with protocol version {1}", connection.getAddress(), version));
		}
		connection.setVersion(version);
		channel.pipeline().remove(ChannelHandlers.INITIAL_DECODER);
		pipelineBuilders.get(version).buildPipeLine(channel, connection);
		receivedData.readerIndex(0);
		channel.pipeline().firstContext().fireChannelRead(receivedData);
	}

	private static ProtocolVersion attemptDecodeNettyHandshake(ByteBuf bytebuf) {
		bytebuf.readerIndex(0);
		return ProtocolUtils.readNettyHandshake(bytebuf.readSlice(ProtocolSupportPacketDataSerializer.readVarInt(bytebuf)));
	}

}
