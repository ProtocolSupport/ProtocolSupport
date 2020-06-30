package protocolsupport.protocol.typeremapper.block;

import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataEntry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.IdRemappingTable;
import protocolsupport.protocol.utils.BlockBlockDataLookup;

public class BlockRemappingHelper {

	public static int remapPreFlatteningBlockId(IdRemappingTable blockDataRemappingTable, int blockId) {
		return PreFlatteningBlockIdData.getIdFromCombinedId(remapPreFlatteningBlockDataNormal(blockDataRemappingTable, BlockBlockDataLookup.getBlockDataId(blockId)));
	}

	public static int remapPreFlatteningBlockDataNormal(IdRemappingTable blockDataRemappingTable, int blockdata) {
		return PreFlatteningBlockIdData.getCombinedId(blockDataRemappingTable.getRemap(blockdata));
	}

	public static int remapPreFlatteningBlockDataM12(IdRemappingTable blockDataRemappingTable, int blockdata) {
		return PreFlatteningBlockIdData.convertCombinedIdToM12(remapPreFlatteningBlockDataNormal(blockDataRemappingTable, blockdata));
	}

	public static int remapPreFlatteningBlockDataM16(IdRemappingTable blockDataRemappingTable, int blockdata) {
		return PreFlatteningBlockIdData.convertCombinedIdToM16(remapPreFlatteningBlockDataNormal(blockDataRemappingTable, blockdata));
	}

	public static int remapFlatteningBlockId(IdRemappingTable blockDataRemappingTable, FlatteningBlockDataTable flatteningBlockDataTable, int blockId) {
		int blockdata = blockDataRemappingTable.getRemap(BlockBlockDataLookup.getBlockDataId(blockId));
		FlatteningBlockDataEntry entry = flatteningBlockDataTable.getRemap(blockdata);
		return entry != null ? entry.getBlockId() : BlockBlockDataLookup.getBlockId(blockdata);
	}

	public static int remapFlatteningBlockDataId(IdRemappingTable blockDataRemappingTable, FlatteningBlockDataTable flatteningBlockDataTable, int blockdata) {
		return flatteningBlockDataTable.getBlockDataRemap(blockDataRemappingTable.getRemap(blockdata));
	}

}
