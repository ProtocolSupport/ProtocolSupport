package protocolsupport.protocol.typeremapper.entity.metadata.types.living.fish;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class PufferFishEntityMetadataRemapper extends FishEntityMetadataRemapper {

	public static final PufferFishEntityMetadataRemapper INSTANCE = new PufferFishEntityMetadataRemapper();

	protected PufferFishEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.PufferFish.PUFF_STATE, 16), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.PufferFish.PUFF_STATE, 15), ProtocolVersionsHelper.ALL_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.PufferFish.PUFF_STATE, 13), ProtocolVersionsHelper.ALL_1_13);
	}

}
