package protocolsupport.zplatform.impl.pe;

import java.text.MessageFormat;
import java.util.List;
import java.util.zip.Inflater;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToMessageDecoder;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class PEDecompressor extends MessageToMessageDecoder<ByteBuf> {

	private static final int maxPacketLength = 2 << 21;

	private final Inflater inflater = new Inflater();
	private final byte[] decompressionbuffer = new byte[maxPacketLength];

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
		inflater.end();
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
		try {
			inflater.setInput(MiscSerializer.readAllBytes(buf));
			int decompressedlength = inflater.inflate(decompressionbuffer);
			if (!inflater.finished()) {
				throw new DecoderException(MessageFormat.format("Badly compressed packet - size is larger than protocol maximum of {0}", maxPacketLength));
			}
			ByteBuf uncompresseddata = Unpooled.wrappedBuffer(decompressionbuffer, 0, decompressedlength);
			while (uncompresseddata.isReadable()) {
				list.add(Unpooled.wrappedBuffer(MiscSerializer.readBytes(uncompresseddata, VarNumberSerializer.readVarInt(uncompresseddata))));
			}
		} finally {
			inflater.reset();
		}
	}

}
