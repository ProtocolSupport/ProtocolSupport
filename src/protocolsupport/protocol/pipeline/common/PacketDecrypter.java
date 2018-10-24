package protocolsupport.protocol.pipeline.common;

import java.util.List;

import javax.crypto.Cipher;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import protocolsupport.utils.netty.ReusableReadHeapBuffer;
import protocolsupport.utils.netty.ReusableWriteHeapBuffer;

public class PacketDecrypter extends ByteToMessageDecoder {

	protected final Cipher cipher;
	public PacketDecrypter(Cipher cipher) {
		this.cipher = cipher;
	}

	protected final ReusableReadHeapBuffer readBuffer = new ReusableReadHeapBuffer();
	protected final ReusableWriteHeapBuffer writeBuffer = new ReusableWriteHeapBuffer();

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
		if (!buf.isReadable()) {
			return;
		}
		readBuffer.readFrom(buf, ((lInArray, lInOffset, lInLength) -> {
			int writeLength = cipher.getOutputSize(lInLength);
			ByteBuf out = ctx.alloc().heapBuffer(writeLength);
			list.add(out);
			writeBuffer.writeTo(
				out, writeLength,
				((lOutArray, lOutOffset, lOutLength) -> cipher.update(lInArray, lInOffset, lInLength, lOutArray, lOutOffset)
			));
		}));
	}

}
