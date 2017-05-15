package protocolsupport.utils.netty;

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
public final class ReplayingDecoderBuffer extends ByteBuf {

	public static final EOFSignal EOF = new EOFSignal();

	private ByteBuf buffer;
	private SwappedByteBuf swapped;

	public ReplayingDecoderBuffer() {
	}

	public ReplayingDecoderBuffer(ByteBuf buffer) {
		this.buffer = buffer;
	}

	public void setBuf(ByteBuf buffer) {
		this.buffer = buffer;
	}

	@Override
	public int capacity() {
		return Integer.MAX_VALUE;
	}

	@Override
	public ByteBuf capacity(final int n) {
		throw reject();
	}

	@Override
	public int maxCapacity() {
		return this.capacity();
	}

	@Override
	public ByteBufAllocator alloc() {
		return this.buffer.alloc();
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
		return this.buffer.isDirect();
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
		throw reject();
	}

	@Override
	public boolean equals(final Object o) {
		return this == o;
	}

	@Override
	public int compareTo(final ByteBuf byteBuf) {
		throw reject();
	}

	@Override
	public ByteBuf copy() {
		throw reject();
	}

	@Override
	public ByteBuf copy(final int n, final int n2) {
		this.checkIndex(n, n2);
		return this.buffer.copy(n, n2);
	}

	@Override
	public ByteBuf discardReadBytes() {
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
	public boolean getBoolean(final int n) {
		this.checkIndex(n, 1);
		return this.buffer.getBoolean(n);
	}

	@Override
	public byte getByte(final int n) {
		this.checkIndex(n, 1);
		return this.buffer.getByte(n);
	}

	@Override
	public short getUnsignedByte(final int n) {
		this.checkIndex(n, 1);
		return this.buffer.getUnsignedByte(n);
	}

	@Override
	public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
		this.checkIndex(n, n3);
		this.buffer.getBytes(n, array, n2, n3);
		return this;
	}

	@Override
	public ByteBuf getBytes(final int n, final byte[] array) {
		this.checkIndex(n, array.length);
		this.buffer.getBytes(n, array);
		return this;
	}

