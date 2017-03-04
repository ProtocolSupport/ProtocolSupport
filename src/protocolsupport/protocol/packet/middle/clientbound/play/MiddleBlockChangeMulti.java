package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleBlockChangeMulti extends ClientBoundMiddlePacket {

	protected int chunkX;
	protected int chunkZ;
	protected Record[] records;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		chunkX = serverdata.readInt();
		chunkZ = serverdata.readInt();
		records = new Record[VarNumberSerializer.readVarInt(serverdata)];
		for (int i = 0; i < records.length; i++) {
			Record record = new Record();
			record.coord = serverdata.readUnsignedShort();
			record.id = VarNumberSerializer.readVarInt(serverdata);
			records[i] = record;
		}
	}

	protected static class Record {
		public int coord;
		public int id;
	}

}
