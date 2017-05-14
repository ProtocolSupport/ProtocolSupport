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

	public final boolean hasMemoryAddress() {
		return this.buf.hasMemoryAddress();
	}

	public final long memoryAddress() {
		return this.buf.memoryAddress();
	}

	public final int capacity() {
		return this.buf.capacity();
	}

	public ByteBuf capacity(final int n) {
		this.buf.capacity(n);
		return this;
	}

	public final int maxCapacity() {
		return this.buf.maxCapacity();
	}

	public final ByteBufAllocator alloc() {
		return this.buf.alloc();
	}

	@SuppressWarnings("deprecation")
	public final ByteOrder order() {
		return this.buf.order();
	}

	@SuppressWarnings("deprecation")
	public ByteBuf order(final ByteOrder byteOrder) {
		return this.buf.order(byteOrder);
	}

	public final ByteBuf unwrap() {
		return this.buf;
	}

	public ByteBuf asReadOnly() {
		return this.buf.asReadOnly();
	}

	public boolean isReadOnly() {
		return this.buf.isReadOnly();
	}

	public final boolean isDirect() {
		return this.buf.isDirect();
	}

	public final int readerIndex() {
		return this.buf.readerIndex();
	}

	public final ByteBuf readerIndex(final int n) {
		this.buf.readerIndex(n);
		return this;
	}

	public final int writerIndex() {
		return this.buf.writerIndex();
	}

	public final ByteBuf writerIndex(final int n) {
		this.buf.writerIndex(n);
		return this;
	}

	public ByteBuf setIndex(final int n, final int n2) {
		this.buf.setIndex(n, n2);
		return this;
	}

	public final int readableBytes() {
		return this.buf.readableBytes();
	}

	public final int writableBytes() {
		return this.buf.writableBytes();
	}

	public final int maxWritableBytes() {
		return this.buf.maxWritableBytes();
	}

	public final boolean isReadable() {
		return this.buf.isReadable();
	}

	public final boolean isWritable() {
		return this.buf.isWritable();
	}

	public final ByteBuf clear() {
		this.buf.clear();
		return this;
	}

	public final ByteBuf markReaderIndex() {
		this.buf.markReaderIndex();
		return this;
	}

	public final ByteBuf resetReaderIndex() {
		this.buf.resetReaderIndex();
		return this;
	}

	public final ByteBuf markWriterIndex() {
		this.buf.markWriterIndex();
		return this;
	}

	public final ByteBuf resetWriterIndex() {
		this.buf.resetWriterIndex();
		return this;
	}

	public ByteBuf discardReadBytes() {
		this.buf.discardReadBytes();
		return this;
	}

	public ByteBuf discardSomeReadBytes() {
		this.buf.discardSomeReadBytes();
		return this;
	}

	public ByteBuf ensureWritable(final int n) {
		this.buf.ensureWritable(n);
		return this;
	}

	public int ensureWritable(final int n, final boolean b) {
		return this.buf.ensureWritable(n, b);
	}

	public boolean getBoolean(final int n) {
		return this.buf.getBoolean(n);
	}

	public byte getByte(final int n) {
		return this.buf.getByte(n);
	}

	public short getUnsignedByte(final int n) {
		return this.buf.getUnsignedByte(n);
	}

	public short getShort(final int n) {
		return this.buf.getShort(n);
	}

	public short getShortLE(final int n) {
		return this.buf.getShortLE(n);
	}

	public int getUnsignedShort(final int n) {
		return this.buf.getUnsignedShort(n);
	}

	public int getUnsignedShortLE(final int n) {
		return this.buf.getUnsignedShortLE(n);
	}

	public int getMedium(final int n) {
		return this.buf.getMedium(n);
	}

	public int getMediumLE(final int n) {
		return this.buf.getMediumLE(n);
	}

	public int getUnsignedMedium(final int n) {
		return this.buf.getUnsignedMedium(n);
	}

	public int getUnsignedMediumLE(final int n) {
		return this.buf.getUnsignedMediumLE(n);
	}

	public int getInt(final int n) {
		return this.buf.getInt(n);
	}

	public int getIntLE(final int n) {
		return this.buf.getIntLE(n);
	}

	public long getUnsignedInt(final int n) {
		return this.buf.getUnsignedInt(n);
	}

	public long getUnsignedIntLE(final int n) {
		return this.buf.getUnsignedIntLE(n);
	}

	public long getLong(final int n) {
		return this.buf.getLong(n);
	}

	public long getLongLE(final int n) {
		return this.buf.getLongLE(n);
	}

	public char getChar(final int n) {
		return this.buf.getChar(n);
	}

	public float getFloat(final int n) {
		return this.buf.getFloat(n);
	}

	public double getDouble(final int n) {
		return this.buf.getDouble(n);
	}

	public ByteBuf getBytes(final int n, final ByteBuf byteBuf) {
		this.buf.getBytes(n, byteBuf);
		return this;
	}

	public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2) {
		this.buf.getBytes(n, byteBuf, n2);
		return this;
	}

	public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
		this.buf.getBytes(n, byteBuf, n2, n3);
		return this;
	}

	public ByteBuf getBytes(final int n, final byte[] array) {
		this.buf.getBytes(n, array);
		return this;
	}

	public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
		this.buf.getBytes(n, array, n2, n3);
		return this;
	}

	public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
		this.buf.getBytes(n, byteBuffer);
		return this;
	}

	public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) throws IOException {
		this.buf.getBytes(n, outputStream, n2);
		return this;
	}

	public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) throws IOException {
		return this.buf.getBytes(n, gatheringByteChannel, n2);
	}

	public int getBytes(final int n, final FileChannel fileChannel, final long n2, final int n3) throws IOException {
		return this.buf.getBytes(n, fileChannel, n2, n3);
	}

	public CharSequence getCharSequence(final int n, final int n2, final Charset charset) {
		return this.buf.getCharSequence(n, n2, charset);
	}

	public ByteBuf setBoolean(final int n, final boolean b) {
		this.buf.setBoolean(n, b);
		return this;
	}

	public ByteBuf setByte(final int n, final int n2) {
		this.buf.setByte(n, n2);
		return this;
	}

	public ByteBuf setShort(final int n, final int n2) {
		this.buf.setShort(n, n2);
		return this;
	}

	public ByteBuf setShortLE(final int n, final int n2) {
		this.buf.setShortLE(n, n2);
		return this;
	}

	public ByteBuf setMedium(final int n, final int n2) {
		this.buf.setMedium(n, n2);
		return this;
	}

	public ByteBuf setMediumLE(final int n, final int n2) {
		this.buf.setMediumLE(n, n2);
		return this;
	}

	public ByteBuf setInt(final int n, final int n2) {
		this.buf.setInt(n, n2);
		return this;
	}

	public ByteBuf setIntLE(final int n, final int n2) {
		this.buf.setIntLE(n, n2);
		return this;
	}

	public ByteBuf setLong(final int n, final long n2) {
		this.buf.setLong(n, n2);
		return this;
	}

	public ByteBuf setLongLE(final int n, final long n2) {
		this.buf.setLongLE(n, n2);
		return this;
	}

	public ByteBuf setChar(final int n, final int n2) {
		this.buf.setChar(n, n2);
		return this;
	}

	public ByteBuf setFloat(final int n, final float n2) {
		this.buf.setFloat(n, n2);
		return this;
	}

	public ByteBuf setDouble(final int n, final double n2) {
		this.buf.setDouble(n, n2);
		return this;
	}

	public ByteBuf setBytes(final int n, final ByteBuf byteBuf) {
		this.buf.setBytes(n, byteBuf);
		return this;
	}

	public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2) {
		this.buf.setBytes(n, byteBuf, n2);
		return this;
	}

	public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
		this.buf.setBytes(n, byteBuf, n2, n3);
		return this;
	}

	public ByteBuf setBytes(final int n, final byte[] array) {
		this.buf.setBytes(n, array);
		return this;
	}

	public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
		this.buf.setBytes(n, array, n2, n3);
		return this;
	}

	public ByteBuf setBytes(final int n, final ByteBuffer byteBuffer) {
		this.buf.setBytes(n, byteBuffer);
		return this;
	}

	public int setBytes(final int n, final InputStream inputStream, final int n2) throws IOException {
		return this.buf.setBytes(n, inputStream, n2);
	}

	public int setBytes(final int n, final ScatteringByteChannel scatteringByteChannel, final int n2) throws IOException {
		return this.buf.setBytes(n, scatteringByteChannel, n2);
	}

	public int setBytes(final int n, final FileChannel fileChannel, final long n2, final int n3) throws IOException {
		return this.buf.setBytes(n, fileChannel, n2, n3);
	}

	public ByteBuf setZero(final int n, final int n2) {
		this.buf.setZero(n, n2);
		return this;
	}

	public int setCharSequence(final int n, final CharSequence charSequence, final Charset charset) {
		return this.buf.setCharSequence(n, charSequence, charset);
	}

	public boolean readBoolean() {
		return this.buf.readBoolean();
	}

	public byte readByte() {
		return this.buf.readByte();
	}

	public short readUnsignedByte() {
		return this.buf.readUnsignedByte();
	}

	public short readShort() {
		return this.buf.readShort();
	}

	public short readShortLE() {
		return this.buf.readShortLE();
	}

	public int readUnsignedShort() {
		return this.buf.readUnsignedShort();
	}

	public int readUnsignedShortLE() {
		return this.buf.readUnsignedShortLE();
	}

	public int readMedium() {
		return this.buf.readMedium();
	}

	public int readMediumLE() {
		return this.buf.readMediumLE();
	}

	public int readUnsignedMedium() {
		return this.buf.readUnsignedMedium();
	}

	public int readUnsignedMediumLE() {
		return this.buf.readUnsignedMediumLE();
	}

	public int readInt() {
		return this.buf.readInt();
	}

	public int readIntLE() {
		return this.buf.readIntLE();
	}

	public long readUnsignedInt() {
		return this.buf.readUnsignedInt();
	}

	public long readUnsignedIntLE() {
		return this.buf.readUnsignedIntLE();
	}

	public long readLong() {
		return this.buf.readLong();
	}

	public long readLongLE() {
		return this.buf.readLongLE();
	}

	public char readChar() {
		return this.buf.readChar();
	}

	public float readFloat() {
		return this.buf.readFloat();
	}

	public double readDouble() {
		return this.buf.readDouble();
	}

	public ByteBuf readBytes(final int n) {
		return this.buf.readBytes(n);
	}

	public ByteBuf readSlice(final int n) {
		return this.buf.readSlice(n);
	}

	public ByteBuf readRetainedSlice(final int n) {
		return this.buf.readRetainedSlice(n);
	}

	public ByteBuf readBytes(final ByteBuf byteBuf) {
		this.buf.readBytes(byteBuf);
		return this;
	}

	public ByteBuf readBytes(final ByteBuf byteBuf, final int n) {
		this.buf.readBytes(byteBuf, n);
		return this;
	}

	public ByteBuf readBytes(final ByteBuf byteBuf, final int n, final int n2) {
		this.buf.readBytes(byteBuf, n, n2);
		return this;
	}

	public ByteBuf readBytes(final byte[] array) {
		this.buf.readBytes(array);
		return this;
	}

	public ByteBuf readBytes(final byte[] array, final int n, final int n2) {
		this.buf.readBytes(array, n, n2);
		return this;
	}

	public ByteBuf readBytes(final ByteBuffer byteBuffer) {
		this.buf.readBytes(byteBuffer);
		return this;
	}

	public ByteBuf readBytes(final OutputStream outputStream, final int n) throws IOException {
		this.buf.readBytes(outputStream, n);
		return this;
	}

	public int readBytes(final GatheringByteChannel gatheringByteChannel, final int n) throws IOException {
		return this.buf.readBytes(gatheringByteChannel, n);
	}

	public int readBytes(final FileChannel fileChannel, final long n, final int n2) throws IOException {
		return this.buf.readBytes(fileChannel, n, n2);
	}

	public CharSequence readCharSequence(final int n, final Charset charset) {
		return this.buf.readCharSequence(n, charset);
	}

	public ByteBuf skipBytes(final int n) {
		this.buf.skipBytes(n);
		return this;
	}

	public ByteBuf writeBoolean(final boolean b) {
		this.buf.writeBoolean(b);
		return this;
	}

	public ByteBuf writeByte(final int n) {
		this.buf.writeByte(n);
		return this;
	}

	public ByteBuf writeShort(final int n) {
		this.buf.writeShort(n);
		return this;
	}

	public ByteBuf writeShortLE(final int n) {
		this.buf.writeShortLE(n);
		return this;
	}

	public ByteBuf writeMedium(final int n) {
		this.buf.writeMedium(n);
		return this;
	}

	public ByteBuf writeMediumLE(final int n) {
		this.buf.writeMediumLE(n);
		return this;
	}

	public ByteBuf writeInt(final int n) {
		this.buf.writeInt(n);
		return this;
	}

	public ByteBuf writeIntLE(final int n) {
		this.buf.writeIntLE(n);
		return this;
	}

	public ByteBuf writeLong(final long n) {
		this.buf.writeLong(n);
		return this;
	}

	public ByteBuf writeLongLE(final long n) {
		this.buf.writeLongLE(n);
		return this;
	}

	public ByteBuf writeChar(final int n) {
		this.buf.writeChar(n);
		return this;
	}

	public ByteBuf writeFloat(final float n) {
		this.buf.writeFloat(n);
		return this;
	}

	public ByteBuf writeDouble(final double n) {
		this.buf.writeDouble(n);
		return this;
	}

	public ByteBuf writeBytes(final ByteBuf byteBuf) {
		this.buf.writeBytes(byteBuf);
		return this;
	}

	public ByteBuf writeBytes(final ByteBuf byteBuf, final int n) {
		this.buf.writeBytes(byteBuf, n);
		return this;
	}

	public ByteBuf writeBytes(final ByteBuf byteBuf, final int n, final int n2) {
		this.buf.writeBytes(byteBuf, n, n2);
		return this;
	}

	public ByteBuf writeBytes(final byte[] array) {
		this.buf.writeBytes(array);
		return this;
	}

	public ByteBuf writeBytes(final byte[] array, final int n, final int n2) {
		this.buf.writeBytes(array, n, n2);
		return this;
	}

	public ByteBuf writeBytes(final ByteBuffer byteBuffer) {
		this.buf.writeBytes(byteBuffer);
		return this;
	}

	public int writeBytes(final InputStream inputStream, final int n) throws IOException {
		return this.buf.writeBytes(inputStream, n);
	}

	public int writeBytes(final ScatteringByteChannel scatteringByteChannel, final int n) throws IOException {
		return this.buf.writeBytes(scatteringByteChannel, n);
	}

	public int writeBytes(final FileChannel fileChannel, final long n, final int n2) throws IOException {
		return this.buf.writeBytes(fileChannel, n, n2);
	}

	public ByteBuf writeZero(final int n) {
		this.buf.writeZero(n);
		return this;
	}

	public int writeCharSequence(final CharSequence charSequence, final Charset charset) {
		return this.buf.writeCharSequence(charSequence, charset);
	}

	public int indexOf(final int n, final int n2, final byte b) {
		return this.buf.indexOf(n, n2, b);
	}

	public int bytesBefore(final byte b) {
		return this.buf.bytesBefore(b);
	}

	public int bytesBefore(final int n, final byte b) {
		return this.buf.bytesBefore(n, b);
	}

	public int bytesBefore(final int n, final int n2, final byte b) {
		return this.buf.bytesBefore(n, n2, b);
	}

	public int forEachByte(final ByteProcessor byteProcessor) {
		return this.buf.forEachByte(byteProcessor);
	}

	public int forEachByte(final int n, final int n2, final ByteProcessor byteProcessor) {
		return this.buf.forEachByte(n, n2, byteProcessor);
	}

	public int forEachByteDesc(final ByteProcessor byteProcessor) {
		return this.buf.forEachByteDesc(byteProcessor);
	}

	public int forEachByteDesc(final int n, final int n2, final ByteProcessor byteProcessor) {
		return this.buf.forEachByteDesc(n, n2, byteProcessor);
	}

	public ByteBuf copy() {
		return this.buf.copy();
	}

	public ByteBuf copy(final int n, final int n2) {
		return this.buf.copy(n, n2);
	}

	public ByteBuf slice() {
		return this.buf.slice();
	}

	public ByteBuf retainedSlice() {
		return this.buf.retainedSlice();
	}

	public ByteBuf slice(final int n, final int n2) {
		return this.buf.slice(n, n2);
	}

	public ByteBuf retainedSlice(final int n, final int n2) {
		return this.buf.retainedSlice(n, n2);
	}

	public ByteBuf duplicate() {
		return this.buf.duplicate();
	}

	public ByteBuf retainedDuplicate() {
		return this.buf.retainedDuplicate();
	}

	public int nioBufferCount() {
		return this.buf.nioBufferCount();
	}

	public ByteBuffer nioBuffer() {
		return this.buf.nioBuffer();
	}

	public ByteBuffer nioBuffer(final int n, final int n2) {
		return this.buf.nioBuffer(n, n2);
	}

	public ByteBuffer[] nioBuffers() {
		return this.buf.nioBuffers();
	}

	public ByteBuffer[] nioBuffers(final int n, final int n2) {
		return this.buf.nioBuffers(n, n2);
	}

	public ByteBuffer internalNioBuffer(final int n, final int n2) {
		return this.buf.internalNioBuffer(n, n2);
	}

	public boolean hasArray() {
		return this.buf.hasArray();
	}

	public byte[] array() {
		return this.buf.array();
	}

	public int arrayOffset() {
		return this.buf.arrayOffset();
	}

	public String toString(final Charset charset) {
		return this.buf.toString(charset);
	}

	public String toString(final int n, final int n2, final Charset charset) {
		return this.buf.toString(n, n2, charset);
	}

	public int hashCode() {
		return this.buf.hashCode();
	}

	public boolean equals(final Object o) {
		return this.buf.equals(o);
	}

	public int compareTo(final ByteBuf byteBuf) {
		return this.buf.compareTo(byteBuf);
	}

	public String toString() {
		return StringUtil.simpleClassName((Object) this) + '(' + this.buf.toString() + ')';
	}

	public ByteBuf retain(final int n) {
		this.buf.retain(n);
		return this;
	}

	public ByteBuf retain() {
		this.buf.retain();
		return this;
	}

	public ByteBuf touch() {
		this.buf.touch();
		return this;
	}

	public ByteBuf touch(final Object o) {
		this.buf.touch(o);
		return this;
	}

	public final boolean isReadable(final int n) {
		return this.buf.isReadable(n);
	}

	public final boolean isWritable(final int n) {
		return this.buf.isWritable(n);
	}

	public final int refCnt() {
		return this.buf.refCnt();
	}

	public boolean release() {
		return this.buf.release();
	}

	public boolean release(final int n) {
		return this.buf.release(n);
	}

}
