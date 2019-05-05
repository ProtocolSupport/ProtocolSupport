package protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class BaseHorseEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public BaseHorseEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BaseHorse.FLAGS, 15), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BaseHorse.FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BaseHorse.FLAGS, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.BaseHorse.FLAGS, 16), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BaseHorse.OWNER, 16), ProtocolVersionsHelper.UP_1_14);
	}

}
