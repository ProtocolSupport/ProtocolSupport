package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheBlockChangeMulti;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.ChunkCoord;

public class BlockChangeMulti extends AbstractChunkCacheBlockChangeMulti {

	public BlockChangeMulti(MiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		int chunkAbsY = chunkSection << 4;

		ClientBoundPacketData blockchangemulti = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BLOCK_CHANGE_MULTI);
		PositionCodec.writeIntChunkCoord(blockchangemulti, new ChunkCoord(chunkX, chunkZ));
		blockchangemulti.writeShort(records.length);
		blockchangemulti.writeInt(records.length * 4);
		for (BlockChangeRecord record : records) {
			blockchangemulti.writeShort((record.getRelX() << 12) | (record.getRelZ() << 8) | chunkAbsY | record.getRelY());
			blockchangemulti.writeShort(BlockRemappingHelper.remapPreFlatteningBlockDataNormal(blockLegacyDataTable, record.getBlockData()));
		}
		codec.writeClientbound(blockchangemulti);
	}

}
