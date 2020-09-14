package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class HoglinEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public static final HoglinEntityMetadataRemapper INSTANCE = new HoglinEntityMetadataRemapper();

	protected HoglinEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Hoglin.ZOMBIFICATION_IMMUNITY, 16), ProtocolVersionsHelper.UP_1_16);
	}

}
