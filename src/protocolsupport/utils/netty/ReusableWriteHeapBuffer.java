package protocolsupport.utils.netty;

import io.netty.buffer.ByteBuf;

public class ReusableWriteHeapBuffer {

	protected byte[] outBuffer = {};

	public void writeTo(ByteBuf to, int maxWriteLength, BufferOperation operation) throws Exception {
		if (to.hasArray()) {
			to.ensureWritable(maxWriteLength);
			int widx = to.writerIndex();
			to.writerIndex(widx + operation.write(to.array(), to.arrayOffset() + widx, maxWriteLength));
		} else {
			byte[] buffer = getBuffer(maxWriteLength);
			to.writeBytes(buffer, 0, operation.write(buffer, 0, maxWriteLength));
		}
	}

	public byte[] getBuffer(int maxLength) {
		if (outBuffer.length < maxLength) {
			outBuffer = new byte[maxLength];
		}
		return outBuffer;
	}

	@FunctionalInterface
	public static interface BufferOperation {

		public int write(byte[] buf, int offset, int length) throws Exception;

	}

}
