package protocolsupport.protocol.utils;

import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public final class PENetworkNBTDataOutputStream implements DataOutput {

	protected final ByteBuf buf;
	public PENetworkNBTDataOutputStream(ByteBuf buf) {
		this.buf = buf;
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
	public void writeBoolean(boolean b) throws IOException {
		buf.writeBoolean(b);
	}

	@Override
	public void writeByte(int b) throws IOException {
		buf.writeByte(b);
	}

	@Override
	public void writeBytes(String s) throws IOException {
		for (char c : s.toCharArray()) {
			buf.writeByte(c);
		}
	}

	@Override
	public void writeChar(int c) throws IOException {
		writeShort(c);
	}

	@Override
	public void writeChars(String s) throws IOException {
		for (int i = 0; i < s.length(); i++) {
			writeChar(s.charAt(i));
		}
	}

	@Override
	public void writeDouble(double d) throws IOException {
		writeLong(Double.doubleToLongBits(d));
	}

	@Override
	public void writeFloat(float f) throws IOException {
		writeInt(Float.floatToIntBits(f));
	}

	@Override
	public void writeInt(int i) throws IOException {
		VarNumberSerializer.writeSVarInt(buf, i);
	}

	@Override
	public void writeLong(long l) throws IOException {
		VarNumberSerializer.writeSVarLong(buf, l);
	}

	@Override
	public void writeShort(int s) throws IOException {
		buf.writeShort(ByteBufUtil.swapShort((short) s));
	}

	@Override
	public void writeUTF(String str) throws IOException {
		byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
		VarNumberSerializer.writeVarInt(buf, bytes.length);
		write(bytes);
	}

	@Override
	public void write(int b) throws IOException {
		writeByte(b);
	}

}
