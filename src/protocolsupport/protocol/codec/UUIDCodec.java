package protocolsupport.protocol.codec;

import java.util.UUID;

import io.netty.buffer.ByteBuf;

public class UUIDCodec {

	private UUIDCodec() {
	}

	public static UUID readUUID(ByteBuf from) {
		return new UUID(from.readLong(), from.readLong());
	}

	public static void writeUUID(ByteBuf to, UUID uuid) {
		to.writeLong(uuid.getMostSignificantBits());
		to.writeLong(uuid.getLeastSignificantBits());
	}
}
