package protocolsupport.protocol.pipeline.initial;

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
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPipeLineBuilder;
import protocolsupport.protocol.pipeline.common.VarIntFrameDecoder;
import protocolsupport.protocol.pipeline.common.VarIntFrameEncoder;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.Utils;
import protocolsupport.utils.netty.ReplayingDecoderBuffer;
import protocolsupport.utils.netty.ReplayingDecoderBuffer.EOFSignal;
import protocolsupport.zplatform.PlatformUtils;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.impl.encapsulated.EncapsulatedProtocolInfo;
import protocolsupport.zplatform.impl.encapsulated.EncapsulatedProtocolUtils;

public class InitialPacketDecoder extends SimpleChannelInboundHandler<ByteBuf> {

	private static final int ping152delay = Utils.getJavaPropertyValue("ping152delay", 100, Integer::parseInt);
	private static final int pingLegacyDelay = Utils.getJavaPropertyValue("pinglegacydelay", 200, Integer::parseInt);

	static {
		ProtocolSupport.logInfo("Assume 1.5.2 ping delay: "+ping152delay);
		ProtocolSupport.logInfo("Assume legacy ping delay: "+pingLegacyDelay);
	}

	private static final EnumMap<ProtocolVersion, IPipeLineBuilder> pipelineBuilders = new EnumMap<>(ProtocolVersion.class);
	static {
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_FUTURE, new protocolsupport.protocol.pipeline.version.v_future.PipeLineBuilder());
		IPipeLineBuilder builder1122 = new protocolsupport.protocol.pipeline.version.v_1_12.r2.PipeLineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_12_2, builder1122);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_12_1, builder1122);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_12, new protocolsupport.protocol.pipeline.version.v_1_12.r1.PipeLineBuilder());
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
			switch (firstbyte) {
				case 0x00: { // encapsulated protocol handsake
					setEncapsulatedProtocol(channel, EncapsulatedProtocolUtils.readInfo(replayingBuffer));
					break;
				}
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
							"MC|PingHost".equals(StringSerializer.readString(replayingBuffer, ProtocolVersion.MINECRAFT_1_6_4))
						) {
							//definitely 1.6
							replayingBuffer.readUnsignedShort();
							setNativeProtocol(channel, ProtocolUtils.get16PingVersion(replayingBuffer.readUnsignedByte()));
						} else {
							//it was 1.7+ handshake after all
							//hope that there won't be any handshake packet with id 0xFA in future because it will be more difficult to support it
							setNativeProtocol(channel, attemptDecodeNewHandshake(replayingBuffer));
						}
					} else {
						//1.7+ handshake
						setNativeProtocol(channel, attemptDecodeNewHandshake(replayingBuffer));
					}
					break;
				}
				case 0x02: { // <= 1.6.4 handshake
					setNativeProtocol(channel, ProtocolUtils.readOldHandshake(replayingBuffer));
					break;
				}
				default: { // >= 1.7 handshake
					setNativeProtocol(channel, attemptDecodeNewHandshake(replayingBuffer));
					break;
				}
			}
		} catch (EOFSignal ex) {
		}
	}

	private void setEncapsulatedProtocol(Channel channel, EncapsulatedProtocolInfo info) {
		ConnectionImpl connection = prepare(channel, info.getVersion());
		PlatformUtils putils = ServerPlatform.get().getMiscUtils();
		pipelineBuilders.get(connection.getVersion()).buildCodec(channel, connection);
		putils.setFraming(channel.pipeline(), new VarIntFrameDecoder(), new VarIntFrameEncoder());
		if (info.hasCompression()) {
			putils.enableCompression(channel.pipeline(), putils.getCompressionThreshold());
		}
		channel.pipeline().firstContext().fireChannelRead(receivedData);
	}

	protected void setNativeProtocol(Channel channel, ProtocolVersion version) {
		ConnectionImpl connection = prepare(channel, version);
		IPipeLineBuilder builder = pipelineBuilders.get(connection.getVersion());
		builder.buildCodec(channel, connection);
		builder.buildPipeLine(channel, connection);
		receivedData.readerIndex(0);
		channel.pipeline().firstContext().fireChannelRead(receivedData);
	}

	protected ConnectionImpl prepare(Channel channel, ProtocolVersion version) {
		channel.pipeline().remove(ChannelHandlers.INITIAL_DECODER);
		ConnectionImpl connection = ConnectionImpl.getFromChannel(channel);
		if (ServerPlatform.get().getMiscUtils().isDebugging()) {
			ProtocolSupport.logInfo(MessageFormat.format("{0} connected with protocol version {1}", connection.getAddress(), version));
		}
		connection.getNetworkManagerWrapper().setPacketListener(ServerPlatform.get().getWrapperFactory().createHandshakeListener(connection.getNetworkManagerWrapper()));
		if (!ProtocolSupportAPI.isProtocolVersionEnabled(version)) {
			version = version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_4) ? ProtocolVersion.MINECRAFT_LEGACY : ProtocolVersion.MINECRAFT_FUTURE;
		}
		connection.setVersion(version);
		return connection;
	}

	private static ProtocolVersion attemptDecodeNewHandshake(ByteBuf bytebuf) {
		bytebuf.readerIndex(0);
		return ProtocolUtils.readNewHandshake(bytebuf.readSlice(VarNumberSerializer.readVarInt(bytebuf)));
	}

}
