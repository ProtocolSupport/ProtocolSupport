package protocolsupport.protocol.pipeline.common;

import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class PacketDecrypter extends ByteToMessageDecoder {

	private static final byte[] empty = new byte[0];

	private Cipher cipher;
	private byte[] buffer = empty;

	public PacketDecrypter(Cipher cipher) {
		this.cipher = cipher;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> packet) throws Exception {
		packet.add(decrypt(ctx, buf));
	}

	private byte[] readToBuffer(final ByteBuf byteBuf) {
		final int readableBytes = byteBuf.readableBytes();
		if (buffer.length < readableBytes) {
			buffer = new byte[readableBytes];
		}
		byteBuf.readBytes(buffer, 0, readableBytes);
		return buffer;
	}

	protected ByteBuf decrypt(ChannelHandlerContext ctx, ByteBuf input) throws ShortBufferException {
		int readableBytes = input.readableBytes();
		byte[] bytes = readToBuffer(input);
		ByteBuf heapBuffer = ctx.alloc().heapBuffer(cipher.getOutputSize(readableBytes));
		heapBuffer.writerIndex(cipher.update(bytes, 0, readableBytes, heapBuffer.array(), heapBuffer.arrayOffset()));
		return heapBuffer;
	}

}
