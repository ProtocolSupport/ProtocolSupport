package protocolsupport.protocol.transformer.v_1_5.serverboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class PacketDecrypter {

	private Cipher cipher;
	private byte[] buffer;

	protected PacketDecrypter(final Cipher cipher) {
		buffer = new byte[0];
		this.cipher = cipher;
	}

	private byte[] readToBuffer(final ByteBuf byteBuf) {
		final int readableBytes = byteBuf.readableBytes();
		if (buffer.length < readableBytes) {
			buffer = new byte[readableBytes];
		}
		byteBuf.readBytes(buffer, 0, readableBytes);
		return buffer;
	}

	protected ByteBuf decrypt(final ChannelHandlerContext ctx, final ByteBuf input) throws ShortBufferException {
		final int readableBytes = input.readableBytes();
		final byte[] bytes = readToBuffer(input);
		final ByteBuf heapBuffer = ctx.alloc().heapBuffer(cipher.getOutputSize(readableBytes));
		heapBuffer.writerIndex(cipher.update(bytes, 0, readableBytes, heapBuffer.array(), heapBuffer.arrayOffset()));
		return heapBuffer;
	}

}
