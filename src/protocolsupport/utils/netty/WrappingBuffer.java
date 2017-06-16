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
import io.netty.util.ByteProcessor;
import io.netty.util.internal.StringUtil;

public class WrappingBuffer extends ByteBuf {

	protected ByteBuf buf;

	public WrappingBuffer() {
	}

	public WrappingBuffer(ByteBuf buf) {
		this.buf = buf;
	}

	public void setBuf(ByteBuf buf) {
		this.buf = buf;
	}

	@Override
	public final boolean hasMemoryAddress() {
		return this.buf.hasMemoryAddress();
	}

	@Override
	public final long memoryAddress() {
		return this.buf.memoryAddress();
	}

	@Override
	public final int capacity() {
		return this.buf.capacity();
	}

	@Override
	public ByteBuf capacity(final int n) {
		this.buf.capacity(n);
		return this;
	}

	@Override
	public final int maxCapacity() {
		return this.buf.maxCapacity();
	}

	@Override
	public final ByteBufAllocator alloc() {
		return this.buf.alloc();
	}

	@Override
	@SuppressWarnings("deprecation")
	public final ByteOrder order() {
		return this.buf.order();
	}

	@Override
	@SuppressWarnings("deprecation")
	public ByteBuf order(final ByteOrder byteOrder) {
		return this.buf.order(byteOrder);
	}

	@Override
	public final ByteBuf unwrap() {
		return this.buf;
	}

	@Override
	public ByteBuf asReadOnly() {
		return this.buf.asReadOnly();
	}

	@Override
	public boolean isReadOnly() {
		return this.buf.isReadOnly();
	}

	@Override
	public final boolean isDirect() {
		return this.buf.isDirect();
	}

	@Override
	public final int readerIndex() {
		return this.buf.readerIndex();
	}

	@Override
	public final ByteBuf readerIndex(final int n) {
		this.buf.readerIndex(n);
		return this;
	}

	@Override
	public final int writerIndex() {
		return this.buf.writerIndex();
	}

	@Override
	public final ByteBuf writerIndex(final int n) {
		this.buf.writerIndex(n);
		return this;
	}

	@Override
	public ByteBuf setIndex(final int n, final int n2) {
		this.buf.setIndex(n, n2);
		return this;
	}

	@Override
	public final int readableBytes() {
		return this.buf.readableBytes();
	}

	@Override
	public final int writableBytes() {
		return this.buf.writableBytes();
	}

	@Override
	public final int maxWritableBytes() {
		return this.buf.maxWritableBytes();
	}

	@Override
	public final boolean isReadable() {
		return this.buf.isReadable();
	}

	@Override
	public final boolean isWritable() {
		return this.buf.isWritable();
	}

	@Override
	public final ByteBuf clear() {
		this.buf.clear();
		return this;
	}

	@Override
	public final ByteBuf markReaderIndex() {
		this.buf.markReaderIndex();
		return this;
	}

	@Override
	public final ByteBuf resetReaderIndex() {
		this.buf.resetReaderIndex();
		return this;
	}

	@Override
	public final ByteBuf markWriterIndex() {
		this.buf.markWriterIndex();
		return this;
	}

	@Override
	public final ByteBuf resetWriterIndex() {
		this.buf.resetWriterIndex();
		return this;
	}

	@Override
	public ByteBuf discardReadBytes() {
		this.buf.discardReadBytes();
		return this;
	}

	@Override
	public ByteBuf discardSomeReadBytes() {
		this.buf.discardSomeReadBytes();
		return this;
	}

	@Override
	public ByteBuf ensureWritable(final int n) {
		this.buf.ensureWritable(n);
		return this;
	}

	@Override
	public int ensureWritable(final int n, final boolean b) {
		return this.buf.ensureWritable(n, b);
	}

	@Override
	public boolean getBoolean(final int n) {
		return this.buf.getBoolean(n);
	}

	@Override
	public byte getByte(final int n) {
		return this.buf.getByte(n);
	}

	@Override
	public short getUnsignedByte(final int n) {
		return this.buf.getUnsignedByte(n);
	}

	@Override
	public short getShort(final int n) {
		return this.buf.getShort(n);
	}

	@Override
	public short getShortLE(final int n) {
		return this.buf.getShortLE(n);
	}

	@Override
	public int getUnsignedShort(final int n) {
		return this.buf.getUnsignedShort(n);
	}

