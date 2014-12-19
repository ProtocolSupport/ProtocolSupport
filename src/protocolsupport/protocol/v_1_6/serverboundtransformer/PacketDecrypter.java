package protocolsupport.protocol.v_1_6.serverboundtransformer;

import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PacketDecrypter {

	private Cipher cipher;
	private byte[] buffer;

	protected PacketDecrypter(final Cipher cipher) {
		this.buffer = new byte[0];
		this.cipher = cipher;
	}

	private byte[] readToBuffer(final ByteBuf byteBuf) {
		final int readableBytes = byteBuf.readableBytes();
		if (this.buffer.length < readableBytes) {
			this.buffer = new byte[readableBytes];
		}
		byteBuf.readBytes(this.buffer, 0, readableBytes);
		return this.buffer;
	}

	protected ByteBuf decrypt(final ChannelHandlerContext ctx, final ByteBuf input) throws ShortBufferException {
		final int readableBytes = input.readableBytes();
		final byte[] bytes = this.readToBuffer(input);
		final ByteBuf heapBuffer = ctx.alloc().heapBuffer(this.cipher.getOutputSize(readableBytes));
		heapBuffer.writerIndex(this.cipher.update(bytes, 0, readableBytes, heapBuffer.array(), heapBuffer.arrayOffset()));
		return heapBuffer;
	}

}
