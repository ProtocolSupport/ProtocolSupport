package protocolsupport.protocol.typeremapper.entity.metadata.types.living.fish;

import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class TropicalFishEntityMetadataRemapper extends FishEntityMetadataRemapper {

	public TropicalFishEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.TropicalFish.VARIANT, 13), ProtocolVersionsHelper.UP_1_13);
	}

}
