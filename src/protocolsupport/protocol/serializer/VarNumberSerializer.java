package protocolsupport.protocol.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;

public class VarNumberSerializer {

	private VarNumberSerializer() {
	}

	public static final int MAX_LENGTH = 5;

	public static int calculateVarIntSize(int value) {
		switch (Integer.numberOfLeadingZeros(value)) {
			case 32:
			case 31:
			case 30:
			case 29:
			case 28:
			case 27:
			case 26:
			case 25: {
				return 1;
			}
			case 24:
			case 23:
			case 22:
			case 21:
			case 20:
			case 19:
			case 18: {
				return 2;
			}
			case 17:
			case 16:
			case 15:
			case 14:
			case 13:
			case 12:
			case 11: {
				return 3;
			}
			case 10:
			case 9:
			case 8:
			case 7:
			case 6:
			case 5:
			case 4: {
				return 4;
			}
			case 3:
			case 2:
			case 1:
			case 0: {
				return 5;
			}
			default: {
				throw new IllegalStateException("Reached impossible branch while calculating var number " + value + " length");
			}
		}
	}

	public static void writeFixedSizeVarInt(ByteBuf to, int i) {
		writeFixedSizeVarInt(to, i, MAX_LENGTH);
	}

	public static void writeFixedSizeVarInt(ByteBuf to, int i, int length) {
		int writerIndex = to.writerIndex();
		while ((i & 0xFFFFFF80) != 0x0) {
			to.writeByte(i | 0x80);
			i >>>= 7;
		}
		int paddingBytes = length - (to.writerIndex() - writerIndex) - 1;
		if (paddingBytes < 0) {
			throw new EncoderException("Fixed size VarInt too big");
		}
		if (paddingBytes == 0) {
			to.writeByte(i);
		} else {
			to.writeByte(i | 0x80);
			while (--paddingBytes > 0) {
				to.writeByte(0x80);
			}
			to.writeByte(0);
		}
	}

	public static int readVarInt(ByteBuf from) {
		int value = 0;
		int length = 0;
		int part;
		do {
			part = from.readByte();
			value |= (part & 0x7F) << (length++ * 7);
			if (length > MAX_LENGTH) {
				throw new DecoderException("VarInt too big");
			}
		} while (part < 0);
		return value;
	}

	public static void writeVarInt(ByteBuf to, int i) {
		while ((i & 0xFFFFFF80) != 0x0) {
			to.writeByte(i | 0x80);
			i >>>= 7;
		}
		to.writeByte(i);
	}

	public static long readVarLong(ByteBuf from) {
		long varlong = 0L;
		int length = 0;
		long part;
		do {
			part = from.readByte();
			varlong |= (part & 0x7F) << (length++ * 7);
			if (length > 10) {
				throw new DecoderException("VarLong too big");
			}
		} while ((part & 0x80) == 0x80);
		return varlong;
	}

	public static void writeVarLong(ByteBuf to, long varlong) {
		while ((varlong & 0xFFFFFFFFFFFFFF80L) != 0x0L) {
			to.writeByte((int) (varlong & 0x7FL) | 0x80);
			varlong >>>= 7;
		}
		to.writeByte((int) varlong);
	}

}
