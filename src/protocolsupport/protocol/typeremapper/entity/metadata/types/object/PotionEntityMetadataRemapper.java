package protocolsupport.protocol.typeremapper.entity.metadata.types.object;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class PotionEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public PotionEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Potion.ITEM, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Potion.ITEM, 7), ProtocolVersion.MINECRAFT_1_10);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Potion.ITEM, 5), ProtocolVersionsHelper.ALL_1_9);
	}

}
