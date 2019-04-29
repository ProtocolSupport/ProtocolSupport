package protocolsupport.protocol.typeremapper.entity.metadata.types.living.tameable;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.TameableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class CatEntityMetadataRemapper extends TameableEntityMetadataRemapper {

	public CatEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Cat.VARIANT, 17), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Cat.UNKNOWN_1, 18), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Cat.UNKNOWN_2, 19), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Cat.COLLAR_COLOR, 20), ProtocolVersionsHelper.UP_1_14);
	}

}
