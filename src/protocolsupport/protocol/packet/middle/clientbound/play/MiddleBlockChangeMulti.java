package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.Position;
import protocolsupport.utils.Utils;

public abstract class MiddleBlockChangeMulti extends ClientBoundMiddlePacket {

	public MiddleBlockChangeMulti(ConnectionImpl connection) {
		super(connection);
	}

	protected ChunkCoord chunkCoord;
	protected Record[] records;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		chunkCoord = PositionSerializer.readIntChunkCoord(serverdata);
		records = ArraySerializer.readVarIntTArray(
			serverdata,
			Record.class,
			from -> new Record(serverdata.readUnsignedShort(), VarNumberSerializer.readVarInt(from))
		);
	}


	public static class Record {
		public final int coord;
		public final int x;
		public final int y;
		public final int z;
		public final int id;
		public Record(int coord, int id) {
			this.coord = coord;
			this.x = coord >> 12;
			this.y = coord & 0xFF;
			this.z = (coord >> 8) & 0xF;
			this.id = id;
		}
		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
	}

	public static Position getGlobalPosition(ChunkCoord chunk, Record record) {
		return new Position(
			(chunk.getX() << 4) + record.x,
			record.y,
			(chunk.getZ() << 4) + record.z
		);
	}

}
