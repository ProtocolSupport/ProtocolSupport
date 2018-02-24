package protocolsupport.protocol.serializer;

import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.function.BiConsumer;
import java.util.function.Function;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;

public class ArraySerializer {

	public static byte[] readByteArray(ByteBuf from, ProtocolVersion version) {
		return readByteArray(from, version, from.readableBytes());
	}

	public static byte[] readByteArray(ByteBuf from, ProtocolVersion version, int limit) {
		int length = -1;
		if (isUsingShortLength(version)) {
			length = from.readShort();
		} else if (isUsingVarIntLength(version)) {
			length = VarNumberSerializer.readVarInt(from);
		} else {
			throw new IllegalArgumentException(MessageFormat.format("Dont know how to read byte array of version {0}", version));
		}
		MiscSerializer.checkLimit(length, limit);
		return MiscSerializer.readBytes(from, length);
	}

	public static void writeByteArray(ByteBuf to, ProtocolVersion version, ByteBuf data) {
		if (isUsingShortLength(version)) {
			to.writeShort(data.readableBytes());
		} else if (isUsingVarIntLength(version)) {
			VarNumberSerializer.writeVarInt(to, data.readableBytes());
		} else {
			throw new IllegalArgumentException(MessageFormat.format("Dont know how to write byte array of version {0}", version));
		}
		to.writeBytes(data);
	}

	public static void writeByteArray(ByteBuf to, ProtocolVersion version, byte[] data) {
		writeByteArray(to, version, Unpooled.wrappedBuffer(data));
	}

	private static boolean isUsingShortLength(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_7_10);
	}

	private static boolean isUsingVarIntLength(ProtocolVersion version) {
		return
			((version.getProtocolType() == ProtocolType.PC) && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_8)) ||
			(version.getProtocolType() == ProtocolType.PE);
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] readVarIntTArray(ByteBuf from, Class<T> tclass, Function<ByteBuf, T> elementReader) {
		T[] array = (T[]) Array.newInstance(tclass, VarNumberSerializer.readVarInt(from));
		for (int i = 0; i < array.length; i++) {
			array[i] = elementReader.apply(from);
		}
		return array;
	}

	public static String[] readVarIntStringArray(ByteBuf from, ProtocolVersion version, int strmaxlength) {
		return readVarIntTArray(from, String.class, buf -> StringSerializer.readString(buf, version, strmaxlength));
	}

	public static String[] readVarIntStringArray(ByteBuf from, ProtocolVersion version) {
		return readVarIntTArray(from, String.class, buf -> StringSerializer.readString(buf, version));
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] readShortTArray(ByteBuf from, Class<T> tclass, Function<ByteBuf, T> elementReader) {
		T[] array = (T[]) Array.newInstance(tclass, from.readShort());
		for (int i = 0; i < array.length; i++) {
			array[i] = elementReader.apply(from);
		}
		return array;
	}

	public static int[] readVarIntVarIntArray(ByteBuf from) {
		int[] array = new int[VarNumberSerializer.readVarInt(from)];
		for (int i = 0; i < array.length; i++) {
			array[i] = VarNumberSerializer.readVarInt(from);
		}
		return array;
	}

	public static <T> void writeVarIntTArray(ByteBuf to, T[] array, BiConsumer<ByteBuf, T> elementWriter) {
		VarNumberSerializer.writeVarInt(to, array.length);
		for (T element : array) {
			elementWriter.accept(to, element);
		}
	}

	public static void writeVarIntStringArray(ByteBuf to, ProtocolVersion version, String[] array) {
		VarNumberSerializer.writeVarInt(to, array.length);
		for (String str : array) {
			StringSerializer.writeString(to, version, str);
		}
	}

	public static <T> void writeShortTArray(ByteBuf to, T[] array, BiConsumer<ByteBuf, T> elementWriter) {
		to.writeShort(array.length);
		for (T element : array) {
			elementWriter.accept(to, element);
		}
	}

	public static void writeVarIntVarIntArray(ByteBuf to, int[] array) {
		VarNumberSerializer.writeVarInt(to, array.length);
		for (int element : array) {
			VarNumberSerializer.writeVarInt(to, element);
		}
	}

}
