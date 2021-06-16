package protocolsupport.protocol.typeremapper.block;

import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataEntry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;
import protocolsupport.protocol.utils.BlockBlockDataLookup;

public class BlockRemappingHelper {

	private BlockRemappingHelper() {
	}

	public static int remapPreFlatteningBlockId(IntMappingTable blockDataRemappingTable, int blockId) {
		return PreFlatteningBlockIdData.getIdFromCombinedId(remapPreFlatteningBlockDataNormal(blockDataRemappingTable, BlockBlockDataLookup.getBlockDataId(blockId)));
	}

	public static int remapPreFlatteningBlockDataNormal(IntMappingTable blockDataRemappingTable, int blockdata) {
		return PreFlatteningBlockIdData.getCombinedId(blockDataRemappingTable.get(blockdata));
	}

	public static int remapPreFlatteningBlockDataM12(IntMappingTable blockDataRemappingTable, int blockdata) {
		return PreFlatteningBlockIdData.convertCombinedIdToM12(remapPreFlatteningBlockDataNormal(blockDataRemappingTable, blockdata));
	}

	public static int remapPreFlatteningBlockDataM16(IntMappingTable blockDataRemappingTable, int blockdata) {
		return PreFlatteningBlockIdData.convertCombinedIdToM16(remapPreFlatteningBlockDataNormal(blockDataRemappingTable, blockdata));
	}

	public static int remapFlatteningBlockId(IntMappingTable blockDataRemappingTable, FlatteningBlockDataTable flatteningBlockDataTable, int blockId) {
		int blockdata = blockDataRemappingTable.get(BlockBlockDataLookup.getBlockDataId(blockId));
		FlatteningBlockDataEntry entry = flatteningBlockDataTable.get(blockdata);
		return entry != null ? entry.getBlockId() : BlockBlockDataLookup.getBlockId(blockdata);
	}

	public static int remapFlatteningBlockDataId(IntMappingTable blockDataRemappingTable, FlatteningBlockDataTable flatteningBlockDataTable, int blockdata) {
		return flatteningBlockDataTable.getId(blockDataRemappingTable.get(blockdata));
	}

}
