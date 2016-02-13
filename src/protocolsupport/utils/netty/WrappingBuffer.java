package protocolsupport.utils.netty;

import java.io.IOException;
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

public class WrappingBuffer extends ByteBuf {

	protected ByteBuf buf;

	public void setBuf(ByteBuf buf) {
		this.buf = buf;
	}

	@Override
	public int capacity() {
		return this.buf.capacity();
	}

	@Override
	public ByteBuf capacity(final int i) {
		return this.buf.capacity(i);
	}

	@Override
	public int maxCapacity() {
		return this.buf.maxCapacity();
	}

	@Override
	public ByteBufAllocator alloc() {
		return this.buf.alloc();
	}

	@Override
	public ByteOrder order() {
		return this.buf.order();
	}

	@Override
	public ByteBuf order(final ByteOrder byteorder) {
		return this.buf.order(byteorder);
	}

	@Override
	public ByteBuf unwrap() {
		return this.buf.unwrap();
	}

	@Override
	public boolean isDirect() {
		return this.buf.isDirect();
	}

	@Override
	public int readerIndex() {
		return this.buf.readerIndex();
	}

	@Override
	public ByteBuf readerIndex(final int i) {
		return this.buf.readerIndex(i);
	}

	@Override
	public int writerIndex() {
		return this.buf.writerIndex();
	}

	@Override
	public ByteBuf writerIndex(final int writerIndex) {
		return this.buf.writerIndex(writerIndex);
	}

	@Override
	public ByteBuf setIndex(final int readerIndex, final int writerIndex) {
		return this.buf.setIndex(readerIndex, writerIndex);
	}

	@Override
	public int readableBytes() {
		return this.buf.readableBytes();
	}

	@Override
	public int writableBytes() {
		return this.buf.writableBytes();
	}

	@Override
	public int maxWritableBytes() {
		return this.buf.maxWritableBytes();
	}

	@Override
	public boolean isReadable() {
		return this.buf.isReadable();
	}

	@Override
	public boolean isReadable(final int size) {
		return this.buf.isReadable(size);
	}

	@Override
	public boolean isWritable() {
		return this.buf.isWritable();
	}

	@Override
	public boolean isWritable(final int i) {
		return this.buf.isWritable(i);
	}

	@Override
	public ByteBuf clear() {
		return this.buf.clear();
	}

	@Override
	public ByteBuf markReaderIndex() {
		return this.buf.markReaderIndex();
	}

	@Override
	public ByteBuf resetReaderIndex() {
		return this.buf.resetReaderIndex();
	}

	@Override
	public ByteBuf markWriterIndex() {
		return this.buf.markWriterIndex();
	}

	@Override
	public ByteBuf resetWriterIndex() {
		return this.buf.resetWriterIndex();
	}

	@Override
	public ByteBuf discardReadBytes() {
		return this.buf.discardReadBytes();
	}

	@Override
	public ByteBuf discardSomeReadBytes() {
		return this.buf.discardSomeReadBytes();
	}

	@Override
	public ByteBuf ensureWritable(final int size) {
		return this.buf.ensureWritable(size);
	}

	@Override
	public int ensureWritable(final int size, final boolean flag) {
		return this.buf.ensureWritable(size, flag);
	}

	@Override
	public boolean getBoolean(final int index) {
		return this.buf.getBoolean(index);
	}

	@Override
	public byte getByte(final int index) {
		return this.buf.getByte(index);
	}

	@Override
	public short getUnsignedByte(final int index) {
		return this.buf.getUnsignedByte(index);
	}

	@Override
	public short getShort(final int index) {
		return this.buf.getShort(index);
	}

	@Override
	public int getUnsignedShort(final int index) {
		return this.buf.getUnsignedShort(index);
	}

	@Override
	public int getMedium(final int index) {
		return this.buf.getMedium(index);
	}

	@Override
	public int getUnsignedMedium(final int index) {
		return this.buf.getUnsignedMedium(index);
	}

	@Override
	public int getInt(final int index) {
		return this.buf.getInt(index);
	}

