package protocolsupport.protocol.pipeline.version.util.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.pipeline.IPacketCodec;

public abstract class AbstractModernPacketDecoder extends AbstractPacketDecoder {

	public AbstractModernPacketDecoder(ConnectionImpl connection, IPacketCodec codec) {
		super(connection, codec);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf input) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		try {
			decodeAndTransform(ctx, input);
			if (input.isReadable()) {
				throw new DecoderException("Did not read all data from packet, bytes left: " + input.readableBytes());
			}
		} catch (Exception e) {
			throwFailedTransformException(e, input);
		}
	}

}
