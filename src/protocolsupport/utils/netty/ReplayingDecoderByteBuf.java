package protocolsupport.utils.netty;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.SwappedByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.StringUtil;

@SuppressWarnings("deprecation")
public final class ReplayingDecoderByteBuf extends ByteBuf {

	public static final EOFSignal EOF = new EOFSignal();

	private ByteBuf buf;
	private SwappedByteBuf swapped;

	public ReplayingDecoderByteBuf(ByteBuf buffer) {
		this.buf = buffer;
	}

	@Override
	public String toString() {
		return StringUtil.simpleClassName(this) + '(' + "ridx=" + this.readerIndex() + ", widx=" + this.writerIndex() + ')';
	}

	@Override
	public boolean equals(final Object o) {
		return this == o;
	}

	@Override
	public ByteBufAllocator alloc() {
		return this.buf.alloc();
	}

	@Override
	public ByteBuf unwrap() {
		return buf;
	}

	@Override
	public int capacity() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int maxCapacity() {
		return this.capacity();
	}

	@Override
	public boolean isReadOnly() {
		return false;
	}

	@Override
	public ByteBuf asReadOnly() {
		return Unpooled.unmodifiableBuffer(this);
	}

	@Override
	public boolean isDirect() {
		return false;
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
	public int refCnt() {
		return this.buf.refCnt();
	}

	@Override
	public ByteBuf touch() {
		this.buf.touch();
		return this;
	}

	@Override
	public ByteBuf touch(final Object o) {
		this.buf.touch(o);
		return this;
	}

	@Override
	public ByteBuf markReaderIndex() {
		this.buf.markReaderIndex();
		return this;
	}

	@Override
	public ByteBuf resetReaderIndex() {
		this.buf.resetReaderIndex();
		return this;
	}

	@Override
	public int readerIndex() {
		return this.buf.readerIndex();
	}

	@Override
	public ByteBuf readerIndex(final int index) {
		this.buf.readerIndex(index);
		return this;
	}

	@Override
	public int writerIndex() {
		return this.buf.writerIndex();
	}

	@Override
	public boolean isReadable() {
		return this.buf.isReadable();
	}

	@Override
	public boolean isReadable(final int length) {
		return this.buf.isReadable(length);
	}

	@Override
	public int readableBytes() {
		return this.buf.readableBytes();
	}

	@Override
	public ByteOrder order() {
		return this.buf.order();
	}

	@Override
	public ByteBuf order(final ByteOrder byteOrder) {
		if (byteOrder == null) {
			throw new NullPointerException("endianness");
		}
		if (byteOrder == this.order()) {
			return this;
		}
		SwappedByteBuf swapped = this.swapped;
		if (swapped == null) {
			swapped = (this.swapped = new SwappedByteBuf(this));
		}
		return swapped;
	}

	@Override
	public int indexOf(final int fromIndex, final int toIndex, final byte b) {
		if (fromIndex == toIndex) {
			return -1;
		}
		if (Math.max(fromIndex, toIndex) > this.buf.writerIndex()) {
			throw EOF;
		}
		return this.buf.indexOf(fromIndex, toIndex, b);
	}

	@Override
	public int bytesBefore(final byte b) {
		final int bytesBefore = this.buf.bytesBefore(b);
		if (bytesBefore < 0) {
			throw EOF;
		}
		return bytesBefore;
	}

	@Override
	public int bytesBefore(final int length, final byte b) {
		return this.bytesBefore(this.buf.readerIndex(), length, b);
	}

	@Override
	public int bytesBefore(final int index, final int length, final byte b) {
		final int writerIndex = this.buf.writerIndex();
		if (index >= writerIndex) {
			throw EOF;
		}
		if (index <= (writerIndex - length)) {
			return this.buf.bytesBefore(index, length, b);
		}
		final int bytesBefore = this.buf.bytesBefore(index, writerIndex - index, b);
		if (bytesBefore < 0) {
			throw EOF;
		}
		return bytesBefore;
	}

	@Override
	public ByteBuf copy() {
		return this.buf.copy();
	}

	@Override
	public ByteBuf copy(final int index, final int length) {
		this.checkIndex(index, length);
		return this.buf.copy(index, length);
	}

	@Override
	public ByteBuf slice() {
		return this.buf.slice();
	}

	@Override
	public ByteBuf retainedSlice() {
		return this.buf.retainedSlice();
	}

	@Override
	public ByteBuf slice(final int index, final int length) {
		this.checkIndex(index, length);
		return this.buf.slice(index, length);
	}

	@Override
	public ByteBuf retainedSlice(final int index, final int length) {
		this.checkIndex(index, length);
		return this.buf.retainedSlice(index, length);
	}

	@Override
	public ByteBuf skipBytes(final int length) {
		this.checkReadableBytes(length);
		this.buf.skipBytes(length);
		return this;
	}

	@Override
	public ByteBuf getBytes(final int index, final byte[] array, final int dstIndex, final int length) {
		this.checkIndex(index, length);
		this.buf.getBytes(index, array, dstIndex, length);
		return this;
	}

	@Override
	public ByteBuf getBytes(final int index, final byte[] array) {
		this.checkIndex(index, array.length);
		this.buf.getBytes(index, array);
		return this;
	}

	@Override
	public ByteBuf getBytes(final int index, final ByteBuf byteBuf, final int dstIndex, final int length) {
		this.checkIndex(index, length);
		this.buf.getBytes(index, byteBuf, dstIndex, length);
		return this;
	}

	@Override
	public ByteBuf getBytes(final int index, final ByteBuf byteBuf, final int length) {
		this.checkIndex(index, length);
		this.buf.getBytes(index, byteBuf, length);
		return this;
	}

	@Override
	public ByteBuf getBytes(final int index, final ByteBuf byteBuf) {
		throw reject();
	}

	@Override
	public ByteBuf getBytes(final int index, final ByteBuffer byteBuffer) {
		throw reject();
	}

	@Override
	public int getBytes(final int index, final GatheringByteChannel gatheringByteChannel, final int length) {
		throw reject();
	}

	@Override
	public int getBytes(final int index, final FileChannel fileChannel, final long position, final int length) {
		throw reject();
	}

	@Override
	public ByteBuf getBytes(final int index, final OutputStream outputStream, final int length) {
		throw reject();
	}

	@Override
	public int forEachByte(final ByteProcessor byteProcessor) {
		final int forEachByte = this.buf.forEachByte(byteProcessor);
		if (forEachByte < 0) {
			throw EOF;
		}
		return forEachByte;
	}

	@Override
	public int forEachByte(final int index, final int length, final ByteProcessor byteProcessor) {
		final int writerIndex = this.buf.writerIndex();
		if (index >= writerIndex) {
			throw EOF;
		}
		if (index <= (writerIndex - length)) {
			return this.buf.forEachByte(index, length, byteProcessor);
		}
		final int forEachByte = this.buf.forEachByte(index, writerIndex - index, byteProcessor);
		if (forEachByte < 0) {
			throw EOF;
		}
		return forEachByte;
	}

	@Override
	public int forEachByteDesc(final ByteProcessor byteProcessor) {
		return this.buf.forEachByteDesc(byteProcessor);
	}

	@Override
	public int forEachByteDesc(final int index, final int length, final ByteProcessor byteProcessor) {
		if ((index + length) > this.buf.writerIndex()) {
			throw EOF;
		}
		return this.buf.forEachByteDesc(index, length, byteProcessor);
	}

	@Override
	public CharSequence getCharSequence(final int index, final int length, final Charset charset) {
		this.checkIndex(index, length);
		return this.buf.getCharSequence(index, length, charset);
	}

	@Override
	public String toString(final int index, final int length, final Charset charset) {
		this.checkIndex(index, length);
		return this.buf.toString(index, length, charset);
	}

	@Override
	public String toString(final Charset charset) {
		return this.buf.toString(charset);
	}

	@Override
	public boolean getBoolean(final int index) {
		this.checkIndex(index, Byte.BYTES);
		return this.buf.getBoolean(index);
	}

	@Override
	public byte getByte(final int index) {
		this.checkIndex(index, Byte.BYTES);
		return this.buf.getByte(index);
	}

	@Override
	public short getUnsignedByte(final int index) {
		this.checkIndex(index, Byte.BYTES);
		return this.buf.getUnsignedByte(index);
	}

	@Override
	public short getShort(final int index) {
		this.checkIndex(index, Short.BYTES);
		return this.buf.getShort(index);
	}

	@Override
	public short getShortLE(final int index) {
		this.checkIndex(index, Short.BYTES);
		return this.buf.getShortLE(index);
	}

	@Override
	public int getUnsignedShort(final int index) {
		this.checkIndex(index, Short.BYTES);
		return this.buf.getUnsignedShort(index);
	}

	@Override
	public int getUnsignedShortLE(final int index) {
		this.checkIndex(index, Short.BYTES);
		return this.buf.getUnsignedShortLE(index);
	}

	@Override
	public int getMedium(final int n) {
		this.checkIndex(n, 3);
		return this.buf.getMedium(n);
	}

	@Override
	public int getMediumLE(final int n) {
		this.checkIndex(n, 3);
		return this.buf.getMediumLE(n);
	}

	@Override
	public int getUnsignedMedium(final int n) {
		this.checkIndex(n, 3);
		return this.buf.getUnsignedMedium(n);
	}

	@Override
	public int getUnsignedMediumLE(final int n) {
		this.checkIndex(n, 3);
		return this.buf.getUnsignedMediumLE(n);
	}

	@Override
	public int getInt(final int index) {
		this.checkIndex(index, Integer.BYTES);
		return this.buf.getInt(index);
	}

	@Override
	public int getIntLE(final int index) {
		this.checkIndex(index, Integer.BYTES);
		return this.buf.getIntLE(index);
	}

	@Override
	public long getUnsignedInt(final int index) {
		this.checkIndex(index, Integer.BYTES);
		return this.buf.getUnsignedInt(index);
	}

	@Override
	public long getUnsignedIntLE(final int index) {
		this.checkIndex(index, Integer.BYTES);
		return this.buf.getUnsignedIntLE(index);
	}

	@Override
	public long getLong(final int index) {
		this.checkIndex(index, Long.BYTES);
		return this.buf.getLong(index);
	}

	@Override
	public long getLongLE(final int index) {
		this.checkIndex(index, Long.BYTES);
		return this.buf.getLongLE(index);
	}

	@Override
	public float getFloat(final int index) {
		this.checkIndex(index, Float.BYTES);
		return this.buf.getFloat(index);
	}

	@Override
	public double getDouble(final int index) {
		this.checkIndex(index, Double.BYTES);
		return this.buf.getDouble(index);
	}

	@Override
	public char getChar(final int index) {
		this.checkIndex(index, 2);
		return this.buf.getChar(index);
	}

	@Override
	public boolean readBoolean() {
		this.checkReadableBytes(1);
		return this.buf.readBoolean();
	}

	@Override
	public byte readByte() {
		this.checkReadableBytes(1);
		return this.buf.readByte();
	}

	@Override
	public short readUnsignedByte() {
		this.checkReadableBytes(1);
		return this.buf.readUnsignedByte();
	}

	@Override
	public ByteBuf readBytes(final byte[] array, final int n, final int n2) {
		this.checkReadableBytes(n2);
		this.buf.readBytes(array, n, n2);
		return this;
	}

	@Override
	public ByteBuf readBytes(final byte[] array) {
		this.checkReadableBytes(array.length);
		this.buf.readBytes(array);
		return this;
	}

	@Override
	public ByteBuf readBytes(final ByteBuffer byteBuffer) {
		throw reject();
	}

	@Override
	public ByteBuf readBytes(final ByteBuf byteBuf, final int n, final int n2) {
		this.checkReadableBytes(n2);
		this.buf.readBytes(byteBuf, n, n2);
		return this;
	}

	@Override
	public ByteBuf readBytes(final ByteBuf byteBuf, final int n) {
		throw reject();
	}

	@Override
	public ByteBuf readBytes(final ByteBuf byteBuf) {
		this.checkReadableBytes(byteBuf.writableBytes());
		this.buf.readBytes(byteBuf);
		return this;
	}

	@Override
	public int readBytes(final GatheringByteChannel gatheringByteChannel, final int n) {
		throw reject();
	}

	@Override
	public int readBytes(final FileChannel fileChannel, final long n, final int n2) {
		throw reject();
	}

	@Override
	public ByteBuf readBytes(final int n) {
		this.checkReadableBytes(n);
		return this.buf.readBytes(n);
	}

	@Override
	public ByteBuf readSlice(final int n) {
		this.checkReadableBytes(n);
		return this.buf.readSlice(n);
	}

	@Override
	public ByteBuf readRetainedSlice(final int n) {
		this.checkReadableBytes(n);
		return this.buf.readRetainedSlice(n);
	}

	@Override
	public ByteBuf readBytes(final OutputStream outputStream, final int n) {
		throw reject();
	}

	@Override
	public short readShort() {
		this.checkReadableBytes(Short.BYTES);
		return this.buf.readShort();
	}

	@Override
	public short readShortLE() {
		this.checkReadableBytes(Short.BYTES);
		return this.buf.readShortLE();
	}

	@Override
	public int readUnsignedShort() {
		this.checkReadableBytes(Short.BYTES);
		return this.buf.readUnsignedShort();
	}

	@Override
	public int readUnsignedShortLE() {
		this.checkReadableBytes(Short.BYTES);
		return this.buf.readUnsignedShortLE();
	}

	@Override
	public int readMedium() {
		this.checkReadableBytes(3);
		return this.buf.readMedium();
	}

	@Override
	public int readMediumLE() {
		this.checkReadableBytes(3);
		return this.buf.readMediumLE();
	}

	@Override
	public int readUnsignedMedium() {
		this.checkReadableBytes(3);
		return this.buf.readUnsignedMedium();
	}

	@Override
	public int readUnsignedMediumLE() {
		this.checkReadableBytes(3);
		return this.buf.readUnsignedMediumLE();
	}

	@Override
	public int readInt() {
		this.checkReadableBytes(Integer.BYTES);
		return this.buf.readInt();
	}

	@Override
	public int readIntLE() {
		this.checkReadableBytes(Integer.BYTES);
		return this.buf.readIntLE();
	}

	@Override
	public long readUnsignedInt() {
		this.checkReadableBytes(Integer.BYTES);
		return this.buf.readUnsignedInt();
	}

	@Override
	public long readUnsignedIntLE() {
		this.checkReadableBytes(Integer.BYTES);
		return this.buf.readUnsignedIntLE();
	}

	@Override
	public long readLong() {
		this.checkReadableBytes(Long.BYTES);
		return this.buf.readLong();
	}

	@Override
	public long readLongLE() {
		this.checkReadableBytes(Long.BYTES);
		return this.buf.readLongLE();
	}

	@Override
	public float readFloat() {
		this.checkReadableBytes(Float.BYTES);
		return this.buf.readFloat();
	}

	@Override
	public double readDouble() {
		this.checkReadableBytes(Double.BYTES);
		return this.buf.readDouble();
	}

	@Override
	public char readChar() {
		this.checkReadableBytes(2);
		return this.buf.readChar();
	}

	@Override
	public CharSequence readCharSequence(final int length, final Charset charset) {
		this.checkReadableBytes(length);
		return this.buf.readCharSequence(length, charset);
	}


	@Override
	public boolean isWritable() {
		return false;
	}

	@Override
	public boolean isWritable(final int n) {
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
	public int nioBufferCount() {
		throw reject();
	}

	@Override
	public ByteBuffer nioBuffer() {
		throw reject();
	}

	@Override
	public ByteBuffer nioBuffer(final int n, final int n2) {
		throw reject();
	}

	@Override
	public ByteBuffer[] nioBuffers() {
		throw reject();
	}

	@Override
	public ByteBuffer[] nioBuffers(final int n, final int n2) {
		throw reject();
	}

	@Override
	public ByteBuffer internalNioBuffer(final int n, final int n2) {
		throw reject();
	}

	@Override
	public ByteBuf setBoolean(final int n, final boolean b) {
		throw reject();
	}

	@Override
	public ByteBuf setByte(final int n, final int n2) {
		throw reject();
	}

	@Override
	public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
		throw reject();
	}

	@Override
	public ByteBuf setBytes(final int n, final byte[] array) {
		throw reject();
	}

	@Override
	public ByteBuf setBytes(final int n, final ByteBuffer byteBuffer) {
		throw reject();
	}

	@Override
	public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
		throw reject();
	}

