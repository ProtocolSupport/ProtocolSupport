package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class PolarBearEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public PolarBearEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.PolarBear.STANDING_UP, 15), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.PolarBear.STANDING_UP, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
	}

}
