package protocolsupport.protocol.typeremapper.block;

import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataEntry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.BlockBlockDataLookup;

public class BlockRemappingHelper {

	public static int remapBlockId(ArrayBasedIdRemappingTable blockDataRemappingTable, int blockId) {
		return PreFlatteningBlockIdData.getIdFromCombinedId(remapBlockDataNormal(blockDataRemappingTable, BlockBlockDataLookup.getBlockDataId(blockId)));
	}

	public static int remapBlockDataNormal(ArrayBasedIdRemappingTable blockDataRemappingTable, int blockdata) {
		return PreFlatteningBlockIdData.getCombinedId(blockDataRemappingTable.getRemap(blockdata));
	}

	public static int remapBlockDataM12(ArrayBasedIdRemappingTable blockDataRemappingTable, int blockdata) {
		return PreFlatteningBlockIdData.convertCombinedIdToM12(remapBlockDataNormal(blockDataRemappingTable, blockdata));
	}

	public static int remapBlockDataM16(ArrayBasedIdRemappingTable blockDataRemappingTable, int blockdata) {
		return PreFlatteningBlockIdData.convertCombinedIdToM16(remapBlockDataNormal(blockDataRemappingTable, blockdata));
	}

	public static int remapFBlockId(ArrayBasedIdRemappingTable blockDataRemappingTable, FlatteningBlockDataTable flatteningBlockDataTable, int blockId) {
		int blockdata = blockDataRemappingTable.getRemap(BlockBlockDataLookup.getBlockDataId(blockId));
		FlatteningBlockDataEntry entry = flatteningBlockDataTable.getRemap(blockdata);
		return entry != null ? entry.getBlockId() : BlockBlockDataLookup.getBlockId(blockdata);
	}

	public static int remapFBlockDataId(ArrayBasedIdRemappingTable blockDataRemappingTable, FlatteningBlockDataTable flatteningBlockDataTable, int blockdata) {
		blockdata = blockDataRemappingTable.getRemap(blockdata);
		FlatteningBlockDataEntry entry = flatteningBlockDataTable.getRemap(blockdata);
		return entry != null ? entry.getBlockDataId() : blockdata;
	}

}
