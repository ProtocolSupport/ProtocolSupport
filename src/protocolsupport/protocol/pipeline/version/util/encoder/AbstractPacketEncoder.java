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
import protocolsupport.protocol.packet.PacketDataCodec;
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

	protected PacketDataCodec codec;

	public void init(PacketDataCodec codec) {
		this.codec = codec;
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

		ClientBoundMiddlePacket packetTransformer = null;
		try {
			packetTransformer = registry.getTransformer(connection.getNetworkState(), VarNumberSerializer.readVarInt(input));

			packetTransformer.readFromServerData(input);
			if (input.isReadable()) {
				throw new DecoderException("Data not read fully, bytes left " + input.readableBytes());
			}

			codec.channelWrite(promise, packetTransformer, lPacketTransformer -> {
				if (lPacketTransformer.postFromServerRead()) {
					lPacketTransformer.writeToClient();
				}
			});

			packetTransformer.postHandle();
		} catch (Exception exception) {
			if (ServerPlatform.get().getMiscUtils().isDebugging()) {
				input.readerIndex(0);
				throw new EncoderException(MessageFormat.format(
					"Unable to transform or read middle packet(type {0}, data {1})",
					packetTransformer != null ? packetTransformer.getClass().getName() : "unknown",
					Arrays.toString(MiscSerializer.readAllBytes(input)
				)), exception);
			} else {
				throw exception;
			}
		} finally {
			input.release();
		}
	}

}
