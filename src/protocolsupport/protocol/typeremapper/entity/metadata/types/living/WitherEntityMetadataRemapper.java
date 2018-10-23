package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class WitherEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public WitherEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET1, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET1, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.TARGET1, 17), ProtocolVersionsHelper.BEFORE_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET2, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET2, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.TARGET2, 18), ProtocolVersionsHelper.BEFORE_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET3, 14), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET3, 13), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.TARGET3, 19), ProtocolVersionsHelper.BEFORE_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.INVULNERABLE_TIME, 15), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.INVULNERABLE_TIME, 14), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.INVULNERABLE_TIME, 20), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
