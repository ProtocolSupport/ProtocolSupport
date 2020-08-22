package protocolsupport.protocol.pipeline.version.util.decoder;

import java.text.MessageFormat;
import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderException;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.PacketDataCodecImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.pipeline.version.util.ConnectionImplMiddlePacketInit;
import protocolsupport.protocol.pipeline.version.util.MiddlePacketRegistry;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.zplatform.ServerPlatform;

public abstract class AbstractPacketDecoder extends SimpleChannelInboundHandler<ByteBuf> {

	protected final ConnectionImpl connection;
	protected final MiddlePacketRegistry<ServerBoundMiddlePacket> registry;
	protected PacketDataCodecImpl codec;

	public AbstractPacketDecoder(ConnectionImpl connection) {
		this.connection = connection;
		this.registry = new MiddlePacketRegistry<>(new ConnectionImplMiddlePacketInit(connection));
	}

	public void init(PacketDataCodecImpl codec) {
		this.codec = codec;
	}


	protected void decodeAndTransform(ByteBuf input) {
		registry.getTransformer(connection.getNetworkState(), codec.readPacketId(input)).decode(input);
	}

	protected void throwFailedTransformException(Exception exception, ByteBuf input) throws Exception {
		if (ServerPlatform.get().getMiscUtils().isDebugging()) {
			throw new DecoderException(MessageFormat.format(
				"Unable to transform or read serverbound middle packet(data {0})",
				Arrays.toString(MiscSerializer.readAllBytes(input))
			), exception);
		} else {
			throw exception;
		}
	}

}
