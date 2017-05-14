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

	public ByteBuf capacity(final int n) {
		throw reject();
	}

	public int maxCapacity() {
		return this.capacity();
	}

	public ByteBufAllocator alloc() {
		return this.buffer.alloc();
	}

	public boolean isReadOnly() {
		return false;
	}

	public ByteBuf asReadOnly() {
		return Unpooled.unmodifiableBuffer((ByteBuf) this);
	}

	public boolean isDirect() {
		return this.buffer.isDirect();
	}

	public boolean hasArray() {
		return false;
	}

	public byte[] array() {
		throw new UnsupportedOperationException();
	}

	public int arrayOffset() {
		throw new UnsupportedOperationException();
	}

	public boolean hasMemoryAddress() {
		return false;
	}

	public long memoryAddress() {
		throw new UnsupportedOperationException();
	}

	public ByteBuf clear() {
		throw reject();
	}

	public boolean equals(final Object o) {
		return this == o;
	}

	public int compareTo(final ByteBuf byteBuf) {
		throw reject();
	}

	public ByteBuf copy() {
		throw reject();
	}

	public ByteBuf copy(final int n, final int n2) {
		this.checkIndex(n, n2);
		return this.buffer.copy(n, n2);
	}

	public ByteBuf discardReadBytes() {
		throw reject();
	}

	public ByteBuf ensureWritable(final int n) {
		throw reject();
	}

	public int ensureWritable(final int n, final boolean b) {
		throw reject();
	}

	public ByteBuf duplicate() {
		throw reject();
	}

	public ByteBuf retainedDuplicate() {
		throw reject();
	}

	public boolean getBoolean(final int n) {
		this.checkIndex(n, 1);
		return this.buffer.getBoolean(n);
	}

	public byte getByte(final int n) {
		this.checkIndex(n, 1);
		return this.buffer.getByte(n);
	}

	public short getUnsignedByte(final int n) {
		this.checkIndex(n, 1);
		return this.buffer.getUnsignedByte(n);
	}

	public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
		this.checkIndex(n, n3);
		this.buffer.getBytes(n, array, n2, n3);
		return this;
	}

	public ByteBuf getBytes(final int n, final byte[] array) {
		this.checkIndex(n, array.length);
		this.buffer.getBytes(n, array);
		return this;
	}

	public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
		throw reject();
	}

	public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
		this.checkIndex(n, n3);
		this.buffer.getBytes(n, byteBuf, n2, n3);
		return this;
	}

	public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2) {
		throw reject();
	}

	public ByteBuf getBytes(final int n, final ByteBuf byteBuf) {
		throw reject();
	}

	public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) {
		throw reject();
	}

	public int getBytes(final int n, final FileChannel fileChannel, final long n2, final int n3) {
		throw reject();
	}

	public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) {
		throw reject();
	}

	public int getInt(final int n) {
		this.checkIndex(n, 4);
		return this.buffer.getInt(n);
	}

	public int getIntLE(final int n) {
		this.checkIndex(n, 4);
		return this.buffer.getIntLE(n);
	}

	public long getUnsignedInt(final int n) {
		this.checkIndex(n, 4);
		return this.buffer.getUnsignedInt(n);
	}

	public long getUnsignedIntLE(final int n) {
		this.checkIndex(n, 4);
		return this.buffer.getUnsignedIntLE(n);
	}

	public long getLong(final int n) {
		this.checkIndex(n, 8);
		return this.buffer.getLong(n);
	}

	public long getLongLE(final int n) {
		this.checkIndex(n, 8);
		return this.buffer.getLongLE(n);
	}

	public int getMedium(final int n) {
		this.checkIndex(n, 3);
		return this.buffer.getMedium(n);
	}

	public int getMediumLE(final int n) {
		this.checkIndex(n, 3);
		return this.buffer.getMediumLE(n);
	}

	public int getUnsignedMedium(final int n) {
		this.checkIndex(n, 3);
		return this.buffer.getUnsignedMedium(n);
	}

	public int getUnsignedMediumLE(final int n) {
		this.checkIndex(n, 3);
		return this.buffer.getUnsignedMediumLE(n);
	}

	public short getShort(final int n) {
		this.checkIndex(n, 2);
		return this.buffer.getShort(n);
	}

	public short getShortLE(final int n) {
		this.checkIndex(n, 2);
		return this.buffer.getShortLE(n);
	}

	public int getUnsignedShort(final int n) {
		this.checkIndex(n, 2);
		return this.buffer.getUnsignedShort(n);
	}

	public int getUnsignedShortLE(final int n) {
		this.checkIndex(n, 2);
		return this.buffer.getUnsignedShortLE(n);
	}

	public char getChar(final int n) {
		this.checkIndex(n, 2);
		return this.buffer.getChar(n);
	}

	public float getFloat(final int n) {
		this.checkIndex(n, 4);
		return this.buffer.getFloat(n);
	}

	public double getDouble(final int n) {
		this.checkIndex(n, 8);
		return this.buffer.getDouble(n);
	}

	public CharSequence getCharSequence(final int n, final int n2, final Charset charset) {
		this.checkIndex(n, n2);
		return this.buffer.getCharSequence(n, n2, charset);
	}

	public int hashCode() {
		throw reject();
	}

	public int indexOf(final int n, final int n2, final byte b) {
		if (n == n2) {
			return -1;
		}
		if (Math.max(n, n2) > this.buffer.writerIndex()) {
			throw EOF;
		}
		return this.buffer.indexOf(n, n2, b);
	}

	public int bytesBefore(final byte b) {
		final int bytesBefore = this.buffer.bytesBefore(b);
		if (bytesBefore < 0) {
			throw EOF;
		}
		return bytesBefore;
	}

	public int bytesBefore(final int n, final byte b) {
		return this.bytesBefore(this.buffer.readerIndex(), n, b);
	}

	public int bytesBefore(final int n, final int n2, final byte b) {
		final int writerIndex = this.buffer.writerIndex();
		if (n >= writerIndex) {
			throw EOF;
		}
		if (n <= writerIndex - n2) {
			return this.buffer.bytesBefore(n, n2, b);
		}
		final int bytesBefore = this.buffer.bytesBefore(n, writerIndex - n, b);
		if (bytesBefore < 0) {
			throw EOF;
		}
		return bytesBefore;
	}

	public int forEachByte(final ByteProcessor byteProcessor) {
		final int forEachByte = this.buffer.forEachByte(byteProcessor);
		if (forEachByte < 0) {
			throw EOF;
		}
		return forEachByte;
	}

	public int forEachByte(final int n, final int n2, final ByteProcessor byteProcessor) {
		final int writerIndex = this.buffer.writerIndex();
		if (n >= writerIndex) {
			throw EOF;
		}
		if (n <= writerIndex - n2) {
			return this.buffer.forEachByte(n, n2, byteProcessor);
		}
		final int forEachByte = this.buffer.forEachByte(n, writerIndex - n, byteProcessor);
		if (forEachByte < 0) {
			throw EOF;
		}
		return forEachByte;
	}

	public int forEachByteDesc(final ByteProcessor byteProcessor) {
		return this.buffer.forEachByteDesc(byteProcessor);
	}

	public int forEachByteDesc(final int n, final int n2, final ByteProcessor byteProcessor) {
		if (n + n2 > this.buffer.writerIndex()) {
			throw EOF;
		}
		return this.buffer.forEachByteDesc(n, n2, byteProcessor);
	}

	public ByteBuf markReaderIndex() {
		this.buffer.markReaderIndex();
		return this;
	}

	public ByteBuf markWriterIndex() {
		throw reject();
	}

	public ByteOrder order() {
		return this.buffer.order();
	}

	public ByteBuf order(final ByteOrder byteOrder) {
		if (byteOrder == null) {
			throw new NullPointerException("endianness");
		}
		if (byteOrder == this.order()) {
			return this;
		}
		SwappedByteBuf swapped = this.swapped;
		if (swapped == null) {
			swapped = (this.swapped = new SwappedByteBuf((ByteBuf) this));
		}
		return (ByteBuf) swapped;
	}

	public boolean isReadable() {
		return this.buffer.isReadable();
	}

	public boolean isReadable(final int n) {
		return this.buffer.isReadable(n);
	}

	public int readableBytes() {
		return this.buffer.readableBytes();
	}

	public boolean readBoolean() {
		this.checkReadableBytes(1);
		return this.buffer.readBoolean();
	}

	public byte readByte() {
		this.checkReadableBytes(1);
		return this.buffer.readByte();
	}

	public short readUnsignedByte() {
		this.checkReadableBytes(1);
		return this.buffer.readUnsignedByte();
	}

	public ByteBuf readBytes(final byte[] array, final int n, final int n2) {
		this.checkReadableBytes(n2);
		this.buffer.readBytes(array, n, n2);
		return this;
	}

	public ByteBuf readBytes(final byte[] array) {
		this.checkReadableBytes(array.length);
		this.buffer.readBytes(array);
		return this;
	}

	public ByteBuf readBytes(final ByteBuffer byteBuffer) {
		throw reject();
	}

	public ByteBuf readBytes(final ByteBuf byteBuf, final int n, final int n2) {
		this.checkReadableBytes(n2);
		this.buffer.readBytes(byteBuf, n, n2);
		return this;
	}

	public ByteBuf readBytes(final ByteBuf byteBuf, final int n) {
		throw reject();
	}

	public ByteBuf readBytes(final ByteBuf byteBuf) {
		this.checkReadableBytes(byteBuf.writableBytes());
		this.buffer.readBytes(byteBuf);
		return this;
	}

	public int readBytes(final GatheringByteChannel gatheringByteChannel, final int n) {
		throw reject();
	}

	public int readBytes(final FileChannel fileChannel, final long n, final int n2) {
		throw reject();
	}

	public ByteBuf readBytes(final int n) {
		this.checkReadableBytes(n);
		return this.buffer.readBytes(n);
	}

	public ByteBuf readSlice(final int n) {
		this.checkReadableBytes(n);
		return this.buffer.readSlice(n);
	}

	public ByteBuf readRetainedSlice(final int n) {
		this.checkReadableBytes(n);
		return this.buffer.readRetainedSlice(n);
	}

	public ByteBuf readBytes(final OutputStream outputStream, final int n) {
		throw reject();
	}

	public int readerIndex() {
		return this.buffer.readerIndex();
	}

	public ByteBuf readerIndex(final int n) {
		this.buffer.readerIndex(n);
		return this;
	}

	public int readInt() {
		this.checkReadableBytes(4);
		return this.buffer.readInt();
	}

	public int readIntLE() {
		this.checkReadableBytes(4);
		return this.buffer.readIntLE();
	}

	public long readUnsignedInt() {
		this.checkReadableBytes(4);
		return this.buffer.readUnsignedInt();
	}

	public long readUnsignedIntLE() {
		this.checkReadableBytes(4);
		return this.buffer.readUnsignedIntLE();
	}

	public long readLong() {
		this.checkReadableBytes(8);
		return this.buffer.readLong();
	}

	public long readLongLE() {
		this.checkReadableBytes(8);
		return this.buffer.readLongLE();
	}

	public int readMedium() {
		this.checkReadableBytes(3);
		return this.buffer.readMedium();
	}

	public int readMediumLE() {
		this.checkReadableBytes(3);
		return this.buffer.readMediumLE();
	}

	public int readUnsignedMedium() {
		this.checkReadableBytes(3);
		return this.buffer.readUnsignedMedium();
	}

	public int readUnsignedMediumLE() {
		this.checkReadableBytes(3);
		return this.buffer.readUnsignedMediumLE();
	}

	public short readShort() {
		this.checkReadableBytes(2);
		return this.buffer.readShort();
	}

	public short readShortLE() {
		this.checkReadableBytes(2);
		return this.buffer.readShortLE();
	}

	public int readUnsignedShort() {
		this.checkReadableBytes(2);
		return this.buffer.readUnsignedShort();
	}

	public int readUnsignedShortLE() {
		this.checkReadableBytes(2);
		return this.buffer.readUnsignedShortLE();
	}

	public char readChar() {
		this.checkReadableBytes(2);
		return this.buffer.readChar();
	}

	public float readFloat() {
		this.checkReadableBytes(4);
		return this.buffer.readFloat();
	}

	public double readDouble() {
		this.checkReadableBytes(8);
		return this.buffer.readDouble();
	}

	public CharSequence readCharSequence(final int n, final Charset charset) {
		this.checkReadableBytes(n);
		return this.buffer.readCharSequence(n, charset);
	}

	public ByteBuf resetReaderIndex() {
		this.buffer.resetReaderIndex();
		return this;
	}

	public ByteBuf resetWriterIndex() {
		throw reject();
	}

	public ByteBuf setBoolean(final int n, final boolean b) {
		throw reject();
	}

	public ByteBuf setByte(final int n, final int n2) {
		throw reject();
	}

	public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
		throw reject();
	}

	public ByteBuf setBytes(final int n, final byte[] array) {
		throw reject();
	}

	public ByteBuf setBytes(final int n, final ByteBuffer byteBuffer) {
		throw reject();
	}

	public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
		throw reject();
	}

	public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2) {
		throw reject();
	}

	public ByteBuf setBytes(final int n, final ByteBuf byteBuf) {
		throw reject();
	}

	public int setBytes(final int n, final InputStream inputStream, final int n2) {
		throw reject();
	}

	public ByteBuf setZero(final int n, final int n2) {
		throw reject();
	}

	public int setBytes(final int n, final ScatteringByteChannel scatteringByteChannel, final int n2) {
		throw reject();
	}

	public int setBytes(final int n, final FileChannel fileChannel, final long n2, final int n3) {
		throw reject();
	}

	public ByteBuf setIndex(final int n, final int n2) {
		throw reject();
	}

	public ByteBuf setInt(final int n, final int n2) {
		throw reject();
	}

	public ByteBuf setIntLE(final int n, final int n2) {
		throw reject();
	}

	public ByteBuf setLong(final int n, final long n2) {
		throw reject();
	}

	public ByteBuf setLongLE(final int n, final long n2) {
		throw reject();
	}

	public ByteBuf setMedium(final int n, final int n2) {
		throw reject();
	}

	public ByteBuf setMediumLE(final int n, final int n2) {
		throw reject();
	}

	public ByteBuf setShort(final int n, final int n2) {
		throw reject();
	}

	public ByteBuf setShortLE(final int n, final int n2) {
		throw reject();
	}

	public ByteBuf setChar(final int n, final int n2) {
		throw reject();
	}

	public ByteBuf setFloat(final int n, final float n2) {
		throw reject();
	}

	public ByteBuf setDouble(final int n, final double n2) {
		throw reject();
	}

	public ByteBuf skipBytes(final int n) {
		this.checkReadableBytes(n);
		this.buffer.skipBytes(n);
		return this;
	}

	public ByteBuf slice() {
		throw reject();
	}

	public ByteBuf retainedSlice() {
		throw reject();
	}

	public ByteBuf slice(final int n, final int n2) {
		this.checkIndex(n, n2);
		return this.buffer.slice(n, n2);
	}

	public ByteBuf retainedSlice(final int n, final int n2) {
		this.checkIndex(n, n2);
		return this.buffer.slice(n, n2);
	}

	public int nioBufferCount() {
		return this.buffer.nioBufferCount();
	}

	public ByteBuffer nioBuffer() {
		throw reject();
	}

	public ByteBuffer nioBuffer(final int n, final int n2) {
		this.checkIndex(n, n2);
		return this.buffer.nioBuffer(n, n2);
	}

	public ByteBuffer[] nioBuffers() {
		throw reject();
	}

	public ByteBuffer[] nioBuffers(final int n, final int n2) {
		this.checkIndex(n, n2);
		return this.buffer.nioBuffers(n, n2);
	}

	public ByteBuffer internalNioBuffer(final int n, final int n2) {
		this.checkIndex(n, n2);
		return this.buffer.internalNioBuffer(n, n2);
	}

	public String toString(final int n, final int n2, final Charset charset) {
		this.checkIndex(n, n2);
		return this.buffer.toString(n, n2, charset);
	}

	public String toString(final Charset charset) {
		throw reject();
	}

	public String toString() {
		return StringUtil.simpleClassName((Object) this) + '(' + "ridx=" + this.readerIndex() + ", widx=" + this.writerIndex() + ')';
	}

	public boolean isWritable() {
		return false;
	}

	public boolean isWritable(final int n) {
		return false;
	}

	public int writableBytes() {
		return 0;
	}

	public int maxWritableBytes() {
		return 0;
	}

	public ByteBuf writeBoolean(final boolean b) {
		throw reject();
	}

	public ByteBuf writeByte(final int n) {
		throw reject();
	}

	public ByteBuf writeBytes(final byte[] array, final int n, final int n2) {
		throw reject();
	}

	public ByteBuf writeBytes(final byte[] array) {
		throw reject();
	}

	public ByteBuf writeBytes(final ByteBuffer byteBuffer) {
		throw reject();
	}

	public ByteBuf writeBytes(final ByteBuf byteBuf, final int n, final int n2) {
		throw reject();
	}

	public ByteBuf writeBytes(final ByteBuf byteBuf, final int n) {
		throw reject();
	}

	public ByteBuf writeBytes(final ByteBuf byteBuf) {
		throw reject();
	}

	public int writeBytes(final InputStream inputStream, final int n) {
		throw reject();
	}

	public int writeBytes(final ScatteringByteChannel scatteringByteChannel, final int n) {
		throw reject();
	}

	public int writeBytes(final FileChannel fileChannel, final long n, final int n2) {
		throw reject();
	}

	public ByteBuf writeInt(final int n) {
		throw reject();
	}

	public ByteBuf writeIntLE(final int n) {
		throw reject();
	}

	public ByteBuf writeLong(final long n) {
		throw reject();
	}

	public ByteBuf writeLongLE(final long n) {
		throw reject();
	}

	public ByteBuf writeMedium(final int n) {
		throw reject();
	}

	public ByteBuf writeMediumLE(final int n) {
		throw reject();
	}

	public ByteBuf writeZero(final int n) {
		throw reject();
	}

	public int writerIndex() {
		return this.buffer.writerIndex();
	}

	public ByteBuf writerIndex(final int n) {
		throw reject();
	}

	public ByteBuf writeShort(final int n) {
		throw reject();
	}

	public ByteBuf writeShortLE(final int n) {
		throw reject();
	}

	public ByteBuf writeChar(final int n) {
		throw reject();
	}

	public ByteBuf writeFloat(final float n) {
		throw reject();
	}

	public ByteBuf writeDouble(final double n) {
		throw reject();
	}

	public int setCharSequence(final int n, final CharSequence charSequence, final Charset charset) {
		throw reject();
	}

	public int writeCharSequence(final CharSequence charSequence, final Charset charset) {
		throw reject();
	}

	private void checkIndex(final int n, final int n2) {
		if (n + n2 > this.buffer.writerIndex()) {
			throw EOF;
		}
	}

	private void checkReadableBytes(final int n) {
		if (this.buffer.readableBytes() < n) {
			throw EOF;
		}
	}

	public ByteBuf discardSomeReadBytes() {
		throw reject();
	}

	public int refCnt() {
		return this.buffer.refCnt();
	}

	public ByteBuf retain() {
		throw reject();
	}

	public ByteBuf retain(final int n) {
		throw reject();
	}

	public ByteBuf touch() {
		this.buffer.touch();
		return this;
	}

	public ByteBuf touch(final Object o) {
		this.buffer.touch(o);
		return this;
	}

	public boolean release() {
		throw reject();
	}

	public boolean release(final int n) {
		throw reject();
	}

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
