package protocolsupport.utils.netty;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufProcessor;
import io.netty.buffer.SwappedByteBuf;
import io.netty.util.internal.StringUtil;

public final class ReplayingDecoderBuffer extends ByteBuf {

	public static final EOFSignal EOF = new EOFSignal();

	private ByteBuf buffer;
	private SwappedByteBuf swapped;

	public ReplayingDecoderBuffer() {
	}

	public ReplayingDecoderBuffer(ByteBuf buffer) {
		this.buffer = buffer;
	}

	public void setCumulation(final ByteBuf buffer) {
		this.buffer = buffer;
	}

	@Override
	public int capacity() {
		return Integer.MAX_VALUE;
	}

	@Override
	public ByteBuf capacity(final int newCapacity) {
		reject();
		return this;
	}

	@Override
	public int maxCapacity() {
		return this.capacity();
	}

	@Override
	public ByteBufAllocator alloc() {
		return buffer.alloc();
	}

	@Override
	public boolean isDirect() {
		return buffer.isDirect();
	}

	@Override
	public boolean hasArray() {
		return false;
	}

	@Override
	public byte[] array() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int arrayOffset() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasMemoryAddress() {
		return false;
	}

	@Override
	public long memoryAddress() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ByteBuf clear() {
		reject();
		return this;
	}

	@Override
	public boolean equals(final Object obj) {
		return this == obj;
	}

	@Override
	public int compareTo(final ByteBuf buffer) {
		reject();
		return 0;
	}

	@Override
	public ByteBuf copy() {
		reject();
		return this;
	}

	@Override
	public ByteBuf copy(final int index, final int length) {
		checkIndex(index, length);
		return buffer.copy(index, length);
	}

	@Override
	public ByteBuf discardReadBytes() {
		reject();
		return this;
	}

	@Override
	public ByteBuf ensureWritable(final int writableBytes) {
		reject();
		return this;
	}

	@Override
	public int ensureWritable(final int minWritableBytes, final boolean force) {
		reject();
		return 0;
	}

	@Override
	public ByteBuf duplicate() {
		reject();
		return this;
	}

	@Override
	public boolean getBoolean(final int index) {
		checkIndex(index, 1);
		return buffer.getBoolean(index);
	}

	@Override
	public byte getByte(final int index) {
		checkIndex(index, 1);
		return buffer.getByte(index);
	}

	@Override
	public short getUnsignedByte(final int index) {
		checkIndex(index, 1);
		return buffer.getUnsignedByte(index);
	}

	@Override
	public ByteBuf getBytes(final int index, final byte[] dst, final int dstIndex, final int length) {
		checkIndex(index, length);
		buffer.getBytes(index, dst, dstIndex, length);
		return this;
	}

	@Override
	public ByteBuf getBytes(final int index, final byte[] dst) {
		checkIndex(index, dst.length);
		buffer.getBytes(index, dst);
		return this;
	}

	@Override
	public ByteBuf getBytes(final int index, final ByteBuffer dst) {
		reject();
		return this;
	}

	@Override
	public ByteBuf getBytes(final int index, final ByteBuf dst, final int dstIndex, final int length) {
		checkIndex(index, length);
		buffer.getBytes(index, dst, dstIndex, length);
		return this;
	}

	@Override
	public ByteBuf getBytes(final int index, final ByteBuf dst, final int length) {
		reject();
		return this;
	}

	@Override
	public ByteBuf getBytes(final int index, final ByteBuf dst) {
		reject();
		return this;
	}

	@Override
	public int getBytes(final int index, final GatheringByteChannel out, final int length) {
		reject();
		return 0;
	}

	@Override
	public ByteBuf getBytes(final int index, final OutputStream out, final int length) {
		reject();
		return this;
	}

	@Override
	public int getInt(final int index) {
		checkIndex(index, 4);
		return buffer.getInt(index);
	}

	@Override
	public long getUnsignedInt(final int index) {
		checkIndex(index, 4);
		return buffer.getUnsignedInt(index);
	}

	@Override
	public long getLong(final int index) {
		checkIndex(index, 8);
		return buffer.getLong(index);
	}

	@Override
	public int getMedium(final int index) {
		checkIndex(index, 3);
		return buffer.getMedium(index);
	}

	@Override
	public int getUnsignedMedium(final int index) {
		checkIndex(index, 3);
		return buffer.getUnsignedMedium(index);
	}

	@Override
	public short getShort(final int index) {
		checkIndex(index, 2);
		return buffer.getShort(index);
	}

	@Override
	public int getUnsignedShort(final int index) {
		checkIndex(index, 2);
		return buffer.getUnsignedShort(index);
	}

