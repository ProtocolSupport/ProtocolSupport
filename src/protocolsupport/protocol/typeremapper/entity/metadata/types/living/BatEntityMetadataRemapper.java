package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class BatEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public BatEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Bat.HANGING, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Bat.HANGING, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Bat.HANGING, 16), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
