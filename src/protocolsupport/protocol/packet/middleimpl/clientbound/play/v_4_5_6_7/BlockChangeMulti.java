package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheBlockChangeMulti;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.ChunkCoord;

public class BlockChangeMulti extends AbstractChunkCacheBlockChangeMulti {

	public BlockChangeMulti(ConnectionImpl connection) {
		super(connection);
	}

	protected final ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	@Override
	protected void writeToClient() {
		int chunkAbsY = getChunkSectionY(chunkCoordWithSection) << 4;

		ClientBoundPacketData blockchangemulti = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_MULTI);
		PositionSerializer.writeIntChunkCoord(blockchangemulti, new ChunkCoord(getChunkX(chunkCoordWithSection), getChunkZ(chunkCoordWithSection)));
		blockchangemulti.writeShort(records.length);
		blockchangemulti.writeInt(records.length * 4);
		for (long record : records) {
			blockchangemulti.writeShort((getRecordRelX(record) << 12) | (getRecordRelZ(record) << 8) | chunkAbsY | getRecordRelY(record));
			blockchangemulti.writeShort(BlockRemappingHelper.remapPreFlatteningBlockDataNormal(blockDataRemappingTable, getRecordBlockData(record)));
		}
		codec.write(blockchangemulti);
	}

}
