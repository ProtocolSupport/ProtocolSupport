package protocolsupport.protocol.codec;

import java.util.UUID;

import io.netty.buffer.ByteBuf;

public class UUIDCodec {

	private UUIDCodec() {
	}

	public static UUID readUUID2L(ByteBuf from) {
		return new UUID(from.readLong(), from.readLong());
	}

	public static UUID readUUID4I(ByteBuf from) {
		return new UUID(((from.readUnsignedInt()) << 32) | from.readUnsignedInt(), (from.readUnsignedInt() << 32) | from.readUnsignedInt());
	}

	public static void writeUUID2L(ByteBuf to, UUID uuid) {
		to.writeLong(uuid.getMostSignificantBits());
		to.writeLong(uuid.getLeastSignificantBits());
	}

	public static void writeUUID4I(ByteBuf to, UUID uuid) {
		long most = uuid.getMostSignificantBits();
		long least = uuid.getLeastSignificantBits();
		to.writeInt((int) (most >>> 32));
		to.writeInt((int) most);
		to.writeInt((int) (least >>> 32));
		to.writeInt((int) least);
	}

}
