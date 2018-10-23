package protocolsupport.protocol.typeremapper.entity.metadata.types.living.tameable;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.TameableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class ParrotEntityMetadataRemapper extends TameableEntityMetadataRemapper {

	public ParrotEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Parrot.VARIANT, 15), ProtocolVersionsHelper.RANGE__1_12__1_13_2);
	}

}
