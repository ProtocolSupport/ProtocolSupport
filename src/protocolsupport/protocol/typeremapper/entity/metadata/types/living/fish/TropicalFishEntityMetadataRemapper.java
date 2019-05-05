package protocolsupport.protocol.typeremapper.entity.metadata.types.living.fish;

import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class TropicalFishEntityMetadataRemapper extends FishEntityMetadataRemapper {

	public TropicalFishEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.TropicalFish.VARIANT, 15), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.TropicalFish.VARIANT, 13), ProtocolVersionsHelper.ALL_1_13);
	}

}
