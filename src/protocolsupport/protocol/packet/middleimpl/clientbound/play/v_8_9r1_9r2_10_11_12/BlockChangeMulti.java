package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockChangeMulti extends MiddleBlockChangeMulti {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ArrayBasedIdRemappingTable remapper = IdRemapper.BLOCK.getTable(version);
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID, version);
		serializer.writeInt(chunkX);
		serializer.writeInt(chunkZ);
		ArraySerializer.writeVarIntTArray(serializer, records, (to, record) -> {
			to.writeShort(record.coord);
			VarNumberSerializer.writeVarInt(to, remapper.getRemap(record.id));
		});
		return RecyclableSingletonList.create(serializer);
	}

}
