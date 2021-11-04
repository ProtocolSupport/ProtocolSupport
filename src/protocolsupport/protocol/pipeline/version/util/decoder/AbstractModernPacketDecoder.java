package protocolsupport.protocol.pipeline.version.util.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import protocolsupport.protocol.packet.middle.base.serverbound.IServerboundMiddlePacket;
import protocolsupport.protocol.pipeline.IPacketDataChannelIO;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;

public abstract class AbstractModernPacketDecoder<T extends IServerboundMiddlePacket> extends AbstractPacketDecoder<T> {

	protected AbstractModernPacketDecoder(IPacketDataChannelIO io, NetworkDataCache cache) {
		super(io, cache);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf input) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		try {
			decodeAndTransform(input);
			if (input.isReadable()) {
				throw new DecoderException("Data not read fully, bytes left " + input.readableBytes());
			}
		} catch (Exception e) {
			try {
				input.readerIndex(0);
				throwFailedTransformException(e, input);
			} finally {
				input.skipBytes(input.readableBytes());
			}
		}
	}

}
