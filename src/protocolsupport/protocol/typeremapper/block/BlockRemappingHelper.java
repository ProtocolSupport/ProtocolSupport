package protocolsupport.protocol.typeremapper.block;

import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.BlockBlockDataLookup;

public class BlockRemappingHelper {

	public static int remapBlockId(ArrayBasedIdRemappingTable blockIdRemapper, int blockId) {
		return PreFlatteningBlockIdData.getIdFromCombinedId(remapBlockDataNormal(blockIdRemapper, BlockBlockDataLookup.getBlockDataId(blockId)));
	}

	public static int remapBlockDataNormal(ArrayBasedIdRemappingTable blockIdRemapper, int blockdata) {
		return PreFlatteningBlockIdData.getCombinedId(blockIdRemapper.getRemap(blockdata));
	}

	public static int remapBlockDataM12(ArrayBasedIdRemappingTable blockIdRemapper, int blockdata) {
		return PreFlatteningBlockIdData.convertCombinedIdToM12(remapBlockDataNormal(blockIdRemapper, blockdata));
	}

	public static int remapBlockDataM16(ArrayBasedIdRemappingTable blockIdRemapper, int blockdata) {
		return PreFlatteningBlockIdData.convertCombinedIdToM16(remapBlockDataNormal(blockIdRemapper, blockdata));
	}

}
