package protocolsupport.protocol.serializer;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.utils.types.Position;

public class PositionSerializer {

	public static Position readPosition(ByteBuf from) {
		return Position.fromLong(from.readLong());
	}

	public static Position readLegacyPositionB(ByteBuf from) {
		return new Position(from.readInt(), from.readUnsignedByte(), from.readInt());
	}

	public static Position readLegacyPositionS(ByteBuf from) {
		return new Position(from.readInt(), from.readShort(), from.readInt());
	}

	public static Position readLegacyPositionI(ByteBuf from) {
		return new Position(from.readInt(), from.readInt(), from.readInt());
	}

	public static void writePosition(ByteBuf to, Position position) {
		to.writeLong(position.asLong());
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

}
