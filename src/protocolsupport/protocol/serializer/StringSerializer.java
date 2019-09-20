package protocolsupport.protocol.serializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;

public class StringSerializer {

	public static String readVarIntUTF8String(ByteBuf from) {
		return readString(from, VarNumberSerializer.readVarInt(from), StandardCharsets.UTF_8);
	}

	public static String readVarIntUTF8String(ByteBuf from, int limit) {
		int length = VarNumberSerializer.readVarInt(from);
		MiscSerializer.checkLimit(length, limit);
		return readString(from, length, StandardCharsets.UTF_8);
	}

	public static String readShortUTF16BEString(ByteBuf from, int limit) {
		int length = from.readUnsignedShort() * 2;
		MiscSerializer.checkLimit(length, limit * 2);
		return readString(from, length, StandardCharsets.UTF_16BE);
	}

	protected static String readString(ByteBuf from, int length, Charset charset) {
		String string = from.toString(from.readerIndex(), length, charset);
		from.skipBytes(length);
		return string;
	}

	public static void writeVarIntUTF8String(ByteBuf to, String string) {
		byte[] data = string.getBytes(StandardCharsets.UTF_8);
		VarNumberSerializer.writeVarInt(to, data.length);
		to.writeBytes(data);
	}

	public static void writeShortUTF16BEString(ByteBuf to, String string) {
		to.writeShort(string.length());
		to.writeBytes(string.getBytes(StandardCharsets.UTF_16BE));
	}

	public static void writeString(ByteBuf to, ProtocolVersion version, String string) {
		if (isUsingUTF16(version)) {
			writeShortUTF16BEString(to, string);
		} else if (isUsingUTF8(version)) {
			writeVarIntUTF8String(to, string);
		} else {
			throw new IllegalArgumentException(MessageFormat.format("Dont know how to write string of version {0}", version));
		}
	}

	private static boolean isUsingUTF16(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_4);
	}

	private static boolean isUsingUTF8(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_7_5);
	}

}
