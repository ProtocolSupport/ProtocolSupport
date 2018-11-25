package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.ChunkCoord;
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
			from -> {
				return new Record(PositionSerializer.readLocalCoord(serverdata), VarNumberSerializer.readVarInt(from));
			}
		);
	}

	public static class Record {
		public final int localCoord;
		public final int id;
		public Record(int localCoord, int id) {
			this.localCoord = localCoord;
			this.id = id;
		}
		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
	}

}
