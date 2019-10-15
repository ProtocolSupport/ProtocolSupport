package protocolsupport.protocol.typeremapper.entity.metadata.types.living.tameable;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.TameableEntityMetadataRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ParrotEntityMetadataRemapper extends TameableEntityMetadataRemapper {

	public ParrotEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Parrot.VARIANT, 17), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Parrot.VARIANT, 15), ProtocolVersionsHelper.RANGE__1_12__1_13_2);
	}

}
