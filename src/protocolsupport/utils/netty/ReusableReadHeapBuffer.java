package protocolsupport.utils.netty;

import io.netty.buffer.ByteBuf;

public class ReusableReadHeapBuffer {

	protected byte[] inBuffer = {};

	public void readFrom(ByteBuf from, ReadOperation operation) throws Exception {
		int length = from.readableBytes();
		if (from.hasArray()) {
			int ridx = from.readerIndex();
			operation.read(from.array(), from.arrayOffset() + ridx, length);
			from.readerIndex(ridx + length);
		} else {
			byte[] buffer = getBuffer(length);
			from.readBytes(buffer, 0, length);
			operation.read(buffer, 0, length);
		}
	}

	public byte[] getBuffer(int maxLength) {
		if (inBuffer.length < maxLength) {
			inBuffer = new byte[maxLength];
		}
		return inBuffer;
	}

	@FunctionalInterface
	public static interface ReadOperation {

		public void read(byte[] array, int offset, int length) throws Exception;

	}

}
