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
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPipeLineBuilder;
import protocolsupport.protocol.pipeline.common.VarIntFrameDecoder;
import protocolsupport.protocol.pipeline.common.VarIntFrameEncoder;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.Utils;
import protocolsupport.utils.netty.Decompressor;
import protocolsupport.utils.netty.ReplayingDecoderBuffer;
import protocolsupport.utils.netty.ReplayingDecoderBuffer.EOFSignal;
import protocolsupport.zplatform.PlatformUtils;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.impl.encapsulated.EncapsulatedProtocolInfo;
import protocolsupport.zplatform.impl.encapsulated.EncapsulatedProtocolUtils;
import protocolsupport.zplatform.impl.pe.PEProxyServerInfoHandler;

public class InitialPacketDecoder extends SimpleChannelInboundHandler<ByteBuf> {

	protected static final int ping152delay = Utils.getJavaPropertyValue("ping152delay", 100, Integer::parseInt);
	protected static final int pingLegacyDelay = Utils.getJavaPropertyValue("pinglegacydelay", 200, Integer::parseInt);

	static {
		ProtocolSupport.logInfo("Assume 1.5.2 ping delay: "+ping152delay);
		ProtocolSupport.logInfo("Assume legacy ping delay: "+pingLegacyDelay);
	}

	protected static final EnumMap<ProtocolVersion, IPipeLineBuilder> pipelineBuilders = new EnumMap<>(ProtocolVersion.class);
	static {
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_FUTURE, new protocolsupport.protocol.pipeline.version.v_f.PipeLineBuilder());
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
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_LEGACY, new protocolsupport.protocol.pipeline.version.v_l.PipeLineBuilder());
		IPipeLineBuilder builderpe = new protocolsupport.protocol.pipeline.version.v_pe.PipeLineBuilder();
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_PE_FUTURE, builderpe);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_PE, builderpe);
		pipelineBuilders.put(ProtocolVersion.MINECRAFT_PE_LEGACY, builderpe);
	}

	protected final ReplayingDecoderBuffer buffer = new ReplayingDecoderBuffer(Unpooled.buffer());

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
		buffer.writeBytes(buf);
		buffer.readerIndex(0);
		decode(ctx);
	}

	private boolean firstread = true;
	private EncapsulatedProtocolInfo encapsulatedinfo = null;

	private void decode(ChannelHandlerContext ctx) throws Exception {
		cancelTask();
		if (firstread) {
			int firstbyte = buffer.readUnsignedByte();
			if (firstbyte == 0) {
				encapsulatedinfo = EncapsulatedProtocolUtils.readInfo(buffer);
				buffer.discardReadBytes();
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

	private void decodeRaw(ChannelHandlerContext ctx) {
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
						"MC|PingHost".equals(StringSerializer.readString(buffer, ProtocolVersion.MINECRAFT_1_6_4))
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

	private static ProtocolVersion attemptDecodeNewHandshake(ByteBuf bytebuf) {
		bytebuf.readerIndex(0);
		return ProtocolUtils.readNewHandshake(bytebuf.readSlice(VarNumberSerializer.readVarInt(bytebuf)));
	}

	private void decodeEncapsulated(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		ByteBuf firstpacketdata = buffer.readSlice(VarNumberSerializer.readVarInt(buffer));
		if (encapsulatedinfo.hasCompression()) {
			int uncompressedlength = VarNumberSerializer.readVarInt(firstpacketdata);
			if (uncompressedlength != 0) {
				firstpacketdata = Unpooled.wrappedBuffer(Decompressor.decompressStatic(MiscSerializer.readAllBytes(firstpacketdata), uncompressedlength));
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
						"MC|PingHost".equals(StringSerializer.readString(firstpacketdata, ProtocolVersion.MINECRAFT_1_6_4))
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
			case PEPacketIDs.LOGIN: {
				setProtocol(channel, ProtocolUtils.readPEHandshake(firstpacketdata));
				break;
			}
			case PEProxyServerInfoHandler.PACKET_ID: {
				setProtocol(channel, ProtocolVersion.MINECRAFT_PE);
				break;
			}
			default: {
				throw new DecoderException("Unable to detect incoming protocol");
			}
		}
	}

	protected void setProtocol(Channel channel, ProtocolVersion version) {
		ConnectionImpl connection = prepare(channel, version);
		IPipeLineBuilder builder = pipelineBuilders.get(connection.getVersion());
		builder.buildCodec(channel, connection);
		if (encapsulatedinfo == null) {
			builder.buildPipeLine(channel, connection);
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
		connection.getNetworkManagerWrapper().setPacketListener(ServerPlatform.get().getWrapperFactory().createHandshakeListener(connection.getNetworkManagerWrapper()));
		if (!ProtocolSupportAPI.isProtocolVersionEnabled(version)) {
			if (version.getProtocolType() == ProtocolType.PC) {
				version = version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_4) ? ProtocolVersion.MINECRAFT_LEGACY : ProtocolVersion.MINECRAFT_FUTURE;
			} else if (version.getProtocolType() == ProtocolType.PE) {
				version = version.isAfterOrEq(ProtocolVersion.MINECRAFT_PE) ? ProtocolVersion.MINECRAFT_PE_FUTURE : ProtocolVersion.MINECRAFT_PE_LEGACY;
			} else {
				throw new IllegalArgumentException(MessageFormat.format("Unable to get legacy or future version for disabled protocol version {0}", version));
			}
		}
		connection.setVersion(version);
		return connection;
	}

}