	@Override
	public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
		throw reject();
	}

	@Override
	public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
		this.checkIndex(n, n3);
		this.buffer.getBytes(n, byteBuf, n2, n3);
		return this;
	}

	@Override
	public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2) {
		throw reject();
	}

	@Override
	public ByteBuf getBytes(final int n, final ByteBuf byteBuf) {
		throw reject();
	}

	@Override
	public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) {
		throw reject();
	}

	@Override
	public int getBytes(final int n, final FileChannel fileChannel, final long n2, final int n3) {
		throw reject();
	}

	@Override
	public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) {
		throw reject();
	}

	@Override
	public int getInt(final int n) {
		this.checkIndex(n, 4);
		return this.buffer.getInt(n);
	}

	@Override
	public int getIntLE(final int n) {
		this.checkIndex(n, 4);
		return this.buffer.getIntLE(n);
	}

	@Override
	public long getUnsignedInt(final int n) {
		this.checkIndex(n, 4);
		return this.buffer.getUnsignedInt(n);
	}

	@Override
	public long getUnsignedIntLE(final int n) {
		this.checkIndex(n, 4);
		return this.buffer.getUnsignedIntLE(n);
	}

	@Override
	public long getLong(final int n) {
		this.checkIndex(n, 8);
		return this.buffer.getLong(n);
	}

	@Override
	public long getLongLE(final int n) {
		this.checkIndex(n, 8);
		return this.buffer.getLongLE(n);
	}

	@Override
	public int getMedium(final int n) {
		this.checkIndex(n, 3);
		return this.buffer.getMedium(n);
	}

	@Override
	public int getMediumLE(final int n) {
		this.checkIndex(n, 3);
		return this.buffer.getMediumLE(n);
	}

	@Override
	public int getUnsignedMedium(final int n) {
		this.checkIndex(n, 3);
		return this.buffer.getUnsignedMedium(n);
	}

	@Override
	public int getUnsignedMediumLE(final int n) {
		this.checkIndex(n, 3);
		return this.buffer.getUnsignedMediumLE(n);
	}

	@Override
	public short getShort(final int n) {
		this.checkIndex(n, 2);
		return this.buffer.getShort(n);
	}

	@Override
	public short getShortLE(final int n) {
		this.checkIndex(n, 2);
		return this.buffer.getShortLE(n);
	}

	@Override
	public int getUnsignedShort(final int n) {
		this.checkIndex(n, 2);
		return this.buffer.getUnsignedShort(n);
	}

	@Override
	public int getUnsignedShortLE(final int n) {
		this.checkIndex(n, 2);
		return this.buffer.getUnsignedShortLE(n);
	}

	@Override
	public char getChar(final int n) {
		this.checkIndex(n, 2);
		return this.buffer.getChar(n);
	}

	@Override
	public float getFloat(final int n) {
		this.checkIndex(n, 4);
		return this.buffer.getFloat(n);
	}

	@Override
	public double getDouble(final int n) {
		this.checkIndex(n, 8);
		return this.buffer.getDouble(n);
	}

	@Override
	public CharSequence getCharSequence(final int n, final int n2, final Charset charset) {
		this.checkIndex(n, n2);
		return this.buffer.getCharSequence(n, n2, charset);
	}

	@Override
	public int hashCode() {
		throw reject();
	}

	@Override
	public int indexOf(final int n, final int n2, final byte b) {
		if (n == n2) {
			return -1;
		}
		if (Math.max(n, n2) > this.buffer.writerIndex()) {
			throw EOF;
		}
		return this.buffer.indexOf(n, n2, b);
	}

	@Override
	public int bytesBefore(final byte b) {
		final int bytesBefore = this.buffer.bytesBefore(b);
		if (bytesBefore < 0) {
			throw EOF;
		}
		return bytesBefore;
	}

	@Override
	public int bytesBefore(final int n, final byte b) {
		return this.bytesBefore(this.buffer.readerIndex(), n, b);
	}

	@Override
	public int bytesBefore(final int n, final int n2, final byte b) {
		final int writerIndex = this.buffer.writerIndex();
		if (n >= writerIndex) {
			throw EOF;
		}
		if (n <= (writerIndex - n2)) {
			return this.buffer.bytesBefore(n, n2, b);
		}
		final int bytesBefore = this.buffer.bytesBefore(n, writerIndex - n, b);
		if (bytesBefore < 0) {
			throw EOF;
		}
		return bytesBefore;
	}

	@Override
	public int forEachByte(final ByteProcessor byteProcessor) {
		final int forEachByte = this.buffer.forEachByte(byteProcessor);
		if (forEachByte < 0) {
			throw EOF;
		}
		return forEachByte;
	}

	@Override
	public int forEachByte(final int n, final int n2, final ByteProcessor byteProcessor) {
		final int writerIndex = this.buffer.writerIndex();
		if (n >= writerIndex) {
			throw EOF;
		}
		if (n <= (writerIndex - n2)) {
			return this.buffer.forEachByte(n, n2, byteProcessor);
		}
		final int forEachByte = this.buffer.forEachByte(n, writerIndex - n, byteProcessor);
		if (forEachByte < 0) {
			throw EOF;
		}
		return forEachByte;
	}

	@Override
	public int forEachByteDesc(final ByteProcessor byteProcessor) {
		return this.buffer.forEachByteDesc(byteProcessor);
	}

	@Override
	public int forEachByteDesc(final int n, final int n2, final ByteProcessor byteProcessor) {
		if ((n + n2) > this.buffer.writerIndex()) {
			throw EOF;
		}
		return this.buffer.forEachByteDesc(n, n2, byteProcessor);
	}

	@Override
	public ByteBuf markReaderIndex() {
		this.buffer.markReaderIndex();
		return this;
	}

	@Override
	public ByteBuf markWriterIndex() {
		throw reject();
	}

	@Override
	public ByteOrder order() {
		return this.buffer.order();
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
	public boolean isReadable() {
		return this.buffer.isReadable();
	}

	@Override
	public boolean isReadable(final int n) {
		return this.buffer.isReadable(n);
	}

	@Override
	public int readableBytes() {
		return this.buffer.readableBytes();
	}

	@Override
	public boolean readBoolean() {
		this.checkReadableBytes(1);
		return this.buffer.readBoolean();
	}

	@Override
	public byte readByte() {
		this.checkReadableBytes(1);
		return this.buffer.readByte();
	}

	@Override
	public short readUnsignedByte() {
		this.checkReadableBytes(1);
		return this.buffer.readUnsignedByte();
	}

	@Override
	public ByteBuf readBytes(final byte[] array, final int n, final int n2) {
		this.checkReadableBytes(n2);
		this.buffer.readBytes(array, n, n2);
		return this;
	}

	@Override
	public ByteBuf readBytes(final byte[] array) {
		this.checkReadableBytes(array.length);
		this.buffer.readBytes(array);
		return this;
	}

	@Override
	public ByteBuf readBytes(final ByteBuffer byteBuffer) {
		throw reject();
	}

	@Override
	public ByteBuf readBytes(final ByteBuf byteBuf, final int n, final int n2) {
		this.checkReadableBytes(n2);
		this.buffer.readBytes(byteBuf, n, n2);
		return this;
	}

	@Override
	public ByteBuf readBytes(final ByteBuf byteBuf, final int n) {
		throw reject();
	}

	@Override
	public ByteBuf readBytes(final ByteBuf byteBuf) {
		this.checkReadableBytes(byteBuf.writableBytes());
		this.buffer.readBytes(byteBuf);
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
		return this.buffer.readBytes(n);
	}

	@Override
	public ByteBuf readSlice(final int n) {
		this.checkReadableBytes(n);
		return this.buffer.readSlice(n);
	}

	@Override
	public ByteBuf readRetainedSlice(final int n) {
		this.checkReadableBytes(n);
		return this.buffer.readRetainedSlice(n);
	}

	@Override
	public ByteBuf readBytes(final OutputStream outputStream, final int n) {
		throw reject();
	}

	@Override
	public int readerIndex() {
		return this.buffer.readerIndex();
	}

	@Override
	public ByteBuf readerIndex(final int n) {
		this.buffer.readerIndex(n);
		return this;
	}

	@Override
	public int readInt() {
		this.checkReadableBytes(4);
		return this.buffer.readInt();
	}

	@Override
	public int readIntLE() {
		this.checkReadableBytes(4);
		return this.buffer.readIntLE();
	}

	@Override
	public long readUnsignedInt() {
		this.checkReadableBytes(4);
		return this.buffer.readUnsignedInt();
	}

	@Override
	public long readUnsignedIntLE() {
		this.checkReadableBytes(4);
		return this.buffer.readUnsignedIntLE();
	}

	@Override
	public long readLong() {
		this.checkReadableBytes(8);
		return this.buffer.readLong();
	}

	@Override
	public long readLongLE() {
		this.checkReadableBytes(8);
		return this.buffer.readLongLE();
	}

	@Override
	public int readMedium() {
		this.checkReadableBytes(3);
		return this.buffer.readMedium();
	}

	@Override
	public int readMediumLE() {
		this.checkReadableBytes(3);
		return this.buffer.readMediumLE();
	}

	@Override
	public int readUnsignedMedium() {
		this.checkReadableBytes(3);
		return this.buffer.readUnsignedMedium();
	}

	@Override
	public int readUnsignedMediumLE() {
		this.checkReadableBytes(3);
		return this.buffer.readUnsignedMediumLE();
	}

	@Override
	public short readShort() {
		this.checkReadableBytes(2);
		return this.buffer.readShort();
	}

	@Override
	public short readShortLE() {
		this.checkReadableBytes(2);
		return this.buffer.readShortLE();
	}

	@Override
	public int readUnsignedShort() {
		this.checkReadableBytes(2);
		return this.buffer.readUnsignedShort();
	}

	@Override
	public int readUnsignedShortLE() {
		this.checkReadableBytes(2);
		return this.buffer.readUnsignedShortLE();
	}

	@Override
	public char readChar() {
		this.checkReadableBytes(2);
		return this.buffer.readChar();
	}

	@Override
	public float readFloat() {
		this.checkReadableBytes(4);
		return this.buffer.readFloat();
	}

	@Override
	public double readDouble() {
		this.checkReadableBytes(8);
		return this.buffer.readDouble();
	}

	@Override
	public CharSequence readCharSequence(final int n, final Charset charset) {
		this.checkReadableBytes(n);
		return this.buffer.readCharSequence(n, charset);
	}

	@Override
	public ByteBuf resetReaderIndex() {
		this.buffer.resetReaderIndex();
		return this;
	}

	@Override
	public ByteBuf resetWriterIndex() {
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
	public ByteBuf skipBytes(final int n) {
		this.checkReadableBytes(n);
		this.buffer.skipBytes(n);
		return this;
	}

	@Override
	public ByteBuf slice() {
		throw reject();
	}

	@Override
	public ByteBuf retainedSlice() {
		throw reject();
	}

	@Override
	public ByteBuf slice(final int n, final int n2) {
		this.checkIndex(n, n2);
		return this.buffer.slice(n, n2);
	}

	@Override
	public ByteBuf retainedSlice(final int n, final int n2) {
		this.checkIndex(n, n2);
		return this.buffer.slice(n, n2);
	}

	@Override
	public int nioBufferCount() {
		return this.buffer.nioBufferCount();
	}

	@Override
	public ByteBuffer nioBuffer() {
		throw reject();
	}

	@Override
	public ByteBuffer nioBuffer(final int n, final int n2) {
		this.checkIndex(n, n2);
		return this.buffer.nioBuffer(n, n2);
	}

	@Override
	public ByteBuffer[] nioBuffers() {
		throw reject();
	}

	@Override
	public ByteBuffer[] nioBuffers(final int n, final int n2) {
		this.checkIndex(n, n2);
		return this.buffer.nioBuffers(n, n2);
	}

	@Override
	public ByteBuffer internalNioBuffer(final int n, final int n2) {
		this.checkIndex(n, n2);
		return this.buffer.internalNioBuffer(n, n2);
	}

	@Override
	public String toString(final int n, final int n2, final Charset charset) {
		this.checkIndex(n, n2);
		return this.buffer.toString(n, n2, charset);
	}

	@Override
	public String toString(final Charset charset) {
		throw reject();
	}

	@Override
	public String toString() {
		return StringUtil.simpleClassName(this) + '(' + "ridx=" + this.readerIndex() + ", widx=" + this.writerIndex() + ')';
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
	public int writeBytes(final InputStream inputStream, final int n) {
		throw reject();
	}

	@Override
	public int writeBytes(final ScatteringByteChannel scatteringByteChannel, final int n) {
		throw reject();
	}

	@Override
	public int writeBytes(final FileChannel fileChannel, final long n, final int n2) {
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
	public int writerIndex() {
		return this.buffer.writerIndex();
	}

	@Override
	public ByteBuf writerIndex(final int n) {
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
	public int setCharSequence(final int n, final CharSequence charSequence, final Charset charset) {
		throw reject();
	}

	@Override
	public int writeCharSequence(final CharSequence charSequence, final Charset charset) {
		throw reject();
	}

	private void checkIndex(final int n, final int n2) {
		if ((n + n2) > this.buffer.writerIndex()) {
			throw EOF;
		}
	}

	private void checkReadableBytes(final int n) {
		if (this.buffer.readableBytes() < n) {
			throw EOF;
		}
	}

	@Override
	public ByteBuf discardSomeReadBytes() {
		throw reject();
	}

	@Override
	public int refCnt() {
		return this.buffer.refCnt();
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
	public ByteBuf touch() {
		this.buffer.touch();
		return this;
	}

	@Override
	public ByteBuf touch(final Object o) {
		this.buffer.touch(o);
		return this;
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
	public ByteBuf unwrap() {
		throw reject();
	}

	private static UnsupportedOperationException reject() {
		return new UnsupportedOperationException("not a replayable operation");
	}

	public static final class EOFSignal extends RuntimeException {

		private static final long serialVersionUID = 1L;

		@Override
		public EOFSignal fillInStackTrace() {
			return this;
		}

	}

}
