package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class ShulkerEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public ShulkerEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.DIRECTION, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.DIRECTION, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.ATTACHMENT_POS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.ATTACHMENT_POS, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.SHIELD_HEIGHT, 14), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.SHIELD_HEIGHT, 13), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.COLOR, 15), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
	}

}
