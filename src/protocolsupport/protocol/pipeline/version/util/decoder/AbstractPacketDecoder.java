package protocolsupport.protocol.pipeline.version.util.decoder;

import java.text.MessageFormat;
import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderException;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.pipeline.IPacketCodec;
import protocolsupport.protocol.pipeline.version.util.MiddlePacketRegistry;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.zplatform.ServerPlatform;

public abstract class AbstractPacketDecoder extends SimpleChannelInboundHandler<ByteBuf> {

	protected final ConnectionImpl connection;
	protected final IPacketCodec codec;
	protected final MiddlePacketRegistry<ServerBoundMiddlePacket> registry;

	public AbstractPacketDecoder(ConnectionImpl connection, IPacketCodec codec) {
		this.connection = connection;
		this.codec = codec;
		this.registry = new MiddlePacketRegistry<>(connection);
	}

	protected void decodeAndTransform(ChannelHandlerContext ctx, ByteBuf input) {
		ServerBoundMiddlePacket packetTransformer = registry.getTransformer(connection.getNetworkState(), codec.readPacketId(input));
		packetTransformer.readFromClientData(input);
		connection.readServerboundPackets(packetTransformer.toNative(), false);
	}

	protected void throwFailedTransformException(Exception exception, ByteBuf data) throws Exception {
		if (ServerPlatform.get().getMiscUtils().isDebugging()) {
			data.readerIndex(0);
			throw new DecoderException(MessageFormat.format(
				"Unable to transform or read packet data {0}", Arrays.toString(MiscSerializer.readAllBytes(data))
			), exception);
		} else {
			throw exception;
		}
	}

}