	@Override
	public int getUnsignedShortLE(final int n) {
		return this.buf.getUnsignedShortLE(n);
	}

	@Override
	public int getMedium(final int n) {
		return this.buf.getMedium(n);
	}

	@Override
	public int getMediumLE(final int n) {
		return this.buf.getMediumLE(n);
	}

	@Override
	public int getUnsignedMedium(final int n) {
		return this.buf.getUnsignedMedium(n);
	}

	@Override
	public int getUnsignedMediumLE(final int n) {
		return this.buf.getUnsignedMediumLE(n);
	}

	@Override
	public int getInt(final int n) {
		return this.buf.getInt(n);
	}

	@Override
	public int getIntLE(final int n) {
		return this.buf.getIntLE(n);
	}

	@Override
	public long getUnsignedInt(final int n) {
		return this.buf.getUnsignedInt(n);
	}

	@Override
	public long getUnsignedIntLE(final int n) {
		return this.buf.getUnsignedIntLE(n);
	}

	@Override
	public long getLong(final int n) {
		return this.buf.getLong(n);
	}

	@Override
	public long getLongLE(final int n) {
		return this.buf.getLongLE(n);
	}

	@Override
	public char getChar(final int n) {
		return this.buf.getChar(n);
	}

	@Override
	public float getFloat(final int n) {
		return this.buf.getFloat(n);
	}

	@Override
	public double getDouble(final int n) {
		return this.buf.getDouble(n);
	}

	@Override
	public ByteBuf getBytes(final int n, final ByteBuf byteBuf) {
		this.buf.getBytes(n, byteBuf);
		return this;
	}

