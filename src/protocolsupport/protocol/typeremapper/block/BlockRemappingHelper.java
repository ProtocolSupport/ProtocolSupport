package protocolsupport.protocol.typeremapper.block;

import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataEntry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IdMappingTable;
import protocolsupport.protocol.utils.BlockBlockDataLookup;

public class BlockRemappingHelper {

	public static int remapPreFlatteningBlockId(IdMappingTable blockDataRemappingTable, int blockId) {
		return PreFlatteningBlockIdData.getIdFromCombinedId(remapPreFlatteningBlockDataNormal(blockDataRemappingTable, BlockBlockDataLookup.getBlockDataId(blockId)));
	}

	public static int remapPreFlatteningBlockDataNormal(IdMappingTable blockDataRemappingTable, int blockdata) {
		return PreFlatteningBlockIdData.getCombinedId(blockDataRemappingTable.get(blockdata));
	}

	public static int remapPreFlatteningBlockDataM12(IdMappingTable blockDataRemappingTable, int blockdata) {
		return PreFlatteningBlockIdData.convertCombinedIdToM12(remapPreFlatteningBlockDataNormal(blockDataRemappingTable, blockdata));
	}

	public static int remapPreFlatteningBlockDataM16(IdMappingTable blockDataRemappingTable, int blockdata) {
		return PreFlatteningBlockIdData.convertCombinedIdToM16(remapPreFlatteningBlockDataNormal(blockDataRemappingTable, blockdata));
	}

	public static int remapFlatteningBlockId(IdMappingTable blockDataRemappingTable, FlatteningBlockDataTable flatteningBlockDataTable, int blockId) {
		int blockdata = blockDataRemappingTable.get(BlockBlockDataLookup.getBlockDataId(blockId));
		FlatteningBlockDataEntry entry = flatteningBlockDataTable.get(blockdata);
		return entry != null ? entry.getBlockId() : BlockBlockDataLookup.getBlockId(blockdata);
	}

	public static int remapFlatteningBlockDataId(IdMappingTable blockDataRemappingTable, FlatteningBlockDataTable flatteningBlockDataTable, int blockdata) {
		return flatteningBlockDataTable.getId(blockDataRemappingTable.get(blockdata));
	}

}
