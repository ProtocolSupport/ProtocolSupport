package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.ChunkCoord;

public class BlockChangeMulti extends MiddleBlockChangeMulti {

	public BlockChangeMulti(MiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntMappingTable blockDataRemappingTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		int chunkAbsY = getChunkSectionY(chunkCoordWithSection) << 4;

		ClientBoundPacketData blockchangemulti = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_MULTI);
		PositionSerializer.writeIntChunkCoord(blockchangemulti, new ChunkCoord(getChunkX(chunkCoordWithSection), getChunkZ(chunkCoordWithSection)));
		VarNumberSerializer.writeVarInt(blockchangemulti, records.length);
		for (long record : records) {
			blockchangemulti.writeShort((getRecordRelX(record) << 12) | (getRecordRelZ(record) << 8) | chunkAbsY | getRecordRelY(record));
			VarNumberSerializer.writeVarInt(blockchangemulti, BlockRemappingHelper.remapFlatteningBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, getRecordBlockData(record)));
		}
		codec.writeClientbound(blockchangemulti);
	}

}