	@Override
	public long getUnsignedInt(final int index) {
		return this.buf.getUnsignedInt(index);
	}

	@Override
	public long getLong(final int index) {
		return this.buf.getLong(index);
	}

	@Override
	public char getChar(final int index) {
		return this.buf.getChar(index);
	}

	@Override
	public float getFloat(final int index) {
		return this.buf.getFloat(index);
	}

	@Override
	public double getDouble(final int index) {
		return this.buf.getDouble(index);
	}

	@Override
	public ByteBuf getBytes(final int index, final ByteBuf bytebuf) {
		return this.buf.getBytes(index, bytebuf);
	}

	@Override
	public ByteBuf getBytes(final int index, final ByteBuf bytebuf, final int length) {
		return this.buf.getBytes(index, bytebuf, length);
	}

	@Override
	public ByteBuf getBytes(final int index, final ByteBuf bytebuf, final int bytebufindex, final int length) {
		return this.buf.getBytes(index, bytebuf, bytebufindex, length);
	}

	@Override
	public ByteBuf getBytes(final int index, final byte[] abyte) {
		return this.buf.getBytes(index, abyte);
	}

	@Override
	public ByteBuf getBytes(final int index, final byte[] abyte, final int abyteindex, final int length) {
		return this.buf.getBytes(index, abyte, abyteindex, length);
	}

	@Override
	public ByteBuf getBytes(final int index, final ByteBuffer bytebuffer) {
		return this.buf.getBytes(index, bytebuffer);
	}

	@Override
	public ByteBuf getBytes(final int index, final OutputStream outputstream, final int length) throws IOException {
		return this.buf.getBytes(index, outputstream, length);
	}

	@Override
	public int getBytes(final int index, final GatheringByteChannel gatheringbytechannel, final int length) throws IOException {
		return this.buf.getBytes(index, gatheringbytechannel, length);
	}

	@Override
	public ByteBuf setBoolean(final int index, final boolean b) {
		return this.buf.setBoolean(index, b);
	}

	@Override
	public ByteBuf setByte(final int index, final int b) {
		return this.buf.setByte(index, b);
	}

	@Override
	public ByteBuf setShort(final int index, final int s) {
		return this.buf.setShort(index, s);
	}

	@Override
	public ByteBuf setMedium(final int index, final int m) {
		return this.buf.setMedium(index, m);
	}

	@Override
	public ByteBuf setInt(final int index, final int i) {
		return this.buf.setInt(index, i);
	}

	@Override
	public ByteBuf setLong(final int index, final long l) {
		return this.buf.setLong(index, l);
	}

	@Override
	public ByteBuf setChar(final int index, final int c) {
		return this.buf.setChar(index, c);
	}

	@Override
	public ByteBuf setFloat(final int index, final float f) {
		return this.buf.setFloat(index, f);
	}

	@Override
	public ByteBuf setDouble(final int index, final double d) {
		return this.buf.setDouble(index, d);
	}

	@Override
	public ByteBuf setBytes(final int index, final ByteBuf bytebuf) {
		return this.buf.setBytes(index, bytebuf);
	}

	@Override
	public ByteBuf setBytes(final int index, final ByteBuf bytebuf, final int length) {
		return this.buf.setBytes(index, bytebuf, length);
	}

	@Override
	public ByteBuf setBytes(final int index, final ByteBuf bytebuf, final int bytebufindex, final int length) {
		return this.buf.setBytes(index, bytebuf, bytebufindex, length);
	}

	@Override
	public ByteBuf setBytes(final int index, final byte[] abyte) {
		return this.buf.setBytes(index, abyte);
	}

	@Override
	public ByteBuf setBytes(final int index, final byte[] abyte, final int abyteindex, final int length) {
		return this.buf.setBytes(index, abyte, abyteindex, length);
	}

	@Override
	public ByteBuf setBytes(final int index, final ByteBuffer bytebuffer) {
		return this.buf.setBytes(index, bytebuffer);
	}

	@Override
	public int setBytes(final int index, final InputStream inputstream, final int length) throws IOException {
		return this.buf.setBytes(index, inputstream, length);
	}

