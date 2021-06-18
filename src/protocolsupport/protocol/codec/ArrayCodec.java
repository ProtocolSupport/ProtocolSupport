package protocolsupport.protocol.codec;

import java.lang.reflect.Array;
import java.util.List;
import java.util.RandomAccess;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;

import io.netty.buffer.ByteBuf;

public class ArrayCodec {

	private ArrayCodec() {
	}

	public static ByteBuf readShortByteArraySlice(ByteBuf from, int limit) {
		int length = from.readShort();
		MiscDataCodec.checkLimit(length, limit);
		return from.readSlice(length);
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] readShortTArray(ByteBuf from, Class<T> tclass, Function<ByteBuf, T> elementReader) {
		T[] array = (T[]) Array.newInstance(tclass, from.readShort());
		for (int i = 0; i < array.length; i++) {
			array[i] = elementReader.apply(from);
		}
		return array;
	}


	public static byte[] readVarIntByteArray(ByteBuf from) {
		return MiscDataCodec.readBytes(from, VarNumberCodec.readVarInt(from));
	}

	public static ByteBuf readVarIntByteArraySlice(ByteBuf from, int limit) {
		int length = VarNumberCodec.readVarInt(from);
		MiscDataCodec.checkLimit(length, limit);
		return from.readSlice(length);
	}

	public static ByteBuf readVarIntByteArraySlice(ByteBuf from) {
		return from.readSlice(VarNumberCodec.readVarInt(from));
	}

	public static long[] readVarIntLongArray(ByteBuf from) {
		long[] array = new long[VarNumberCodec.readVarInt(from)];
		for (int i = 0; i < array.length; i++) {
			array[i] = from.readLong();
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] readVarIntTArray(ByteBuf from, Class<T> tclass, Function<ByteBuf, T> elementReader) {
		T[] array = (T[]) Array.newInstance(tclass, VarNumberCodec.readVarInt(from));
		for (int i = 0; i < array.length; i++) {
			array[i] = elementReader.apply(from);
		}
		return array;
	}

	public static String[] readVarIntVarIntUTF8StringArray(ByteBuf from) {
		return readVarIntTArray(from, String.class, StringCodec::readVarIntUTF8String);
	}


	public static int[] readVarIntVarIntArray(ByteBuf from) {
		int[] array = new int[VarNumberCodec.readVarInt(from)];
		for (int i = 0; i < array.length; i++) {
			array[i] = VarNumberCodec.readVarInt(from);
		}
		return array;
	}

	public static long[] readVarIntVarLongArray(ByteBuf from) {
		long[] array = new long[VarNumberCodec.readVarInt(from)];
		for (int i = 0; i < array.length; i++) {
			array[i] = VarNumberCodec.readVarLong(from);
		}
		return array;
	}


	public static void writeShortByteArray(ByteBuf to, ByteBuf data) {
		to.writeShort(data.readableBytes());
		to.writeBytes(data);
	}

	public static <T> void writeShortTArray(ByteBuf to, T[] array, BiConsumer<ByteBuf, T> elementWriter) {
		to.writeShort(array.length);
		for (T element : array) {
			elementWriter.accept(to, element);
		}
	}


	public static void writeVarIntByteArray(ByteBuf to, ByteBuf data) {
		VarNumberCodec.writeVarInt(to, data.readableBytes());
		to.writeBytes(data);
	}

	public static void writeVarIntByteArray(ByteBuf to, byte[] data) {
		VarNumberCodec.writeVarInt(to, data.length);
		to.writeBytes(data);
	}

	public static <T> void writeVarIntTArray(ByteBuf to, T[] array, BiConsumer<ByteBuf, T> elementWriter) {
		VarNumberCodec.writeVarInt(to, array.length);
		for (T element : array) {
			elementWriter.accept(to, element);
		}
	}

	public static <T, A extends List<T> & RandomAccess> void writeVarIntTArray(ByteBuf to, A array, BiConsumer<ByteBuf, T> elementWriter) {
		int size = array.size();
		VarNumberCodec.writeVarInt(to, size);
		for (int i = 0; i < size; i++) {
			elementWriter.accept(to, array.get(i));
		}
	}

	public static void writeVarIntVarIntUTF8StringArray(ByteBuf to, String[] array) {
		VarNumberCodec.writeVarInt(to, array.length);
		for (String str : array) {
			StringCodec.writeVarIntUTF8String(to, str);
		}
	}

	public static void writeVarIntVarIntArray(ByteBuf to, int[] array) {
		VarNumberCodec.writeVarInt(to, array.length);
		for (int element : array) {
			VarNumberCodec.writeVarInt(to, element);
		}
	}

	public static void writeVarIntVarLongArray(ByteBuf to, long[] array) {
		VarNumberCodec.writeVarInt(to, array.length);
		for (long element : array) {
			VarNumberCodec.writeVarLong(to, element);
		}
	}

	public static void writeVarIntLongArray(ByteBuf to, long[] array) {
		VarNumberCodec.writeVarInt(to, array.length);
		for (long element : array) {
			to.writeLong(element);
		}
	}


	public static <T, A extends List<T> & RandomAccess> void writeTArray(ByteBuf to, A array, ObjIntConsumer<ByteBuf> lengthWriter, BiConsumer<ByteBuf, T> elementWriter) {
		int size = array.size();
		lengthWriter.accept(to, size);
		for (int i = 0; i < size; i++) {
			elementWriter.accept(to, array.get(i));
		}
	}

}
