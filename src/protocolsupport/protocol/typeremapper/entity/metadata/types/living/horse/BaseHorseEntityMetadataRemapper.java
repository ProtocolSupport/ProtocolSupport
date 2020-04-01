package protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class BaseHorseEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public static final BaseHorseEntityMetadataRemapper INSTANCE = new BaseHorseEntityMetadataRemapper();

	public BaseHorseEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BaseHorse.HORSE_FLAGS, 16), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BaseHorse.HORSE_FLAGS, 15), ProtocolVersionsHelper.ALL_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BaseHorse.HORSE_FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BaseHorse.HORSE_FLAGS, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.BaseHorse.HORSE_FLAGS, 16), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BaseHorse.OWNER, 17), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BaseHorse.OWNER, 16), ProtocolVersionsHelper.ALL_1_14);
	}

}