	@Override
	public char getChar(final int index) {
		checkIndex(index, 2);
		return buffer.getChar(index);
	}

	@Override
	public float getFloat(final int index) {
		checkIndex(index, 4);
		return buffer.getFloat(index);
	}

	@Override
	public double getDouble(final int index) {
		checkIndex(index, 8);
		return buffer.getDouble(index);
	}

	@Override
	public int hashCode() {
		reject();
		return 0;
	}

	@Override
	public int indexOf(final int fromIndex, final int toIndex, final byte value) {
		if (fromIndex == toIndex) {
			return -1;
		}
		if (Math.max(fromIndex, toIndex) > buffer.writerIndex()) {
			throw EOF;
		}
		return buffer.indexOf(fromIndex, toIndex, value);
	}

	@Override
	public int bytesBefore(final byte value) {
		final int bytes = buffer.bytesBefore(value);
		if (bytes < 0) {
			throw EOF;
		}
		return bytes;
	}

	@Override
	public int bytesBefore(final int length, final byte value) {
		final int readerIndex = buffer.readerIndex();
		return this.bytesBefore(readerIndex, buffer.writerIndex() - readerIndex, value);
	}

	@Override
	public int bytesBefore(final int index, final int length, final byte value) {
		final int writerIndex = buffer.writerIndex();
		if (index >= writerIndex) {
			throw EOF;
		}
		if (index <= (writerIndex - length)) {
			return buffer.bytesBefore(index, length, value);
		}
		final int res = buffer.bytesBefore(index, writerIndex - index, value);
		if (res < 0) {
			throw EOF;
		}
		return res;
	}

	@Override
	public int forEachByte(final ByteBufProcessor processor) {
		final int ret = buffer.forEachByte(processor);
		if (ret < 0) {
			throw EOF;
		}
		return ret;
	}

	@Override
	public int forEachByte(final int index, final int length, final ByteBufProcessor processor) {
		final int writerIndex = buffer.writerIndex();
		if (index >= writerIndex) {
			throw EOF;
		}
		if (index <= (writerIndex - length)) {
			return buffer.forEachByte(index, length, processor);
		}
		final int ret = buffer.forEachByte(index, writerIndex - index, processor);
		if (ret < 0) {
			throw EOF;
		}
		return ret;
	}

	@Override
	public int forEachByteDesc(final ByteBufProcessor processor) {
		reject();
		return 0;
	}

	@Override
	public int forEachByteDesc(final int index, final int length, final ByteBufProcessor processor) {
		if ((index + length) > buffer.writerIndex()) {
			throw EOF;
		}
		return buffer.forEachByteDesc(index, length, processor);
	}

	@Override
	public ByteBuf markReaderIndex() {
		buffer.markReaderIndex();
		return this;
	}

	@Override
	public ByteBuf markWriterIndex() {
		reject();
		return this;
	}

	@Override
	public ByteOrder order() {
		return buffer.order();
	}

	@Override
	public ByteBuf order(final ByteOrder endianness) {
		if (endianness == null) {
			throw new NullPointerException("endianness");
		}
		if (endianness == this.order()) {
			return this;
		}
		SwappedByteBuf swapped = this.swapped;
		if (swapped == null) {
			swapped = (this.swapped = new SwappedByteBuf(this));
		}
		return swapped;
	}

	@Override
	public boolean isReadable() {
		return buffer.isReadable();
	}

	@Override
	public boolean isReadable(final int size) {
		return buffer.isReadable(size);
	}

	@Override
	public int readableBytes() {
		return buffer.readableBytes();
	}

	@Override
	public boolean readBoolean() {
		checkReadableBytes(1);
		return buffer.readBoolean();
	}

	@Override
	public byte readByte() {
		checkReadableBytes(1);
		return buffer.readByte();
	}

	@Override
	public short readUnsignedByte() {
		checkReadableBytes(1);
		return buffer.readUnsignedByte();
	}

	@Override
	public ByteBuf readBytes(final byte[] dst, final int dstIndex, final int length) {
		checkReadableBytes(length);
		buffer.readBytes(dst, dstIndex, length);
		return this;
	}

	@Override
	public ByteBuf readBytes(final byte[] dst) {
		checkReadableBytes(dst.length);
		buffer.readBytes(dst);
		return this;
	}

	@Override
	public ByteBuf readBytes(final ByteBuffer dst) {
		reject();
		return this;
	}

	@Override
	public ByteBuf readBytes(final ByteBuf dst, final int dstIndex, final int length) {
		checkReadableBytes(length);
		buffer.readBytes(dst, dstIndex, length);
		return this;
	}

	@Override
	public ByteBuf readBytes(final ByteBuf dst, final int length) {
		reject();
		return this;
	}

