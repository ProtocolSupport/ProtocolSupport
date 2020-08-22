package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.ChunkCoord;

public class BlockChangeMulti extends MiddleBlockChangeMulti {

	public BlockChangeMulti(MiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockData.REGISTRY.getTable(version);

	@Override
	protected void writeToClient() {
		int chunkAbsY = getChunkSectionY(chunkCoordWithSection) << 4;

		ClientBoundPacketData blockchangemulti = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_MULTI);
		PositionSerializer.writeIntChunkCoord(blockchangemulti, new ChunkCoord(getChunkX(chunkCoordWithSection), getChunkZ(chunkCoordWithSection)));
		VarNumberSerializer.writeVarInt(blockchangemulti, records.length);
		for (long record : records) {
			blockchangemulti.writeShort((getRecordRelX(record) << 12) | (getRecordRelZ(record) << 8) | chunkAbsY | getRecordRelY(record));
			VarNumberSerializer.writeVarInt(blockchangemulti, BlockRemappingHelper.remapFlatteningBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, getRecordBlockData(record)));
		}
		codec.write(blockchangemulti);
	}

}
