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
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.pipeline.version.util.MiddlePacketRegistry;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.zplatform.ServerPlatform;

public abstract class AbstractPacketEncoder extends ChannelOutboundHandlerAdapter {

	protected final ConnectionImpl connection;
	protected final MiddlePacketRegistry<ClientBoundMiddlePacket> registry;

	public AbstractPacketEncoder(ConnectionImpl connection) {
		this.connection = connection;
		this.registry = new MiddlePacketRegistry<>(connection);
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
			promise.setSuccess();
			return;
		}

		try {
			ClientBoundMiddlePacket packetTransformer = registry.getTransformer(connection.getNetworkState(), VarNumberSerializer.readVarInt(input));
			packetTransformer.readFromServerData(input);

			if (input.isReadable()) {
				throw new DecoderException("Did not read all data from packet " + packetTransformer.getClass() + ", bytes left: " + input.readableBytes());
			}
			if (!packetTransformer.postFromServerRead()) {
				promise.setSuccess();
				return;
			}

			connection.writeClientboundPackets(packetTransformer.toData(), promise, false);
		} catch (Exception exception) {
			if (ServerPlatform.get().getMiscUtils().isDebugging()) {
				input.readerIndex(0);
				throw new EncoderException(MessageFormat.format(
					"Unable to transform or read packet data {0}", Arrays.toString(MiscSerializer.readAllBytes(input))
				), exception);
			} else {
				throw exception;
			}
		} finally {
			input.release();
		}
	}

}
