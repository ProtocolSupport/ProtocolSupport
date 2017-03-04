package protocolsupport.protocol.serializer;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;

public class StringSerializer {

	public static String readString(ByteBuf from, ProtocolVersion version) {
		return readString(from, version, Short.MAX_VALUE);
	}

	public static String readString(ByteBuf from, ProtocolVersion version, int limit) {
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_4)) {
			int length = from.readUnsignedShort() * 2;
			MiscSerializer.checkLimit(length, limit * 4);
			return new String(MiscSerializer.readAllBytes(from.readSlice(length)), StandardCharsets.UTF_16BE);
		} else {
			int length = VarNumberSerializer.readVarInt(from);
			MiscSerializer.checkLimit(length, limit * 4);
			return new String(MiscSerializer.readAllBytes(from.readSlice(length)), StandardCharsets.UTF_8);
		}
	}

	public static void writeString(ByteBuf to, ProtocolVersion version, String string) {
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_4) && (version != ProtocolVersion.MINECRAFT_PE)) {
			to.writeShort(string.length());
			to.writeBytes(string.getBytes(StandardCharsets.UTF_16BE));
		} else {
			byte[] data = string.getBytes(StandardCharsets.UTF_8);
			VarNumberSerializer.writeVarInt(to, data.length);
			to.writeBytes(data);
		}
	}

}
