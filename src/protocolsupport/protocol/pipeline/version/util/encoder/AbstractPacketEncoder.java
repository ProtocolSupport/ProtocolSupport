package protocolsupport.protocol.pipeline.version.util.encoder;

import java.text.MessageFormat;
import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.PacketDataCodecImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.pipeline.version.util.ConnectionImplMiddlePacketInit;
import protocolsupport.protocol.pipeline.version.util.MiddlePacketRegistry;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.netty.CombinedResultChannelPromise;
import protocolsupport.zplatform.ServerPlatform;

public abstract class AbstractPacketEncoder extends ChannelOutboundHandlerAdapter {

	protected final ConnectionImpl connection;
	protected final MiddlePacketRegistry<ClientBoundMiddlePacket> registry;
	protected final PacketDataCodecImpl codec;

	public AbstractPacketEncoder(ConnectionImpl connection) {
		this.connection = connection;
		this.codec = connection.getCodec();
		this.registry = new MiddlePacketRegistry<>(new ConnectionImplMiddlePacketInit(connection));
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if (!(msg instanceof ByteBuf)) {
			super.write(ctx, msg, promise);
			return;
		}

		ByteBuf input = (ByteBuf) msg;
		if (!input.isReadable()) {
			input.release();
			promise.trySuccess();
			return;
		}

		ClientBoundMiddlePacket packetTransformer = null;
		try {
			packetTransformer = registry.getTransformer(connection.getNetworkState(), VarNumberSerializer.readVarInt(input));
			CombinedResultChannelPromise combinedPromise = new CombinedResultChannelPromise(ctx.channel(), promise);
			codec.setWritePromise(combinedPromise);
			try {
				packetTransformer.encode(input);
				if (input.isReadable()) {
					throw new DecoderException("Data not read fully, bytes left " + input.readableBytes());
				}
			} finally {
				codec.clearWritePromise();
			}
			combinedPromise.closeUsageRegister();
		} catch (Throwable exception) {
			if (ServerPlatform.get().getMiscUtils().isDebugging()) {
				input.readerIndex(0);
				throw new EncoderException(MessageFormat.format(
					"Unable to transform or read clientbound middle packet(type {0}, data {1})",
					packetTransformer != null ? packetTransformer.getClass().getName() : "unknown",
					Arrays.toString(MiscSerializer.readAllBytes(input))
				), exception);
			} else {
				throw exception;
			}
		} finally {
			input.release();
		}
	}

}
