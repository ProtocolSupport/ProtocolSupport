package protocolsupport.protocol.serializer;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.utils.types.ChunkCoord;
import protocolsupport.protocol.utils.types.Position;

public class PositionSerializer {

	public static void skipPosition(ByteBuf from) {
		from.skipBytes(Long.BYTES);
	}

	public static Position readPosition(ByteBuf from) {
		long l = from.readLong();
		return new Position((int) (l >> 38), (int) ((l >> 26) & 0xFFFL), (int) ((l << 38) >> 38));
	}

	public static void readPositionTo(ByteBuf from, Position to) {
		long l = from.readLong();
		to.setX((int) (l >> 38));
		to.setY((int) ((l >> 26) & 0xFFFL));
		to.setZ((int) ((l << 38) >> 38));
	}

	public static void readLegacyPositionBTo(ByteBuf from, Position to) {
		to.setX(from.readInt());
		to.setY(from.readUnsignedByte());
		to.setZ(from.readInt());
	}

	public static void readLegacyPositionSTo(ByteBuf from, Position to) {
		to.setX(from.readInt());
		to.setY(from.readShort());
		to.setZ(from.readInt());
	}

	public static void readLegacyPositionITo(ByteBuf from, Position to) {
		to.setX(from.readInt());
		to.setY(from.readInt());
		to.setZ(from.readInt());
	}

	public static Position readLegacyPositionI(ByteBuf from) {
		return new Position(from.readInt(), from.readInt(), from.readInt());
	}

	public static void writePosition(ByteBuf to, Position position) {
		to.writeLong(((position.getX() & 0x3FFFFFFL) << 38) | ((position.getY() & 0xFFFL) << 26) | (position.getZ() & 0x3FFFFFFL));
	}

	public static void writeLegacyPositionB(ByteBuf to, Position position) {
		to.writeInt(position.getX());
		to.writeByte(position.getY());
		to.writeInt(position.getZ());
	}

	public static void writeLegacyPositionS(ByteBuf to, Position position) {
		to.writeInt(position.getX());
		to.writeShort(position.getY());
		to.writeInt(position.getZ());
	}

	public static void writeLegacyPositionI(ByteBuf to, Position position) {
		to.writeInt(position.getX());
		to.writeInt(position.getY());
		to.writeInt(position.getZ());
	}



	public static ChunkCoord readChunkCoord(ByteBuf from) {
		return new ChunkCoord(from.readInt(), from.readInt());
	}

	public static void writeChunkCoord(ByteBuf to, ChunkCoord chunk) {
		to.writeInt(chunk.getX());
		to.writeInt(chunk.getZ());
	}

}
