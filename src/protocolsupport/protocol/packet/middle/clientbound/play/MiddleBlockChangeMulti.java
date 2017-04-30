package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleBlockChangeMulti extends ClientBoundMiddlePacket {

	protected int chunkX;
	protected int chunkZ;
	protected Record[] records;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		chunkX = serverdata.readInt();
		chunkZ = serverdata.readInt();
		records = ArraySerializer.readVarIntTArray(serverdata, Record.class, (from) -> new Record(from.readUnsignedShort(), VarNumberSerializer.readVarInt(from)));
	}

	public static class Record {
		public final int coord;
		public final int id;
		public Record(int coord, int id) {
			this.coord = coord;
			this.id = id;
		}
	}

}
