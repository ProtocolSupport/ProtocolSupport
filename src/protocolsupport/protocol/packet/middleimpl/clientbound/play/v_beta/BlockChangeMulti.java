package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockChangeMulti extends MiddleBlockChangeMulti {

	public BlockChangeMulti(ConnectionImpl connection) {
		super(connection);
	}

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_MULTI);
		PositionSerializer.writeIntChunkCoord(serializer, chunkCoord);
		int rLength = records.length;
		serializer.writeShort(rLength);
		byte[] types = new byte[rLength];
		byte[] metadata = new byte[rLength];
		for (int i = 0; i < rLength; i++) {
			Record record = records[i];
			serializer.writeShort(record.coord);
			int blockdata = BlockRemappingHelper.remapBlockDataNormal(blockDataRemappingTable, record.id);
			types[i] = (byte) PreFlatteningBlockIdData.getIdFromCombinedId(blockdata);
			metadata[i] = (byte) PreFlatteningBlockIdData.getDataFromCombinedId(blockdata);
		}
		serializer.writeBytes(types);
		serializer.writeBytes(metadata);
		return RecyclableSingletonList.create(serializer);
	}

}
