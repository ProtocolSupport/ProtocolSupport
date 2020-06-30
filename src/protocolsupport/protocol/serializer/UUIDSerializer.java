package protocolsupport.protocol.serializer;

import java.util.UUID;

import io.netty.buffer.ByteBuf;

public class UUIDSerializer {

	public static UUID readUUID2L(ByteBuf from) {
		return new UUID(from.readLong(), from.readLong());
	}

	public static UUID readUUID4I(ByteBuf from) {
		return new UUID((from.readInt() << 32) | from.readInt(), (from.readInt() << 32) | from.readInt());
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
