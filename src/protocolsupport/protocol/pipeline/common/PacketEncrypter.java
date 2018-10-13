package protocolsupport.protocol.pipeline.common;

import javax.crypto.Cipher;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import protocolsupport.utils.netty.ReusableReadHeapBuffer;
import protocolsupport.utils.netty.ReusableWriteHeapBuffer;

public class PacketEncrypter extends MessageToByteEncoder<ByteBuf> {

	protected final Cipher cipher;
	public PacketEncrypter(Cipher cipher) {
		this.cipher = cipher;
	}

	protected final ReusableReadHeapBuffer readBuffer = new ReusableReadHeapBuffer();
	protected final ReusableWriteHeapBuffer writerBuffer = new ReusableWriteHeapBuffer();

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
		if (!in.isReadable()) {
			return;
		}
		readBuffer.readFrom(in, ((lInArray, lInOffset, lInLength) -> {
			int writeLength = cipher.getOutputSize(lInLength);
			writerBuffer.writeTo(
				out, writeLength,
				((lOutArray, lOutOffset, lOutLength) -> cipher.update(lInArray, lInOffset, lInLength, lOutArray, lOutOffset)
			));
		}));
	}

}
