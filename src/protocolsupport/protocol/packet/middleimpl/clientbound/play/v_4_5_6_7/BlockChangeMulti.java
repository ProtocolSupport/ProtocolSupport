package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractBlockChangeMulti;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public class BlockChangeMulti extends AbstractBlockChangeMulti {

	public BlockChangeMulti(ConnectionImpl connection) {
		super(connection);
	}

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	@Override
	public void writeToClient() {
		ClientBoundPacketData blockchangemulti = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_MULTI);
		PositionSerializer.writeIntChunkCoord(blockchangemulti, chunkCoord);
		blockchangemulti.writeShort(records.length);
		blockchangemulti.writeInt(records.length * 4);
		for (Record record : records) {
			blockchangemulti.writeShort(record.coord);
			blockchangemulti.writeShort(BlockRemappingHelper.remapPreFlatteningBlockDataNormal(blockDataRemappingTable, record.id));
		}
		codec.write(blockchangemulti);
	}

}