	@Override
	public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2) {
		throw reject();
	}

	@Override
	public ByteBuf setBytes(final int n, final ByteBuf byteBuf) {
		throw reject();
	}

	@Override
	public int setBytes(final int n, final InputStream inputStream, final int n2) {
		throw reject();
	}

	@Override
	public ByteBuf setZero(final int n, final int n2) {
		throw reject();
	}

	@Override
	public int setBytes(final int n, final ScatteringByteChannel scatteringByteChannel, final int n2) {
		throw reject();
	}

	@Override
	public int setBytes(final int n, final FileChannel fileChannel, final long n2, final int n3) {
		throw reject();
	}

	@Override
	public ByteBuf setIndex(final int n, final int n2) {
		throw reject();
	}

	@Override
	public ByteBuf setInt(final int n, final int n2) {
		throw reject();
	}

	@Override
	public ByteBuf setIntLE(final int n, final int n2) {
		throw reject();
	}

	@Override
	public ByteBuf setLong(final int n, final long n2) {
		throw reject();
	}

	@Override
	public ByteBuf setLongLE(final int n, final long n2) {
		throw reject();
	}

	@Override
	public ByteBuf setMedium(final int n, final int n2) {
		throw reject();
	}

	@Override
	public ByteBuf setMediumLE(final int n, final int n2) {
		throw reject();
	}

	@Override
	public ByteBuf setShort(final int n, final int n2) {
		throw reject();
	}

	@Override
	public ByteBuf setShortLE(final int n, final int n2) {
		throw reject();
	}

	@Override
	public ByteBuf setChar(final int n, final int n2) {
		throw reject();
	}

	@Override
	public ByteBuf setFloat(final int n, final float n2) {
		throw reject();
	}

	@Override
	public ByteBuf setDouble(final int n, final double n2) {
		throw reject();
	}

	@Override
	public ByteBuf writeBoolean(final boolean b) {
		throw reject();
	}

	@Override
	public ByteBuf writeByte(final int n) {
		throw reject();
	}

	@Override
	public ByteBuf writeBytes(final byte[] array, final int n, final int n2) {
		throw reject();
	}

	@Override
	public ByteBuf writeBytes(final byte[] array) {
		throw reject();
	}

	@Override
	public ByteBuf writeBytes(final ByteBuffer byteBuffer) {
		throw reject();
	}

	@Override
	public ByteBuf writeBytes(final ByteBuf byteBuf, final int n, final int n2) {
		throw reject();
	}

	@Override
	public ByteBuf writeBytes(final ByteBuf byteBuf, final int n) {
		throw reject();
	}

	@Override
	public ByteBuf writeBytes(final ByteBuf byteBuf) {
		throw reject();
	}

	@Override
	public int writeBytes(final InputStream inputStream, final int n) throws IOException {
		throw reject();
	}

	@Override
	public int writeBytes(final ScatteringByteChannel scatteringByteChannel, final int n) throws IOException {
		throw reject();
	}

	@Override
	public int writeBytes(final FileChannel fileChannel, final long n, final int n2) throws IOException {
		throw reject();
	}

	@Override
	public ByteBuf writeInt(final int n) {
		throw reject();
	}

	@Override
	public ByteBuf writeIntLE(final int n) {
		throw reject();
	}

	@Override
	public ByteBuf writeLong(final long n) {
		throw reject();
	}

	@Override
	public ByteBuf writeLongLE(final long n) {
		throw reject();
	}

	@Override
	public ByteBuf writeMedium(final int n) {
		throw reject();
	}

	@Override
	public ByteBuf writeMediumLE(final int n) {
		throw reject();
	}

	@Override
	public ByteBuf writeZero(final int n) {
		throw reject();
	}

	@Override
	public ByteBuf writeShort(final int n) {
		throw reject();
	}

	@Override
	public ByteBuf writeShortLE(final int n) {
		throw reject();
	}

	@Override
	public ByteBuf writeChar(final int n) {
		throw reject();
	}

	@Override
	public ByteBuf writeFloat(final float n) {
		throw reject();
	}

	@Override
	public ByteBuf writeDouble(final double n) {
		throw reject();
	}

	@Override
	public ByteBuf writerIndex(final int n) {
		throw reject();
	}

	@Override
	public ByteBuf markWriterIndex() {
		throw reject();
	}

	@Override
	public ByteBuf resetWriterIndex() {
		throw reject();
	}

	@Override
	public int setCharSequence(final int n, final CharSequence charSequence, final Charset charset) {
		throw reject();
	}

	@Override
	public int writeCharSequence(final CharSequence charSequence, final Charset charset) {
		throw reject();
	}

	@Override
	public ByteBuf discardReadBytes() {
		throw reject();
	}

	@Override
	public ByteBuf discardSomeReadBytes() {
		throw reject();
	}

	@Override
	public ByteBuf ensureWritable(final int n) {
		throw reject();
	}

	@Override
	public int ensureWritable(final int n, final boolean b) {
		throw reject();
	}

	@Override
	public ByteBuf duplicate() {
		throw reject();
	}

	@Override
	public ByteBuf retainedDuplicate() {
		throw reject();
	}

	@Override
	public ByteBuf retain() {
		throw reject();
	}

	@Override
	public ByteBuf retain(final int n) {
		throw reject();
	}

	@Override
	public ByteBuf capacity(final int n) {
		throw reject();
	}

	@Override
	public ByteBuf clear() {
		throw reject();
	}

	@Override
	public boolean release() {
		throw reject();
	}

	@Override
	public boolean release(final int n) {
		throw reject();
	}

	@Override
	public int compareTo(final ByteBuf byteBuf) {
		throw reject();
	}

	@Override
	public int hashCode() {
		throw reject();
	}


	private static UnsupportedOperationException reject() {
		return new UnsupportedOperationException("not a replayable operation");
	}

	private void checkIndex(final int index, final int length) {
		if ((index + length) > this.buf.writerIndex()) {
			throw EOF;
		}
	}

	private void checkReadableBytes(final int length) {
		if (this.buf.readableBytes() < length) {
			throw EOF;
		}
	}

	public static final class EOFSignal extends RuntimeException {

		private static final long serialVersionUID = 1L;

		@Override
		public EOFSignal fillInStackTrace() {
			return this;
		}

	}

}
