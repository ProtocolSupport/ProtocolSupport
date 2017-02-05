package protocolsupport.protocol.pipeline.common;

import javax.crypto.Cipher;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncrypter extends MessageToByteEncoder<ByteBuf> {

	private final Cipher cipher;
	private byte[] inBuffer = {};
	private byte[] outBuffer = {};

	public PacketEncrypter(Cipher cipher) {
		this.cipher = cipher;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
		if (!in.isReadable()) {
			return;
		}
		int readableBytes = in.readableBytes();
		byte[] inBytes = readIn(in);
		int outputSize = cipher.getOutputSize(readableBytes);
		if (outBuffer.length < outputSize) {
			outBuffer = new byte[outputSize];
		}
		out.writeBytes(outBuffer, 0, cipher.update(inBytes, 0, readableBytes, outBuffer));
	}

	private byte[] readIn(final ByteBuf in) {
		final int readableBytes = in.readableBytes();
		if (inBuffer.length < readableBytes) {
			inBuffer = new byte[readableBytes];
		}
		in.readBytes(inBuffer, 0, readableBytes);
		return inBuffer;
	}

}
