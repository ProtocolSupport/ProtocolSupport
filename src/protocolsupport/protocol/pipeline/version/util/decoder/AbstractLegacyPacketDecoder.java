package protocolsupport.protocol.pipeline.version.util.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.protocol.packet.middle.base.serverbound.IServerboundMiddlePacket;
import protocolsupport.protocol.pipeline.IPacketDataChannelIO;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.utils.netty.ReplayingDecoderByteBuf;
import protocolsupport.utils.netty.ReplayingDecoderByteBuf.EOFSignal;

public abstract class AbstractLegacyPacketDecoder<T extends IServerboundMiddlePacket> extends AbstractPacketDecoder<T> {

	protected AbstractLegacyPacketDecoder(IPacketDataChannelIO io, NetworkDataCache cache) {
		super(io, cache);
	}

	protected final ReplayingDecoderByteBuf buffer = new ReplayingDecoderByteBuf(Unpooled.buffer());

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf input) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		buffer.unwrap().writeBytes(input);
		while (buffer.isReadable()) {
			buffer.markReaderIndex();
			try {
				decodeAndTransform(buffer);
			} catch (EOFSignal signal) {
				buffer.resetReaderIndex();
				break;
			} catch (Exception e) {
				try {
					buffer.resetReaderIndex();
					throwFailedTransformException(e, buffer);
				} finally {
					input.skipBytes(input.readableBytes());
				}
			}
		}
		buffer.unwrap().discardReadBytes();
	}

}
