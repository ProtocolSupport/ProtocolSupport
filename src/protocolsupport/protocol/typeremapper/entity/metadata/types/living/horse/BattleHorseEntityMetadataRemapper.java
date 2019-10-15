package protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class BattleHorseEntityMetadataRemapper extends BaseHorseEntityMetadataRemapper {

	public static final BattleHorseEntityMetadataRemapper INSTANCE = new BattleHorseEntityMetadataRemapper();

	public BattleHorseEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BattleHorse.VARIANT, 17), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BattleHorse.VARIANT, 15), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BattleHorse.VARIANT, 14), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.BattleHorse.VARIANT, 20), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BattleHorse.ARMOR, 18), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BattleHorse.ARMOR, 16), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BattleHorse.ARMOR, 17), ProtocolVersion.MINECRAFT_1_10);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BattleHorse.ARMOR, 16), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.BattleHorse.ARMOR, 22), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
