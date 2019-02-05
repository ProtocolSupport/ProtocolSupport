package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.ChunkCoord;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.Utils;

public abstract class MiddleBlockChangeMulti extends ClientBoundMiddlePacket {

	public MiddleBlockChangeMulti(ConnectionImpl connection) {
		super(connection);
	}

	protected ChunkCoord chunk;
	protected Record[] records;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		chunk = PositionSerializer.readChunkCoord(serverdata);
		records = ArraySerializer.readVarIntTArray(
			serverdata,
			Record.class,
			from -> new Record(serverdata.readUnsignedShort(), VarNumberSerializer.readVarInt(from))
		);
	}

	public static class Record {
		public final int coord;
		public final int id;
		public Record(int coord, int id) {
			this.coord = coord;
			this.id = id;
		}
		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
	}

	public static Position getGlobalPosition(ChunkCoord chunk, int coord) {
		return new Position(
			(chunk.getX() << 4) + getCoordX(coord),
			getCoordY(coord),
			(chunk.getZ() << 4) + getCoordZ(coord)
		);
	}

	public static int getCoordX(int coord) {
		return coord >> 12;
	}

	public static int getCoordY(int coord) {
		return coord & 0xFF;
	}

	public static int getCoordZ(int coord) {
		return (coord >> 8) & 0xF;
	}

}
