package protocolsupport.protocol.typeremapper.block;

import protocolsupport.api.ProtocolVersion;

public class BlockIdRemappingHelper {

	public static int remapToCombinedIdNormal(ProtocolVersion version, int blockdata) {
		return PreFlatteningBlockIdData.getCombinedId(LegacyBlockData.REGISTRY.getTable(version).getRemap(blockdata));
	}

	public static int remapToCombinedIdM12(ProtocolVersion version, int blockdata) {
		return PreFlatteningBlockIdData.convertCombinedIdToM12(remapToCombinedIdNormal(version, blockdata));
	}

	public static int remapToCombinedIdM16(ProtocolVersion version, int blockdata) {
		return PreFlatteningBlockIdData.convertCombinedIdToM16(remapToCombinedIdNormal(version, blockdata));
	}

}
