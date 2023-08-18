package protocolsupport.protocol.pipeline.version.util.encoder;

import java.text.MessageFormat;
import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import protocolsupport.protocol.codec.BytesCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.IClientboundMiddlePacket;
import protocolsupport.protocol.pipeline.IPacketDataChannelIO;
import protocolsupport.protocol.pipeline.version.util.MiddlePacketInitImpl;
import protocolsupport.protocol.pipeline.version.util.MiddlePacketRegistry;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.utils.netty.CombinedResultChannelPromise;
import protocolsupport.zplatform.ServerPlatform;

public abstract class AbstractPacketEncoder<T extends IClientboundMiddlePacket> extends ChannelOutboundHandlerAdapter {

	protected final IPacketDataChannelIO io;
	protected final MiddlePacketRegistry<T> registry;

	protected AbstractPacketEncoder(IPacketDataChannelIO io, NetworkDataCache cache) {
		this.io = io;
		this.registry = new MiddlePacketRegistry<>(new MiddlePacketInitImpl(io, cache));
	}

	public MiddlePacketRegistry<T> getMiddlePacketRegistry() {
		return registry;
	}


	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if (!(msg instanceof ByteBuf input)) {
			super.write(ctx, msg, promise);
			return;
		}

		if (!input.isReadable()) {
			input.release();
			promise.trySuccess();
			return;
		}

		IClientboundMiddlePacket packetTransformer = null;
		try {
			packetTransformer = registry.getTransformer(io.getNetworkState(), VarNumberCodec.readVarInt(input));
			CombinedResultChannelPromise combinedPromise = new CombinedResultChannelPromise(ctx.channel(), promise);
			io.setWritePromise(combinedPromise);
			try {
				packetTransformer.encode(input);
				if (input.isReadable()) {
					throw new DecoderException("Data not read fully, bytes left " + input.readableBytes());
				}
			} finally {
				io.clearWritePromise();
			}
			combinedPromise.closeUsageRegister();
		} catch (Throwable exception) {
			if (ServerPlatform.get().getMiscUtils().isDebugging()) {
				input.readerIndex(0);
				throw new EncoderException(MessageFormat.format(
					"Unable to transform or read clientbound middle packet(type {0}, data {1})",
					packetTransformer != null ? packetTransformer.getClass().getName() : "unknown",
					Arrays.toString(BytesCodec.readAllBytes(input))
				), exception);
			} else {
				throw exception;
			}
		} finally {
			input.release();
		}
	}

}
