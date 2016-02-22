package protocolsupport.protocol.transformer.v_1_4;

import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class PacketDecrypter extends ByteToMessageDecoder {

	private Cipher cipher;
	private byte[] buffer;

	public PacketDecrypter(final Cipher cipher) {
		buffer = new byte[0];
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

	private ByteBuf decrypt(final ChannelHandlerContext ctx, final ByteBuf input) throws ShortBufferException {
		final int readableBytes = input.readableBytes();
		final byte[] bytes = readToBuffer(input);
		final ByteBuf heapBuffer = ctx.alloc().heapBuffer(cipher.getOutputSize(readableBytes));
		heapBuffer.writerIndex(cipher.update(bytes, 0, readableBytes, heapBuffer.array(), heapBuffer.arrayOffset()));
		return heapBuffer;
	}

}
