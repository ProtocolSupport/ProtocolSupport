package protocolsupport.protocol.pipeline.version.util.decoder;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class AbstractModernPacketDecoder extends AbstractPacketDecoder {

	public AbstractModernPacketDecoder(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected int readPacketId(ByteBuf buffer) {
		return VarNumberSerializer.readVarInt(buffer);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> out) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		try {
			decodeAndTransform(ctx, input, out);
			if (input.isReadable()) {
				throw new DecoderException("Did not read all data from packet, bytes left: " + input.readableBytes());
			}
		} catch (Exception e) {
			throwFailedTransformException(e, input);
		}
	}

}