	@Override
	public ByteBuf readBytes(final ByteBuf dst) {
		checkReadableBytes(dst.writableBytes());
		buffer.readBytes(dst);
		return this;
	}

	@Override
	public int readBytes(final GatheringByteChannel out, final int length) {
		reject();
		return 0;
	}

	@Override
	public ByteBuf readBytes(final int length) {
		checkReadableBytes(length);
		return buffer.readBytes(length);
	}

	@Override
	public ByteBuf readSlice(final int length) {
		checkReadableBytes(length);
		return buffer.readSlice(length);
	}

	@Override
	public ByteBuf readBytes(final OutputStream out, final int length) {
		reject();
		return this;
	}

	@Override
	public int readerIndex() {
		return buffer.readerIndex();
	}

	@Override
	public ByteBuf readerIndex(final int readerIndex) {
		buffer.readerIndex(readerIndex);
		return this;
	}

	@Override
	public int readInt() {
		checkReadableBytes(4);
		return buffer.readInt();
	}

	@Override
	public long readUnsignedInt() {
		checkReadableBytes(4);
		return buffer.readUnsignedInt();
	}

	@Override
	public long readLong() {
		checkReadableBytes(8);
		return buffer.readLong();
	}

	@Override
	public int readMedium() {
		checkReadableBytes(3);
		return buffer.readMedium();
	}

	@Override
	public int readUnsignedMedium() {
		checkReadableBytes(3);
		return buffer.readUnsignedMedium();
	}

	@Override
	public short readShort() {
		checkReadableBytes(2);
		return buffer.readShort();
	}

	@Override
	public int readUnsignedShort() {
		checkReadableBytes(2);
		return buffer.readUnsignedShort();
	}

	@Override
	public char readChar() {
		checkReadableBytes(2);
		return buffer.readChar();
	}

	@Override
	public float readFloat() {
		checkReadableBytes(4);
		return buffer.readFloat();
	}

	@Override
	public double readDouble() {
		checkReadableBytes(8);
		return buffer.readDouble();
	}

	@Override
	public ByteBuf resetReaderIndex() {
		buffer.resetReaderIndex();
		return this;
	}

	@Override
	public ByteBuf resetWriterIndex() {
		reject();
		return this;
	}

	@Override
	public ByteBuf setBoolean(final int index, final boolean value) {
		reject();
		return this;
	}

	@Override
	public ByteBuf setByte(final int index, final int value) {
		reject();
		return this;
	}

	@Override
	public ByteBuf setBytes(final int index, final byte[] src, final int srcIndex, final int length) {
		reject();
		return this;
	}

	@Override
	public ByteBuf setBytes(final int index, final byte[] src) {
		reject();
		return this;
	}

	@Override
	public ByteBuf setBytes(final int index, final ByteBuffer src) {
		reject();
		return this;
	}

	@Override
	public ByteBuf setBytes(final int index, final ByteBuf src, final int srcIndex, final int length) {
		reject();
		return this;
	}

	@Override
	public ByteBuf setBytes(final int index, final ByteBuf src, final int length) {
		reject();
		return this;
	}

	@Override
	public ByteBuf setBytes(final int index, final ByteBuf src) {
		reject();
		return this;
	}

	@Override
	public int setBytes(final int index, final InputStream in, final int length) {
		reject();
		return 0;
	}

	@Override
	public ByteBuf setZero(final int index, final int length) {
		reject();
		return this;
	}

	@Override
	public int setBytes(final int index, final ScatteringByteChannel in, final int length) {
		reject();
		return 0;
	}

	@Override
	public ByteBuf setIndex(final int readerIndex, final int writerIndex) {
		reject();
		return this;
	}

	@Override
	public ByteBuf setInt(final int index, final int value) {
		reject();
		return this;
	}

	@Override
	public ByteBuf setLong(final int index, final long value) {
		reject();
		return this;
	}

	@Override
	public ByteBuf setMedium(final int index, final int value) {
		reject();
		return this;
	}

	@Override
	public ByteBuf setShort(final int index, final int value) {
		reject();
		return this;
	}

	@Override
	public ByteBuf setChar(final int index, final int value) {
		reject();
		return this;
	}

	@Override
	public ByteBuf setFloat(final int index, final float value) {
		reject();
		return this;
	}

	@Override
	public ByteBuf setDouble(final int index, final double value) {
		reject();
		return this;
	}

	@Override
	public ByteBuf skipBytes(final int length) {
		checkReadableBytes(length);
		buffer.skipBytes(length);
		return this;
	}

	@Override
	public ByteBuf slice() {
		reject();
		return this;
	}

	@Override
	public ByteBuf slice(final int index, final int length) {
		checkIndex(index, length);
		return buffer.slice(index, length);
	}

