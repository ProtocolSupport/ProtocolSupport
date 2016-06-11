package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockChangeMulti extends MiddleBlockChangeMulti<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		ArrayBasedIdRemappingTable remapper = IdRemapper.BLOCK.getTable(version);
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID, version);
		serializer.writeInt(chunkX);
		serializer.writeInt(chunkZ);
		serializer.writeVarInt(records.length);
		for (Record record : records) {
			serializer.writeShort(record.coord);
			serializer.writeVarInt(remapper.getRemap(record.id));
		}
		return RecyclableSingletonList.create(serializer);
	}

}
