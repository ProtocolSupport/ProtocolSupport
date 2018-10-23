package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class PigEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public PigEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Pig.HAS_SADLLE, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Pig.HAS_SADLLE, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Pig.HAS_SADLLE, 16), ProtocolVersionsHelper.BEFORE_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Pig.BOOST_TIME, 14), ProtocolVersionsHelper.RANGE__1_11_1__1_13_2);
	}

}
