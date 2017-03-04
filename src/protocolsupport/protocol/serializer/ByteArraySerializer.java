package protocolsupport.protocol.serializer;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;

public class ByteArraySerializer {

	public static byte[] readByteArray(ByteBuf from, ProtocolVersion version) {
		return readByteArray(from, version, from.readableBytes());
	}

	public static byte[] readByteArray(ByteBuf from, ProtocolVersion version, int limit) {
		int length = -1;
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_7_10)) {
			length = from.readShort();
		} else {
			length = VarNumberSerializer.readVarInt(from);
		}
		MiscSerializer.checkLimit(length, limit);
		return MiscSerializer.readBytes(from, length);
	}

	public static void writeByteArray(ByteBuf to, ProtocolVersion version, ByteBuf data) {
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_7_10)) {
			to.writeShort(data.readableBytes());
		} else {
			VarNumberSerializer.writeVarInt(to, data.readableBytes());
		}
		to.writeBytes(data);
	}

	public static void writeByteArray(ByteBuf to, ProtocolVersion version, byte[] data) {
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_7_10)) {
			to.writeShort(data.length);
		} else {
			VarNumberSerializer.writeVarInt(to, data.length);
		}
		to.writeBytes(data);
	}

}
