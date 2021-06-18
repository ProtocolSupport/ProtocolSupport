package protocolsupport.protocol.codec;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.Position;

public class PositionCodec {

	private PositionCodec() {
	}

	public static void skipPositionL(ByteBuf from) {
		from.skipBytes(Long.BYTES);
	}

	public static Position readPosition(ByteBuf from) {
		long l = from.readLong();
		return new Position(
			(int) (l >> 38), (int) (l & 0xFFF), (int) ((l << 26) >> 38)
		);
	}

	public static void readPosition(ByteBuf from, Position to) {
		long l = from.readLong();
		to.setX((int) (l >> 38));
		to.setY((int) (l & 0xFFF));
		to.setZ((int) ((l << 26) >> 38));
	}

	public static void readPositionLXYZ(ByteBuf from, Position to) {
		long l = from.readLong();
		to.setX((int) (l >> 38));
		to.setY((int) ((l >> 26) & 0xFFFL));
		to.setZ((int) ((l << 38) >> 38));
	}

	public static void readPositionIBI(ByteBuf from, Position to) {
		to.setX(from.readInt());
		to.setY(from.readUnsignedByte());
		to.setZ(from.readInt());
	}

	public static void readPositionISI(ByteBuf from, Position to) {
		to.setX(from.readInt());
		to.setY(from.readShort());
		to.setZ(from.readInt());
	}

	public static void readPositionIII(ByteBuf from, Position to) {
		to.setX(from.readInt());
		to.setY(from.readInt());
		to.setZ(from.readInt());
	}

	public static Position readPositionIII(ByteBuf from) {
		return new Position(from.readInt(), from.readInt(), from.readInt());
	}

	public static void writePosition(ByteBuf to, Position position) {
		to.writeLong(((position.getX() & 0x3FFFFFFL) << 38) | ((position.getZ() & 0x3FFFFFFL) << 12) | (position.getY() & 0xFFFL));
	}

	public static void writePositionLXYZ(ByteBuf to, Position position) {
		to.writeLong(((position.getX() & 0x3FFFFFFL) << 38) | ((position.getY() & 0xFFFL) << 26) | (position.getZ() & 0x3FFFFFFL));
	}

	public static void writePositionIBI(ByteBuf to, Position position) {
		to.writeInt(position.getX());
		to.writeByte(position.getY());
		to.writeInt(position.getZ());
	}

	public static void writePositionISI(ByteBuf to, Position position) {
		to.writeInt(position.getX());
		to.writeShort(position.getY());
		to.writeInt(position.getZ());
	}

	public static void writePositionIII(ByteBuf to, Position position) {
		to.writeInt(position.getX());
		to.writeInt(position.getY());
		to.writeInt(position.getZ());
	}



	public static ChunkCoord readIntChunkCoord(ByteBuf from) {
		return new ChunkCoord(from.readInt(), from.readInt());
	}

	public static ChunkCoord readVarIntChunkCoord(ByteBuf from) {
		return new ChunkCoord(VarNumberCodec.readVarInt(from), VarNumberCodec.readVarInt(from));
	}

	public static void writeIntChunkCoord(ByteBuf to, ChunkCoord chunk) {
		to.writeInt(chunk.getX());
		to.writeInt(chunk.getZ());
	}

	public static void writeVarIntChunkCoord(ByteBuf to, ChunkCoord chunk) {
		VarNumberCodec.writeVarInt(to, chunk.getX());
		VarNumberCodec.writeVarInt(to, chunk.getZ());
	}

}