	@Override
	public int setBytes(final int index, final ScatteringByteChannel scatteringbytechannel, final int length) throws IOException {
		return this.buf.setBytes(index, scatteringbytechannel, length);
	}

	@Override
	public ByteBuf setZero(final int index, final int length) {
		return this.buf.setZero(index, length);
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
	public int readUnsignedShort() {
		return this.buf.readUnsignedShort();
	}

	@Override
	public int readMedium() {
		return this.buf.readMedium();
	}

	@Override
	public int readUnsignedMedium() {
		return this.buf.readUnsignedMedium();
	}

	@Override
	public int readInt() {
		return this.buf.readInt();
	}

	@Override
	public long readUnsignedInt() {
		return this.buf.readUnsignedInt();
	}

	@Override
	public long readLong() {
		return this.buf.readLong();
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
	public ByteBuf readBytes(final int length) {
		return this.buf.readBytes(length);
	}

	@Override
	public ByteBuf readSlice(final int length) {
		return this.buf.readSlice(length);
	}

	@Override
	public ByteBuf readBytes(final ByteBuf bytebuf) {
		return this.buf.readBytes(bytebuf);
	}

	@Override
	public ByteBuf readBytes(final ByteBuf bytebuf, final int length) {
		return this.buf.readBytes(bytebuf, length);
	}

	@Override
	public ByteBuf readBytes(final ByteBuf bytebuf, final int index, final int length) {
		return this.buf.readBytes(bytebuf, index, length);
	}

	@Override
	public ByteBuf readBytes(final byte[] abyte) {
		return this.buf.readBytes(abyte);
	}

	@Override
	public ByteBuf readBytes(final byte[] abyte, final int index, final int length) {
		return this.buf.readBytes(abyte, index, length);
	}

	@Override
	public ByteBuf readBytes(final ByteBuffer bytebuffer) {
		return this.buf.readBytes(bytebuffer);
	}

	@Override
	public ByteBuf readBytes(final OutputStream outputstream, final int length) throws IOException {
		return this.buf.readBytes(outputstream, length);
	}

	@Override
	public int readBytes(final GatheringByteChannel gatheringbytechannel, final int length) throws IOException {
		return this.buf.readBytes(gatheringbytechannel, length);
	}

	@Override
	public ByteBuf skipBytes(final int length) {
		return this.buf.skipBytes(length);
	}

	@Override
	public ByteBuf writeBoolean(final boolean b) {
		return this.buf.writeBoolean(b);
	}

	@Override
	public ByteBuf writeByte(final int b) {
		return this.buf.writeByte(b);
	}

	@Override
	public ByteBuf writeShort(final int s) {
		return this.buf.writeShort(s);
	}

	@Override
	public ByteBuf writeMedium(final int m) {
		return this.buf.writeMedium(m);
	}

	@Override
	public ByteBuf writeInt(final int i) {
		return this.buf.writeInt(i);
	}

	@Override
	public ByteBuf writeLong(final long l) {
		return this.buf.writeLong(l);
	}

	@Override
	public ByteBuf writeChar(final int c) {
		return this.buf.writeChar(c);
	}

	@Override
	public ByteBuf writeFloat(final float f) {
		return this.buf.writeFloat(f);
	}

	@Override
	public ByteBuf writeDouble(final double d) {
		return this.buf.writeDouble(d);
	}

	@Override
	public ByteBuf writeBytes(final ByteBuf bytebuf) {
		return this.buf.writeBytes(bytebuf);
	}

	@Override
	public ByteBuf writeBytes(final ByteBuf bytebuf, final int length) {
		return this.buf.writeBytes(bytebuf, length);
	}

	@Override
	public ByteBuf writeBytes(final ByteBuf bytebuf, final int index, final int length) {
		return this.buf.writeBytes(bytebuf, index, length);
	}

	@Override
	public ByteBuf writeBytes(final byte[] abyte) {
		return this.buf.writeBytes(abyte);
	}

	@Override
	public ByteBuf writeBytes(final byte[] abyte, final int index, final int length) {
		return this.buf.writeBytes(abyte, index, length);
	}

	@Override
	public ByteBuf writeBytes(final ByteBuffer bytebuffer) {
		return this.buf.writeBytes(bytebuffer);
	}

	@Override
	public int writeBytes(final InputStream inputstream, final int length) throws IOException {
		return this.buf.writeBytes(inputstream, length);
	}

	@Override
	public int writeBytes(final ScatteringByteChannel scatteringbytechannel, final int length) throws IOException {
		return this.buf.writeBytes(scatteringbytechannel, length);
	}

	@Override
	public ByteBuf writeZero(final int length) {
		return this.buf.writeZero(length);
	}

	@Override
	public int indexOf(final int fromIndex, final int toIndex, final byte value) {
		return this.buf.indexOf(fromIndex, toIndex, value);
	}

	@Override
	public int bytesBefore(final byte value) {
		return this.buf.bytesBefore(value);
	}

	@Override
	public int bytesBefore(final int length, final byte value) {
		return this.buf.bytesBefore(length, value);
	}

	@Override
	public int bytesBefore(final int index, final int length, final byte b0) {
		return this.buf.bytesBefore(index, length, b0);
	}

	@Override
	public int forEachByte(final ByteBufProcessor bytebufprocessor) {
		return this.buf.forEachByte(bytebufprocessor);
	}

	@Override
	public int forEachByte(final int index, final int length, final ByteBufProcessor bytebufprocessor) {
		return this.buf.forEachByte(index, length, bytebufprocessor);
	}

	@Override
	public int forEachByteDesc(final ByteBufProcessor bytebufprocessor) {
		return this.buf.forEachByteDesc(bytebufprocessor);
	}

	@Override
	public int forEachByteDesc(final int index, final int length, final ByteBufProcessor bytebufprocessor) {
		return this.buf.forEachByteDesc(index, length, bytebufprocessor);
	}

	@Override
	public ByteBuf copy() {
		return this.buf.copy();
	}

	@Override
	public ByteBuf copy(final int index, final int length) {
		return this.buf.copy(index, length);
	}

	@Override
	public ByteBuf slice() {
		return this.buf.slice();
	}

	@Override
	public ByteBuf slice(final int index, final int length) {
		return this.buf.slice(index, length);
	}

	@Override
	public ByteBuf duplicate() {
		return this.buf.duplicate();
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
	public ByteBuffer nioBuffer(final int index, final int length) {
		return this.buf.nioBuffer(index, length);
	}

	@Override
	public ByteBuffer internalNioBuffer(final int index, final int length) {
		return this.buf.internalNioBuffer(index, length);
	}

	@Override
	public ByteBuffer[] nioBuffers() {
		return this.buf.nioBuffers();
	}

	@Override
	public ByteBuffer[] nioBuffers(final int index, final int length) {
		return this.buf.nioBuffers(index, length);
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
	public boolean hasMemoryAddress() {
		return this.buf.hasMemoryAddress();
	}

	@Override
	public long memoryAddress() {
		return this.buf.memoryAddress();
	}

	@Override
	public String toString(final Charset charset) {
		return this.buf.toString(charset);
	}

	@Override
	public String toString(final int index, final int length, final Charset charset) {
		return this.buf.toString(index, length, charset);
	}

	@Override
	public int hashCode() {
		return this.buf.hashCode();
	}

	@Override
	public boolean equals(final Object object) {
		return this.buf.equals(object);
	}

	@Override
	public int compareTo(final ByteBuf bytebuf) {
		return this.buf.compareTo(bytebuf);
	}

	@Override
	public String toString() {
		return this.buf.toString();
	}

	@Override
	public ByteBuf retain(final int amount) {
		return this.buf.retain(amount);
	}

	@Override
	public ByteBuf retain() {
		return this.buf.retain();
	}

	@Override
	public int refCnt() {
		return this.buf.refCnt();
	}

	@Override
	public boolean release() {
		return this.buf.release();
	}

	@Override
	public boolean release(final int amount) {
		return this.buf.release(amount);
	}

}
