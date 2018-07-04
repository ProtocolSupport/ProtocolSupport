package protocolsupport.protocol.serializer;

import java.text.MessageFormat;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.EnumConstantLookups;

public class MiscSerializer {

	public static <T extends Enum<T>> T readVarIntEnum(ByteBuf from, EnumConstantLookups.EnumConstantLookup<T> lookup) {
		return lookup.getByOrdinal(VarNumberSerializer.readVarInt(from));
	}

	public static <T extends Enum<T>> T readByteEnum(ByteBuf from, EnumConstantLookups.EnumConstantLookup<T> lookup) {
		return lookup.getByOrdinal(from.readByte());
	}

	public static void writeVarIntEnum(ByteBuf to, Enum<?> e) {
		VarNumberSerializer.writeVarInt(to, e.ordinal());
	}

	public static void writeByteEnum(ByteBuf to, Enum<?> e) {
		to.writeByte(e.ordinal());
	}

	public static UUID readUUID(ByteBuf from) {
		return new UUID(from.readLong(), from.readLong());
	}

	public static void writeUUID(ByteBuf to, ProtocolVersion version, UUID uuid) {
		if (isUsingLittleEndian(version)) {
			to.writeLongLE(uuid.getMostSignificantBits());
			to.writeLongLE(uuid.getLeastSignificantBits());
		} else {
			to.writeLong(uuid.getMostSignificantBits());
			to.writeLong(uuid.getLeastSignificantBits());
		}
	}

	private static boolean isUsingLittleEndian(ProtocolVersion version) {
		return version.getProtocolType() == ProtocolType.PE;
	}

	public static byte[] readAllBytes(ByteBuf buf) {
		return MiscSerializer.readBytes(buf, buf.readableBytes());
	}

	public static byte[] readBytes(ByteBuf buf, int length) {
		byte[] result = new byte[length];
		buf.readBytes(result);
		return result;
	}

	protected static void checkLimit(int length, int limit) {
		if (length > limit) {
			throw new DecoderException(MessageFormat.format("Size {0} is bigger than allowed {1}", length, limit));
		}
	}

}
