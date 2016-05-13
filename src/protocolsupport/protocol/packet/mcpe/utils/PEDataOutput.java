package protocolsupport.protocol.packet.mcpe.utils;

import io.netty.buffer.ByteBuf;

import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class PEDataOutput implements DataOutput {

	private final ByteBuf buf;
	public PEDataOutput(ByteBuf buf) {
		this.buf = buf.order(ByteOrder.LITTLE_ENDIAN);
	}

	@Override
	public void write(int b) throws IOException {
		buf.writeInt(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		buf.writeBytes(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		buf.writeBytes(b, off, len);
	}

	@Override
	public void writeBoolean(boolean v) throws IOException {
		buf.writeBoolean(v);
	}

	@Override
	public void writeByte(int v) throws IOException {
		buf.writeByte(v);
	}

	@Override
	public void writeShort(int v) throws IOException {
		buf.writeShort(v);
	}

	@Override
	public void writeChar(int v) throws IOException {
		buf.writeChar(v);
	}

	@Override
	public void writeInt(int v) throws IOException {
		buf.writeInt(v);
	}

	@Override
	public void writeLong(long v) throws IOException {
		buf.writeLong(v);
	}

	@Override
	public void writeFloat(float v) throws IOException {
		buf.writeFloat(v);
	}

	@Override
	public void writeDouble(double v) throws IOException {
		buf.writeDouble(v);
	}

	@Override
	public void writeBytes(String s) throws IOException {
		for (char c : s.toCharArray()) {
			writeByte(c);
		}
	}

	@Override
	public void writeChars(String s) throws IOException {
		for (char c : s.toCharArray()) {
			writeChar(c);
		}
	}

	@Override
	public void writeUTF(String s) throws IOException {
		byte[] data = s.getBytes(StandardCharsets.UTF_8);
		writeShort(data.length);
		write(data);
	}

}
