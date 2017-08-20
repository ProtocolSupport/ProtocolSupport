package protocolsupport.zplatform.impl.pe;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.Compressor;

public class PECompressor extends MessageToByteEncoder<ByteBuf> {

	private final ByteBuf packbuffer = Allocator.allocateBuffer();
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
		out.writeBytes(compressor.compress(MiscSerializer.readAllBytes(packbuffer)));
	}

}