	@Override
	public int nioBufferCount() {
		return buffer.nioBufferCount();
	}

	@Override
	public ByteBuffer nioBuffer() {
		reject();
		return null;
	}

	@Override
	public ByteBuffer nioBuffer(final int index, final int length) {
		checkIndex(index, length);
		return buffer.nioBuffer(index, length);
	}

	@Override
	public ByteBuffer[] nioBuffers() {
		reject();
		return null;
	}

	@Override
	public ByteBuffer[] nioBuffers(final int index, final int length) {
		checkIndex(index, length);
		return buffer.nioBuffers(index, length);
	}

	@Override
	public ByteBuffer internalNioBuffer(final int index, final int length) {
		checkIndex(index, length);
		return buffer.internalNioBuffer(index, length);
	}

	@Override
	public String toString(final int index, final int length, final Charset charset) {
		checkIndex(index, length);
		return buffer.toString(index, length, charset);
	}

	@Override
	public String toString(final Charset charsetName) {
		reject();
		return null;
	}

	@Override
	public String toString() {
		return StringUtil.simpleClassName(this) + '(' + "ridx=" + this.readerIndex() + ", " + "widx=" + this.writerIndex() + ')';
	}

	@Override
	public boolean isWritable() {
		return false;
	}

	@Override
	public boolean isWritable(final int size) {
		return false;
	}

	@Override
	public int writableBytes() {
		return 0;
	}

	@Override
	public int maxWritableBytes() {
		return 0;
	}

	@Override
	public ByteBuf writeBoolean(final boolean value) {
		reject();
		return this;
	}

	@Override
	public ByteBuf writeByte(final int value) {
		reject();
		return this;
	}

	@Override
	public ByteBuf writeBytes(final byte[] src, final int srcIndex, final int length) {
		reject();
		return this;
	}

	@Override
	public ByteBuf writeBytes(final byte[] src) {
		reject();
		return this;
	}

	@Override
	public ByteBuf writeBytes(final ByteBuffer src) {
		reject();
		return this;
	}

	@Override
	public ByteBuf writeBytes(final ByteBuf src, final int srcIndex, final int length) {
		reject();
		return this;
	}

	@Override
	public ByteBuf writeBytes(final ByteBuf src, final int length) {
		reject();
		return this;
	}

	@Override
	public ByteBuf writeBytes(final ByteBuf src) {
		reject();
		return this;
	}

	@Override
	public int writeBytes(final InputStream in, final int length) {
		reject();
		return 0;
	}

	@Override
	public int writeBytes(final ScatteringByteChannel in, final int length) {
		reject();
		return 0;
	}

	@Override
	public ByteBuf writeInt(final int value) {
		reject();
		return this;
	}

	@Override
	public ByteBuf writeLong(final long value) {
		reject();
		return this;
	}

	@Override
	public ByteBuf writeMedium(final int value) {
		reject();
		return this;
	}

	@Override
	public ByteBuf writeZero(final int length) {
		reject();
		return this;
	}

	@Override
	public int writerIndex() {
		return buffer.writerIndex();
	}

	@Override
	public ByteBuf writerIndex(final int writerIndex) {
		reject();
		return this;
	}

	@Override
	public ByteBuf writeShort(final int value) {
		reject();
		return this;
	}

	@Override
	public ByteBuf writeChar(final int value) {
		reject();
		return this;
	}

	@Override
	public ByteBuf writeFloat(final float value) {
		reject();
		return this;
	}

	@Override
	public ByteBuf writeDouble(final double value) {
		reject();
		return this;
	}

	private void checkIndex(final int index, final int length) {
		if ((index + length) > buffer.writerIndex()) {
			throw EOF;
		}
	}

	private void checkReadableBytes(final int readableBytes) {
		if (buffer.readableBytes() < readableBytes) {
			throw EOF;
		}
	}

	@Override
	public ByteBuf discardSomeReadBytes() {
		reject();
		return this;
	}

	@Override
	public int refCnt() {
		return buffer.refCnt();
	}

	@Override
	public ByteBuf retain() {
		reject();
		return this;
	}

	@Override
	public ByteBuf retain(final int increment) {
		reject();
		return this;
	}

	@Override
	public boolean release() {
		reject();
		return false;
	}

	@Override
	public boolean release(final int decrement) {
		reject();
		return false;
	}

	@Override
	public ByteBuf unwrap() {
		reject();
		return this;
	}

	private static void reject() {
		throw new UnsupportedOperationException("not a replayable operation");
	}

	public static final class EOFSignal extends RuntimeException {

		private static final long serialVersionUID = 1L;

		@Override
		public EOFSignal fillInStackTrace() {
			return this;
		}

	}

}
