package protocolsupport.zplatform.impl.pe;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.netty.Decompressor;

public class PEDecompressor extends MessageToMessageDecoder<ByteBuf> {

	private final Decompressor decompressor = Decompressor.create();

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
		decompressor.recycle();
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
		ByteBuf uncompresseddata = Unpooled.wrappedBuffer(decompressor.decompress(MiscSerializer.readAllBytes(buf)));
		while (uncompresseddata.isReadable()) {
			list.add(Unpooled.wrappedBuffer(MiscSerializer.readBytes(uncompresseddata, VarNumberSerializer.readVarInt(uncompresseddata))));
		}
	}

}
