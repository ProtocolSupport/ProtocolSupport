package protocolsupport.protocol.typeremapper.entity.metadata.types.living.fish;

import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class PufferFishEntityMetadataRemapper extends FishEntityMetadataRemapper {

	public PufferFishEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.PufferFish.PUFF_STATE, 15), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.PufferFish.PUFF_STATE, 13), ProtocolVersionsHelper.ALL_1_13);
	}

}
