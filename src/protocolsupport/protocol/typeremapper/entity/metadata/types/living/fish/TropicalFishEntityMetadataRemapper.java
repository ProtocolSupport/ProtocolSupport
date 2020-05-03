package protocolsupport.protocol.typeremapper.entity.metadata.types.living.fish;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class TropicalFishEntityMetadataRemapper extends FishEntityMetadataRemapper {

	public static final TropicalFishEntityMetadataRemapper INSTANCE = new TropicalFishEntityMetadataRemapper();

	protected TropicalFishEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.TropicalFish.VARIANT, 16), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.TropicalFish.VARIANT, 15), ProtocolVersionsHelper.ALL_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.TropicalFish.VARIANT, 13), ProtocolVersionsHelper.ALL_1_13);
	}

}
