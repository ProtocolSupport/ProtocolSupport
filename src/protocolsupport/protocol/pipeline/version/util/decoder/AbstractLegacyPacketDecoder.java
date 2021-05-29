package protocolsupport.protocol.pipeline.version.util.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.utils.netty.ReplayingDecoderByteBuf;
import protocolsupport.utils.netty.ReplayingDecoderByteBuf.EOFSignal;

public abstract class AbstractLegacyPacketDecoder extends AbstractPacketDecoder {

	protected AbstractLegacyPacketDecoder(ConnectionImpl connection) {
		super(connection);
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
