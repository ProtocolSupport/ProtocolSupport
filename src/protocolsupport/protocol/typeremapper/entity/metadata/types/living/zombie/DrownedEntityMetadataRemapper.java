package protocolsupport.protocol.typeremapper.entity.metadata.types.living.zombie;

import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class DrownedEntityMetadataRemapper extends ZombieEntityMetadataRemapper {

	public DrownedEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Drowned.HAS_TARGET, 15), ProtocolVersionsHelper.UP_1_13);
	}

}
