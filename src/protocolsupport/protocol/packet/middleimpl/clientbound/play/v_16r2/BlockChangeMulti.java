package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_16r2;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2.AbstractLimitedHeightBlockChangeMulti;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;

public class BlockChangeMulti extends AbstractLimitedHeightBlockChangeMulti {

	public BlockChangeMulti(MiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		ClientBoundPacketData blockchangemulti = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BLOCK_CHANGE_MULTI);
		blockchangemulti.writeLong((((long) chunkX) << 42) | (((long) chunkZ) << 20) | chunkSection);
		blockchangemulti.writeBoolean(skipLight);
		ArrayCodec.writeVarIntTArray(
			blockchangemulti, records,
			(recordTo, record) -> VarNumberCodec.writeVarLong(
				recordTo,
				(record.getRelX() << 8) |
				(record.getRelZ() << 4) |
				(record.getRelY()) |
				(BlockRemappingHelper.remapFlatteningBlockDataId(blockLegacyDataTable, flatteningBlockDataTable, record.getBlockData()) << 12)
			)
		);
		codec.writeClientbound(blockchangemulti);
	}

}
