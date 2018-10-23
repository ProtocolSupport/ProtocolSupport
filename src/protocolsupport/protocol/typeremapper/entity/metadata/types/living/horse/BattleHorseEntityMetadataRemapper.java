package protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class BattleHorseEntityMetadataRemapper extends BaseHorseEntityMetadataRemapper {

	public static final BattleHorseEntityMetadataRemapper INSTANCE = new BattleHorseEntityMetadataRemapper();

	public BattleHorseEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BattleHorse.VARIANT, 15), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BattleHorse.VARIANT, 14), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.BattleHorse.VARIANT, 20), ProtocolVersionsHelper.BEFORE_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BattleHorse.ARMOR, 16), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BattleHorse.ARMOR, 17), ProtocolVersion.MINECRAFT_1_10);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BattleHorse.ARMOR, 16), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.BattleHorse.ARMOR, 22), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
