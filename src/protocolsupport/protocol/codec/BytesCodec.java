package protocolsupport.protocol.codec;

import io.netty.buffer.ByteBuf;

public class BytesCodec {

	public static byte[] readAllBytes(ByteBuf buf) {
		return BytesCodec.readBytes(buf, buf.readableBytes());
	}

	public static ByteBuf readAllBytesSlice(ByteBuf from) {
		return from.readSlice(from.readableBytes());
	}

	public static ByteBuf readAllBytesSlice(ByteBuf buf, int limit) {
		MiscDataCodec.checkLimit(buf.readableBytes(), limit);
		return readAllBytesSlice(buf);
	}

	public static byte[] readBytes(ByteBuf buf, int length) {
		byte[] result = new byte[length];
		buf.readBytes(result);
		return result;
	}

}
