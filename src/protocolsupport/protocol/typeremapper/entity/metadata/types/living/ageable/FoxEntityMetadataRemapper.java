package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class FoxEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public FoxEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Fox.VARIANT, 15), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Fox.FLAGS, 16), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Fox.UNKNOWN_1, 17), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Fox.UNKNOWN_2, 18), ProtocolVersionsHelper.UP_1_14);
	}

}
