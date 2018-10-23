package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class SheepEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public SheepEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Sheep.FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Sheep.FLAGS, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Sheep.FLAGS, 16), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
