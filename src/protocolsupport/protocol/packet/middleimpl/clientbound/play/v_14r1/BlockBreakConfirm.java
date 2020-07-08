package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockBreakConfirm;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16.BlockChangeSingle;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;

public class BlockBreakConfirm extends MiddleBlockBreakConfirm {

	public BlockBreakConfirm(ConnectionImpl connection) {
		super(connection);
	}

	protected final ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockData.REGISTRY.getTable(version);

	@Override
	protected void writeToClient() {
		codec.write(BlockChangeSingle.create(position, BlockRemappingHelper.remapFlatteningBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, blockId)));
	}

}
