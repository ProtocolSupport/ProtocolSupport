package protocolsupport.protocol.typeremapper.block;

import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataEntry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.BlockBlockDataLookup;

public class BlockRemappingHelper {

	public static int remapPreFlatteningBlockId(ArrayBasedIdRemappingTable blockDataRemappingTable, int blockId) {
		return PreFlatteningBlockIdData.getIdFromCombinedId(remapPreFlatteningBlockDataNormal(blockDataRemappingTable, BlockBlockDataLookup.getBlockDataId(blockId)));
	}

	public static int remapPreFlatteningBlockDataNormal(ArrayBasedIdRemappingTable blockDataRemappingTable, int blockdata) {
		return PreFlatteningBlockIdData.getCombinedId(blockDataRemappingTable.getRemap(blockdata));
	}

	public static int remapPreFlatteningBlockDataM12(ArrayBasedIdRemappingTable blockDataRemappingTable, int blockdata) {
		return PreFlatteningBlockIdData.convertCombinedIdToM12(remapPreFlatteningBlockDataNormal(blockDataRemappingTable, blockdata));
	}

	public static int remapPreFlatteningBlockDataM16(ArrayBasedIdRemappingTable blockDataRemappingTable, int blockdata) {
		return PreFlatteningBlockIdData.convertCombinedIdToM16(remapPreFlatteningBlockDataNormal(blockDataRemappingTable, blockdata));
	}

	public static int remapFlatteningBlockId(ArrayBasedIdRemappingTable blockDataRemappingTable, FlatteningBlockDataTable flatteningBlockDataTable, int blockId) {
		int blockdata = blockDataRemappingTable.getRemap(BlockBlockDataLookup.getBlockDataId(blockId));
		FlatteningBlockDataEntry entry = flatteningBlockDataTable.getRemap(blockdata);
		return entry != null ? entry.getBlockId() : BlockBlockDataLookup.getBlockId(blockdata);
	}

	public static int remapFlatteningBlockDataId(ArrayBasedIdRemappingTable blockDataRemappingTable, FlatteningBlockDataTable flatteningBlockDataTable, int blockdata) {
		return flatteningBlockDataTable.getBlockDataRemap(blockDataRemappingTable.getRemap(blockdata));
	}

}
