package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class BeeEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public static final BeeEntityMetadataRemapper INSTANCE = new BeeEntityMetadataRemapper();

	protected BeeEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Bee.BEE_FLAGS, 16), ProtocolVersionsHelper.UP_1_15);

		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Bee.ANGER, 17), ProtocolVersionsHelper.UP_1_15);
	}

}
