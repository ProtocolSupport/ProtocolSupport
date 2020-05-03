package protocolsupport.protocol.pipeline.version.util.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketDataCodec;
import protocolsupport.protocol.typeremapper.packet.AnimatePacketReorderer;
import protocolsupport.utils.netty.ReplayingDecoderBuffer;
import protocolsupport.utils.netty.ReplayingDecoderBuffer.EOFSignal;

public abstract class AbstractLegacyPacketDecoder extends AbstractPacketDecoder {

	public AbstractLegacyPacketDecoder(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void init(PacketDataCodec codec) {
		super.init(codec);
		codec.addServerboundPacketProcessor(new AnimatePacketReorderer(codec));
	}

	protected final ReplayingDecoderBuffer buffer = new ReplayingDecoderBuffer(Unpooled.buffer());

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf input) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		buffer.writeBytes(input);
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
		buffer.discardReadBytes();
	}

}
