package protocolsupport.protocol.serializer;

import java.text.MessageFormat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;

public class ByteArraySerializer {

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
			throw new IllegalArgumentException(MessageFormat.format("Don't know how to read byte array of version {0}", version));
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
			throw new IllegalArgumentException(MessageFormat.format("Don't know how to write byte array of version {0}", version));
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
			((version.getProtocolType() == ProtocolType.PE) && (version == ProtocolVersion.MINECRAFT_PE));
	}

}
