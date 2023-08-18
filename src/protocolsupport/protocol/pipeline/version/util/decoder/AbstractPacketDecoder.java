package protocolsupport.protocol.pipeline.version.util.decoder;

import java.text.MessageFormat;
import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderException;
import protocolsupport.protocol.codec.BytesCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.IServerboundMiddlePacket;
import protocolsupport.protocol.pipeline.IPacketDataChannelIO;
import protocolsupport.protocol.pipeline.version.util.MiddlePacketInitImpl;
import protocolsupport.protocol.pipeline.version.util.MiddlePacketRegistry;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.zplatform.ServerPlatform;

public abstract class AbstractPacketDecoder<T extends IServerboundMiddlePacket> extends SimpleChannelInboundHandler<ByteBuf> {

	protected final IPacketDataChannelIO io;
	protected final MiddlePacketRegistry<T> registry;

	protected AbstractPacketDecoder(IPacketDataChannelIO io, NetworkDataCache cache) {
		this.io = io;
		this.registry = new MiddlePacketRegistry<>(new MiddlePacketInitImpl(io, cache));
	}

	public MiddlePacketRegistry<T> getMiddlePacketRegistry() {
		return registry;
	}


	protected void decodeAndTransform(ByteBuf input) {
		registry.getTransformer(io.getNetworkState(), io.readPacketId(input)).decode(input);
	}

	protected void throwFailedTransformException(Exception exception, ByteBuf input) throws Exception {
		if (ServerPlatform.get().getMiscUtils().isDebugging()) {
			throw new DecoderException(MessageFormat.format(
				"Unable to transform or read serverbound middle packet(data {0})",
				Arrays.toString(BytesCodec.readAllBytes(input))
			), exception);
		} else {
			throw exception;
		}
	}

}
