package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleBlockChangeMulti<T> extends ClientBoundMiddlePacket<T> {

	protected int chunkX;
	protected int chunkZ;
	protected Record[] records;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
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
