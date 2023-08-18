package protocolsupport.protocol.pipeline.initial;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderException;
import io.netty.util.concurrent.Future;
import protocolsupport.ProtocolSupport;
import protocolsupport.ProtocolSupportFileLog;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.codec.BytesCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPipelineBuilder;
import protocolsupport.protocol.pipeline.common.VarIntFrameDecoder;
import protocolsupport.protocol.pipeline.common.VarIntFrameEncoder;
import protocolsupport.utils.JavaSystemProperty;
import protocolsupport.utils.netty.Decompressor;
import protocolsupport.utils.netty.ReplayingDecoderByteBuf;
import protocolsupport.utils.netty.ReplayingDecoderByteBuf.EOFSignal;
import protocolsupport.zplatform.PlatformUtils;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.impl.encapsulated.EncapsulatedProtocolInfo;
import protocolsupport.zplatform.impl.encapsulated.EncapsulatedProtocolUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class InitialPacketDecoder extends SimpleChannelInboundHandler<ByteBuf> {

	protected static final int ping152delay = JavaSystemProperty.getValue("ping152delay", 100, Integer::parseInt);
	protected static final int pingLegacyDelay = JavaSystemProperty.getValue("pinglegacydelay", 200, Integer::parseInt);

	static {
		{
			String message = MessageFormat.format("Assume 1.5.2 ping delay: {0}", ping152delay);
			ProtocolSupport.logInfo(message);
			if (ProtocolSupportFileLog.isEnabled()) {
				ProtocolSupportFileLog.logInfoMessage(message);
			}
		}
		{
			String message = MessageFormat.format("Assume legacy ping delay: {0}", pingLegacyDelay);
			ProtocolSupport.logInfo(message);
			if (ProtocolSupportFileLog.isEnabled()) {
				ProtocolSupportFileLog.logInfoMessage(message);
			}
		}
	}

	protected static final EnumMap<ProtocolVersion, IPipelineBuilder> pipelineBuilders = new EnumMap<>(ProtocolVersion.class);
	static {
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_FUTURE, new protocolsupport.protocol.pipeline.version.v_f.PipelineBuilder());
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_20, new protocolsupport.protocol.pipeline.version.v_1_20.PipelineBuilder());
		IPipelineBuilder builder18 = new protocolsupport.protocol.pipeline.version.v_1_18.PipelineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_18_2, builder18);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_18, builder18);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_17_1, new protocolsupport.protocol.pipeline.version.v_1_17.r2.PipelineBuilder());
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_17, new protocolsupport.protocol.pipeline.version.v_1_17.r1.PipelineBuilder());
		IPipelineBuilder builder16r2 = new protocolsupport.protocol.pipeline.version.v_1_16.r2.PipelineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_16_4, builder16r2);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_16_3, builder16r2);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_16_2, builder16r2);
		IPipelineBuilder builder16r1 = new protocolsupport.protocol.pipeline.version.v_1_16.r1.PipelineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_16_1, builder16r1);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_16, builder16r1);
		IPipelineBuilder builder15 = new protocolsupport.protocol.pipeline.version.v_1_15.PipelineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_15_2, builder15);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_15_1, builder15);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_15, builder15);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_14_4, new protocolsupport.protocol.pipeline.version.v_1_14.r2.PipelineBuilder());
		IPipelineBuilder builder14r1 = new protocolsupport.protocol.pipeline.version.v_1_14.r1.PipelineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_14_3, builder14r1);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_14_2, builder14r1);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_14_1, builder14r1);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_14, builder14r1);
		IPipelineBuilder builder13 = new protocolsupport.protocol.pipeline.version.v_1_13.PipelineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_13_2, builder13);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_13_1, builder13);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_13, builder13);
		IPipelineBuilder builder12 = new protocolsupport.protocol.pipeline.version.v_1_12.r2.PipelineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_12_2, builder12);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_12_1, builder12);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_12, new protocolsupport.protocol.pipeline.version.v_1_12.r1.PipelineBuilder());
		IPipelineBuilder builder11 = new protocolsupport.protocol.pipeline.version.v_1_11.PipelineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_11_1, builder11);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_11, builder11);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_10, new protocolsupport.protocol.pipeline.version.v_1_10.PipelineBuilder());
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_9_4, new protocolsupport.protocol.pipeline.version.v_1_9.r2.PipelineBuilder());
		IPipelineBuilder builder9r1 = new protocolsupport.protocol.pipeline.version.v_1_9.r1.PipelineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_9_2, builder9r1);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_9_1, builder9r1);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_9, builder9r1);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_8, new protocolsupport.protocol.pipeline.version.v_1_8.PipelineBuilder());
		IPipelineBuilder builder7 = new protocolsupport.protocol.pipeline.version.v_1_7.PipelineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_7_10, builder7);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_7_5, builder7);
		IPipelineBuilder builder6 = new protocolsupport.protocol.pipeline.version.v_1_6.PipelineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_6_4, builder6);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_6_2, builder6);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_6_1, builder6);
		IPipelineBuilder builder5 = new protocolsupport.protocol.pipeline.version.v_1_5.PipelineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_5_2, builder5);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_5_1, builder5);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_1_4_7, new protocolsupport.protocol.pipeline.version.v_1_4.PipelineBuilder());
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_LEGACY, new protocolsupport.protocol.pipeline.version.v_l.PipelineBuilder());
	}

	protected final ReplayingDecoderByteBuf buffer = new ReplayingDecoderByteBuf(Unpooled.buffer());

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
	public void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
		if (!buf.isReadable()) {
			return;
		}
		buffer.unwrap().writeBytes(buf);
		buffer.readerIndex(0);
		decode(ctx);
	}

	protected boolean firstread = true;
	protected EncapsulatedProtocolInfo encapsulatedinfo = null;

	protected void decode(ChannelHandlerContext ctx) throws Exception {
		cancelTask();
		if (firstread) {
			int firstbyte = buffer.readUnsignedByte();
			if (firstbyte == 0) {
				encapsulatedinfo = EncapsulatedProtocolUtils.readInfo(buffer);
				buffer.unwrap().discardReadBytes();
			}
			buffer.readerIndex(0);
			firstread = false;
		}
		try {
			if (encapsulatedinfo == null) {
				decodeRaw(ctx);
			} else {
				decodeEncapsulated(ctx);
			}
		} catch (EOFSignal ex) {
		}
	}

	protected void decodeRaw(ChannelHandlerContext ctx) {
		Channel channel = ctx.channel();
		int firstbyte = buffer.readUnsignedByte();
		switch (firstbyte) {
			case 0xFE: { //old ping or a part of varint length
				if (buffer.readableBytes() == 0) {
					//no more data received, it may be old protocol, or we just not received all data yet, so delay assuming as really old protocol for some time
					scheduleTask(ctx, new SetProtocolTask(this, channel, ProtocolVersion.MINECRAFT_LEGACY), pingLegacyDelay, TimeUnit.MILLISECONDS);
				} else if (buffer.readUnsignedByte() == 1) {
					//1.5-1.6 ping or maybe a finishing byte for 1.7+ packet length
					if (buffer.readableBytes() == 0) {
						//no more data received, it may be 1.5.2 or we just didn't receive 1.6 or 1.7+ data yet, so delay assuming as 1.5.2 for some time
						scheduleTask(ctx, new SetProtocolTask(this, channel, ProtocolVersion.MINECRAFT_1_5_2), ping152delay, TimeUnit.MILLISECONDS);
					} else if (
						(buffer.readUnsignedByte() == 0xFA) &&
						"MC|PingHost".equals(StringCodec.readShortUTF16BEString(buffer, Short.MAX_VALUE))
					) {
						//definitely 1.6
						buffer.readUnsignedShort();
						setProtocol(channel, ProtocolUtils.get16PingVersion(buffer.readUnsignedByte()));
					} else {
						//it was 1.7+ handshake after all
						//hope that there won't be any handshake packet with id 0xFA in future because it will be more difficult to support it
						setProtocol(channel, attemptDecodeNewHandshake(buffer));
					}
				} else {
					//1.7+ handshake
					setProtocol(channel, attemptDecodeNewHandshake(buffer));
				}
				break;
			}
			case 0x02: { // <= 1.6.4 handshake
				setProtocol(channel, ProtocolUtils.readOldHandshake(buffer));
				break;
			}
			default: { // >= 1.7 handshake
				setProtocol(channel, attemptDecodeNewHandshake(buffer));
				break;
			}
		}
	}

	protected static ProtocolVersion attemptDecodeNewHandshake(ByteBuf bytebuf) {
		bytebuf.readerIndex(0);
		return ProtocolUtils.readNewHandshake(bytebuf.readSlice(VarNumberCodec.readVarInt(bytebuf)));
	}

	protected void decodeEncapsulated(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		ByteBuf firstpacketdata = buffer.readSlice(VarNumberCodec.readVarInt(buffer));
		if (encapsulatedinfo.hasCompression()) {
			int uncompressedlength = VarNumberCodec.readVarInt(firstpacketdata);
			if (uncompressedlength != 0) {
				firstpacketdata = Unpooled.wrappedBuffer(Decompressor.decompressStatic(BytesCodec.readAllBytes(firstpacketdata), uncompressedlength));
			}
		}
		int firstbyte = firstpacketdata.readUnsignedByte();
		switch (firstbyte) {
			case 0xFE: { //legacy ping
				if (firstpacketdata.readableBytes() == 0) {
					//< 1.5.2
					setProtocol(channel, ProtocolVersion.MINECRAFT_LEGACY);
				} else if (firstpacketdata.readUnsignedByte() == 1) {
					//1.5 or 1.6 ping
					if (firstpacketdata.readableBytes() == 0) {
						//1.5.*, we just assume 1.5.2
						setProtocol(channel, ProtocolVersion.MINECRAFT_1_5_2);
					} else if (
						(firstpacketdata.readUnsignedByte() == 0xFA) &&
						"MC|PingHost".equals(StringCodec.readShortUTF16BEString(firstpacketdata, Short.MAX_VALUE))
					) {
						//1.6.*
						firstpacketdata.readUnsignedShort();
						setProtocol(channel, ProtocolUtils.get16PingVersion(firstpacketdata.readUnsignedByte()));
					} else {
						throw new DecoderException("Unable to detect incoming protocol");
					}
				} else {
					throw new DecoderException("Unable to detect incoming protocol");
				}
				break;
			}
			case 0x02: { // <= 1.6.4 handshake
				setProtocol(channel, ProtocolUtils.readOldHandshake(firstpacketdata));
				break;
			}
			case 0x00: { // >= 1.7 handshake
				setProtocol(channel, ProtocolUtils.readNewHandshake(firstpacketdata));
				break;
			}
			default: {
				throw new DecoderException("Unable to detect incoming protocol");
			}
		}
	}

	protected void setProtocol(Channel channel, ProtocolVersion version) {
		ConnectionImpl connection = prepare(channel, version);
		IPipelineBuilder builder = pipelineBuilders.get(connection.getVersion());

		builder.buildCodec(channel.pipeline(), connection.getPacketDataIO(), connection.getCache());
		connection.initPacketDataIO(builder.getPacketIdCodec());
		builder.buildPostProcessor(connection.getPacketDataIO());

		if (encapsulatedinfo == null) {
			builder.buildTransport(channel.pipeline());
		} else {
			PlatformUtils putils = ServerPlatform.get().getMiscUtils();
			putils.setFraming(channel.pipeline(), new VarIntFrameDecoder(), new VarIntFrameEncoder());
			if (encapsulatedinfo.hasCompression()) {
				putils.enableCompression(channel.pipeline(), putils.getCompressionThreshold());
			}
			if ((encapsulatedinfo.getAddress() != null) && connection.getRawAddress().getAddress().isLoopbackAddress()) {
				connection.changeAddress(encapsulatedinfo.getAddress());
			}
		}

		buffer.readerIndex(0);
		channel.pipeline().firstContext().fireChannelRead(buffer.unwrap());
	}

	protected static ConnectionImpl prepare(Channel channel, ProtocolVersion version) {
		channel.pipeline().remove(ChannelHandlers.INITIAL_DECODER);
		ConnectionImpl connection = ConnectionImpl.getFromChannel(channel);
		if (ServerPlatform.get().getMiscUtils().isDebugging()) {
			ProtocolSupport.logInfo(MessageFormat.format("{0} connected with protocol version {1}", connection.getAddress(), version));
		}
		connection.getNetworkManagerWrapper().setPacketListener(ServerPlatform.get().getMiscUtils().createHandshakeListener(connection.getNetworkManagerWrapper()));
		if (!ProtocolSupportAPI.isProtocolVersionEnabled(version)) {
			if (version.getProtocolType() == ProtocolType.PC) {
				version = version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_4) ? ProtocolVersion.MINECRAFT_LEGACY : ProtocolVersion.MINECRAFT_FUTURE;
			} else {
				throw new IllegalArgumentException(MessageFormat.format("Unable to get legacy or future version for disabled protocol version {0}", version));
			}
		}
		connection.setVersion(version);
		return connection;
	}

}