	@Override
	public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2) {
		this.buf.getBytes(n, byteBuf, n2);
		return this;
	}

	@Override
	public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
		this.buf.getBytes(n, byteBuf, n2, n3);
		return this;
	}

	@Override
	public ByteBuf getBytes(final int n, final byte[] array) {
		this.buf.getBytes(n, array);
		return this;
	}

	@Override
	public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
		this.buf.getBytes(n, array, n2, n3);
		return this;
	}

	@Override
	public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
		this.buf.getBytes(n, byteBuffer);
		return this;
	}

	@Override
	public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) throws IOException {
		this.buf.getBytes(n, outputStream, n2);
		return this;
	}

	@Override
	public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) throws IOException {
		return this.buf.getBytes(n, gatheringByteChannel, n2);
	}

	@Override
	public int getBytes(final int n, final FileChannel fileChannel, final long n2, final int n3) throws IOException {
		return this.buf.getBytes(n, fileChannel, n2, n3);
	}

	@Override
	public CharSequence getCharSequence(final int n, final int n2, final Charset charset) {
		return this.buf.getCharSequence(n, n2, charset);
	}

	@Override
	public ByteBuf setBoolean(final int n, final boolean b) {
		this.buf.setBoolean(n, b);
		return this;
	}

	@Override
	public ByteBuf setByte(final int n, final int n2) {
		this.buf.setByte(n, n2);
		return this;
	}

	@Override
	public ByteBuf setShort(final int n, final int n2) {
		this.buf.setShort(n, n2);
		return this;
	}

	@Override
	public ByteBuf setShortLE(final int n, final int n2) {
		this.buf.setShortLE(n, n2);
		return this;
	}

	@Override
	public ByteBuf setMedium(final int n, final int n2) {
		this.buf.setMedium(n, n2);
		return this;
	}

	@Override
	public ByteBuf setMediumLE(final int n, final int n2) {
		this.buf.setMediumLE(n, n2);
		return this;
	}

	@Override
	public ByteBuf setInt(final int n, final int n2) {
		this.buf.setInt(n, n2);
		return this;
	}

	@Override
	public ByteBuf setIntLE(final int n, final int n2) {
		this.buf.setIntLE(n, n2);
		return this;
	}

	@Override
	public ByteBuf setLong(final int n, final long n2) {
		this.buf.setLong(n, n2);
		return this;
	}

	@Override
	public ByteBuf setLongLE(final int n, final long n2) {
		this.buf.setLongLE(n, n2);
		return this;
	}

	@Override
	public ByteBuf setChar(final int n, final int n2) {
		this.buf.setChar(n, n2);
		return this;
	}

	@Override
	public ByteBuf setFloat(final int n, final float n2) {
		this.buf.setFloat(n, n2);
		return this;
	}

	@Override
	public ByteBuf setDouble(final int n, final double n2) {
		this.buf.setDouble(n, n2);
		return this;
	}

	@Override
	public ByteBuf setBytes(final int n, final ByteBuf byteBuf) {
		this.buf.setBytes(n, byteBuf);
		return this;
	}

	@Override
	public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2) {
		this.buf.setBytes(n, byteBuf, n2);
		return this;
	}

	@Override
	public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
		this.buf.setBytes(n, byteBuf, n2, n3);
		return this;
	}

	@Override
	public ByteBuf setBytes(final int n, final byte[] array) {
		this.buf.setBytes(n, array);
		return this;
	}

	@Override
	public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
		this.buf.setBytes(n, array, n2, n3);
		return this;
	}

	@Override
	public ByteBuf setBytes(final int n, final ByteBuffer byteBuffer) {
		this.buf.setBytes(n, byteBuffer);
		return this;
	}

	@Override
	public int setBytes(final int n, final InputStream inputStream, final int n2) throws IOException {
		return this.buf.setBytes(n, inputStream, n2);
	}

	@Override
	public int setBytes(final int n, final ScatteringByteChannel scatteringByteChannel, final int n2) throws IOException {
		return this.buf.setBytes(n, scatteringByteChannel, n2);
	}

	@Override
	public int setBytes(final int n, final FileChannel fileChannel, final long n2, final int n3) throws IOException {
		return this.buf.setBytes(n, fileChannel, n2, n3);
	}

	@Override
	public ByteBuf setZero(final int n, final int n2) {
		this.buf.setZero(n, n2);
		return this;
	}

	@Override
	public int setCharSequence(final int n, final CharSequence charSequence, final Charset charset) {
		return this.buf.setCharSequence(n, charSequence, charset);
	}

	@Override
	public boolean readBoolean() {
		return this.buf.readBoolean();
	}

	@Override
	public byte readByte() {
		return this.buf.readByte();
	}

	@Override
	public short readUnsignedByte() {
		return this.buf.readUnsignedByte();
	}

	@Override
	public short readShort() {
		return this.buf.readShort();
	}

	@Override
	public short readShortLE() {
		return this.buf.readShortLE();
	}

	@Override
	public int readUnsignedShort() {
		return this.buf.readUnsignedShort();
	}

	@Override
	public int readUnsignedShortLE() {
		return this.buf.readUnsignedShortLE();
	}

	@Override
	public int readMedium() {
		return this.buf.readMedium();
	}

	@Override
	public int readMediumLE() {
		return this.buf.readMediumLE();
	}

	@Override
	public int readUnsignedMedium() {
		return this.buf.readUnsignedMedium();
	}

	@Override
	public int readUnsignedMediumLE() {
		return this.buf.readUnsignedMediumLE();
	}

	@Override
	public int readInt() {
		return this.buf.readInt();
	}

	@Override
	public int readIntLE() {
		return this.buf.readIntLE();
	}

	@Override
	public long readUnsignedInt() {
		return this.buf.readUnsignedInt();
	}

	@Override
	public long readUnsignedIntLE() {
		return this.buf.readUnsignedIntLE();
	}

	@Override
	public long readLong() {
		return this.buf.readLong();
	}

	@Override
	public long readLongLE() {
		return this.buf.readLongLE();
	}

	@Override
	public char readChar() {
		return this.buf.readChar();
	}

	@Override
	public float readFloat() {
		return this.buf.readFloat();
	}

	@Override
	public double readDouble() {
		return this.buf.readDouble();
	}

	@Override
	public ByteBuf readBytes(final int n) {
		return this.buf.readBytes(n);
	}

	@Override
	public ByteBuf readSlice(final int n) {
		return this.buf.readSlice(n);
	}

	@Override
	public ByteBuf readRetainedSlice(final int n) {
		return this.buf.readRetainedSlice(n);
	}

	@Override
	public ByteBuf readBytes(final ByteBuf byteBuf) {
		this.buf.readBytes(byteBuf);
		return this;
	}

	@Override
	public ByteBuf readBytes(final ByteBuf byteBuf, final int n) {
		this.buf.readBytes(byteBuf, n);
		return this;
	}

	@Override
	public ByteBuf readBytes(final ByteBuf byteBuf, final int n, final int n2) {
		this.buf.readBytes(byteBuf, n, n2);
		return this;
	}

	@Override
	public ByteBuf readBytes(final byte[] array) {
		this.buf.readBytes(array);
		return this;
	}

	@Override
	public ByteBuf readBytes(final byte[] array, final int n, final int n2) {
		this.buf.readBytes(array, n, n2);
		return this;
	}

	@Override
	public ByteBuf readBytes(final ByteBuffer byteBuffer) {
		this.buf.readBytes(byteBuffer);
		return this;
	}

	@Override
	public ByteBuf readBytes(final OutputStream outputStream, final int n) throws IOException {
		this.buf.readBytes(outputStream, n);
		return this;
	}

	@Override
	public int readBytes(final GatheringByteChannel gatheringByteChannel, final int n) throws IOException {
		return this.buf.readBytes(gatheringByteChannel, n);
	}

	@Override
	public int readBytes(final FileChannel fileChannel, final long n, final int n2) throws IOException {
		return this.buf.readBytes(fileChannel, n, n2);
	}

	@Override
	public CharSequence readCharSequence(final int n, final Charset charset) {
		return this.buf.readCharSequence(n, charset);
	}

	@Override
	public ByteBuf skipBytes(final int n) {
		this.buf.skipBytes(n);
		return this;
	}

	@Override
	public ByteBuf writeBoolean(final boolean b) {
		this.buf.writeBoolean(b);
		return this;
	}

	@Override
	public ByteBuf writeByte(final int n) {
		this.buf.writeByte(n);
		return this;
	}

	@Override
	public ByteBuf writeShort(final int n) {
		this.buf.writeShort(n);
		return this;
	}

	@Override
	public ByteBuf writeShortLE(final int n) {
		this.buf.writeShortLE(n);
		return this;
	}

	@Override
	public ByteBuf writeMedium(final int n) {
		this.buf.writeMedium(n);
		return this;
	}

	@Override
	public ByteBuf writeMediumLE(final int n) {
		this.buf.writeMediumLE(n);
		return this;
	}

	@Override
	public ByteBuf writeInt(final int n) {
		this.buf.writeInt(n);
		return this;
	}

	@Override
	public ByteBuf writeIntLE(final int n) {
		this.buf.writeIntLE(n);
		return this;
	}

	@Override
	public ByteBuf writeLong(final long n) {
		this.buf.writeLong(n);
		return this;
	}

	@Override
	public ByteBuf writeLongLE(final long n) {
		this.buf.writeLongLE(n);
		return this;
	}

	@Override
	public ByteBuf writeChar(final int n) {
		this.buf.writeChar(n);
		return this;
	}

	@Override
	public ByteBuf writeFloat(final float n) {
		this.buf.writeFloat(n);
		return this;
	}

	@Override
	public ByteBuf writeDouble(final double n) {
		this.buf.writeDouble(n);
		return this;
	}

	@Override
	public ByteBuf writeBytes(final ByteBuf byteBuf) {
		this.buf.writeBytes(byteBuf);
		return this;
	}

	@Override
	public ByteBuf writeBytes(final ByteBuf byteBuf, final int n) {
		this.buf.writeBytes(byteBuf, n);
		return this;
	}

	@Override
	public ByteBuf writeBytes(final ByteBuf byteBuf, final int n, final int n2) {
		this.buf.writeBytes(byteBuf, n, n2);
		return this;
	}

	@Override
	public ByteBuf writeBytes(final byte[] array) {
		this.buf.writeBytes(array);
		return this;
	}

	@Override
	public ByteBuf writeBytes(final byte[] array, final int n, final int n2) {
		this.buf.writeBytes(array, n, n2);
		return this;
	}

	@Override
	public ByteBuf writeBytes(final ByteBuffer byteBuffer) {
		this.buf.writeBytes(byteBuffer);
		return this;
	}

	@Override
	public int writeBytes(final InputStream inputStream, final int n) throws IOException {
		return this.buf.writeBytes(inputStream, n);
	}

	@Override
	public int writeBytes(final ScatteringByteChannel scatteringByteChannel, final int n) throws IOException {
		return this.buf.writeBytes(scatteringByteChannel, n);
	}

	@Override
	public int writeBytes(final FileChannel fileChannel, final long n, final int n2) throws IOException {
		return this.buf.writeBytes(fileChannel, n, n2);
	}

	@Override
	public ByteBuf writeZero(final int n) {
		this.buf.writeZero(n);
		return this;
	}

	@Override
	public int writeCharSequence(final CharSequence charSequence, final Charset charset) {
		return this.buf.writeCharSequence(charSequence, charset);
	}

	@Override
	public int indexOf(final int n, final int n2, final byte b) {
		return this.buf.indexOf(n, n2, b);
	}

	@Override
	public int bytesBefore(final byte b) {
		return this.buf.bytesBefore(b);
	}

	@Override
	public int bytesBefore(final int n, final byte b) {
		return this.buf.bytesBefore(n, b);
	}

	@Override
	public int bytesBefore(final int n, final int n2, final byte b) {
		return this.buf.bytesBefore(n, n2, b);
	}

	@Override
	public int forEachByte(final ByteProcessor byteProcessor) {
		return this.buf.forEachByte(byteProcessor);
	}

	@Override
	public int forEachByte(final int n, final int n2, final ByteProcessor byteProcessor) {
		return this.buf.forEachByte(n, n2, byteProcessor);
	}

	@Override
	public int forEachByteDesc(final ByteProcessor byteProcessor) {
		return this.buf.forEachByteDesc(byteProcessor);
	}

	@Override
	public int forEachByteDesc(final int n, final int n2, final ByteProcessor byteProcessor) {
		return this.buf.forEachByteDesc(n, n2, byteProcessor);
	}

	@Override
	public ByteBuf copy() {
		return this.buf.copy();
	}

	@Override
	public ByteBuf copy(final int n, final int n2) {
		return this.buf.copy(n, n2);
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
	public ByteBuf slice(final int n, final int n2) {
		return this.buf.slice(n, n2);
	}

	@Override
	public ByteBuf retainedSlice(final int n, final int n2) {
		return this.buf.retainedSlice(n, n2);
	}

	@Override
	public ByteBuf duplicate() {
		return this.buf.duplicate();
	}

	@Override
	public ByteBuf retainedDuplicate() {
		return this.buf.retainedDuplicate();
	}

	@Override
	public int nioBufferCount() {
		return this.buf.nioBufferCount();
	}

	@Override
	public ByteBuffer nioBuffer() {
		return this.buf.nioBuffer();
	}

	@Override
	public ByteBuffer nioBuffer(final int n, final int n2) {
		return this.buf.nioBuffer(n, n2);
	}

	@Override
	public ByteBuffer[] nioBuffers() {
		return this.buf.nioBuffers();
	}

	@Override
	public ByteBuffer[] nioBuffers(final int n, final int n2) {
		return this.buf.nioBuffers(n, n2);
	}

	@Override
	public ByteBuffer internalNioBuffer(final int n, final int n2) {
		return this.buf.internalNioBuffer(n, n2);
	}

	@Override
	public boolean hasArray() {
		return this.buf.hasArray();
	}

	@Override
	public byte[] array() {
		return this.buf.array();
	}

	@Override
	public int arrayOffset() {
		return this.buf.arrayOffset();
	}

	@Override
	public String toString(final Charset charset) {
		return this.buf.toString(charset);
	}

	@Override
	public String toString(final int n, final int n2, final Charset charset) {
		return this.buf.toString(n, n2, charset);
	}

	@Override
	public int hashCode() {
		return this.buf.hashCode();
	}

	@Override
	public boolean equals(final Object o) {
		return this.buf.equals(o);
	}

	@Override
	public int compareTo(final ByteBuf byteBuf) {
		return this.buf.compareTo(byteBuf);
	}

	@Override
	public String toString() {
		return StringUtil.simpleClassName(this) + '(' + this.buf.toString() + ')';
	}

	@Override
	public ByteBuf retain(final int n) {
		this.buf.retain(n);
		return this;
	}

	@Override
	public ByteBuf retain() {
		this.buf.retain();
		return this;
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
	public final boolean isReadable(final int n) {
		return this.buf.isReadable(n);
	}

	@Override
	public final boolean isWritable(final int n) {
		return this.buf.isWritable(n);
	}

	@Override
	public final int refCnt() {
		return this.buf.refCnt();
	}

	@Override
	public boolean release() {
		return this.buf.release();
	}

	@Override
	public boolean release(final int n) {
		return this.buf.release(n);
	}

}
