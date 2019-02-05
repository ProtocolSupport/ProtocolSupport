package protocolsupport.protocol.typeremapper.block;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.BlockBlockDataLookup;

public class BlockRemappingHelper {

	public static int remapBlockId(ArrayBasedIdRemappingTable blockIdRemapper, int blockId) {
		return PreFlatteningBlockIdData.getIdFromCombinedId(remapBlockDataNormal(blockIdRemapper, BlockBlockDataLookup.getBlockDataId(blockId)));
	}

	public static int remapBlockDataNormal(ArrayBasedIdRemappingTable blockIdRemapper, int blockdata) {
		return PreFlatteningBlockIdData.getCombinedId(blockIdRemapper.getRemap(blockdata));
	}

	protected static int remapToCombinedIdNormal(ProtocolVersion version, int blockdata) {
		return PreFlatteningBlockIdData.getCombinedId(LegacyBlockData.REGISTRY.getTable(version).getRemap(blockdata));
	}

	public static int remapToCombinedIdM12(ProtocolVersion version, int blockdata) {
		return PreFlatteningBlockIdData.convertCombinedIdToM12(remapToCombinedIdNormal(version, blockdata));
	}

	public static int remapToCombinedIdM16(ProtocolVersion version, int blockdata) {
		return PreFlatteningBlockIdData.convertCombinedIdToM16(remapToCombinedIdNormal(version, blockdata));
	}

}
