package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleBlockChangeMulti<T> extends ClientBoundMiddlePacket<T> {

	protected int chunkX;
	protected int chunkZ;
	protected Record[] records;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		chunkX = serializer.readInt();
		chunkZ = serializer.readInt();
		records = new Record[serializer.readVarInt()];
		for (int i = 0; i < records.length; i++) {
			Record record = new Record();
			record.coord = serializer.readUnsignedShort();
			record.id = serializer.readVarInt();
			records[i] = record;
		}
	}

	protected static class Record {
		public int coord;
		public int id;
	}

}
