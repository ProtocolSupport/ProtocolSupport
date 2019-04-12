package protocolsupport.zplatform.impl.pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.netty.Compressor;

public class PECompressor extends MessageToByteEncoder<ByteBuf> {

	private final ByteBuf packbuffer = Unpooled.buffer();
	private final Compressor compressor = Compressor.create();

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
		packbuffer.release();
		compressor.recycle();
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf buf, ByteBuf out) throws Exception {
		packbuffer.clear();
		VarNumberSerializer.writeVarInt(packbuffer, buf.readableBytes());
		packbuffer.writeBytes(buf);
		byte[] bytes = MiscSerializer.readAllBytes(packbuffer);
		out.writeBytes(compressor.compress(bytes, 0, bytes.length));
	}

}
