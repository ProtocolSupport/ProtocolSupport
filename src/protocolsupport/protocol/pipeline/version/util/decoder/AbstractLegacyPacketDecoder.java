package protocolsupport.protocol.pipeline.version.util.decoder;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.typeremapper.packet.AnimatePacketReorderer;
import protocolsupport.utils.netty.ReplayingDecoderBuffer;
import protocolsupport.utils.netty.ReplayingDecoderBuffer.EOFSignal;
import protocolsupport.utils.recyclable.RecyclableCollection;

public abstract class AbstractLegacyPacketDecoder extends AbstractPacketDecoder {

	public AbstractLegacyPacketDecoder(ConnectionImpl connection) {
		super(connection);
	}

	protected final ReplayingDecoderBuffer buffer = new ReplayingDecoderBuffer(Unpooled.buffer());
	protected final AnimatePacketReorderer animateReorderer = new AnimatePacketReorderer();

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		buffer.writeBytes(input);
		while (buffer.isReadable()) {
			buffer.markReaderIndex();
			try {
				decodeAndTransform(ctx, buffer, list);
			} catch (EOFSignal signal) {
				buffer.resetReaderIndex();
				break;
			} catch (Exception e) {
				throwFailedTransformException(e, buffer);
			}
		}
		buffer.discardReadBytes();
	}

	@Override
	protected RecyclableCollection<ServerBoundPacketData> processPackets(Channel channel, RecyclableCollection<ServerBoundPacketData> data) {
		return animateReorderer.orderPackets(data);
	}

	@Override
	protected int readPacketId(ByteBuf buffer) {
		return buffer.readUnsignedByte();
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
		animateReorderer.release();
	}

}
