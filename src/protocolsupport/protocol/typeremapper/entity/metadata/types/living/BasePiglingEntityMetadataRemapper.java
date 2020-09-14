package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class BasePiglingEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final BasePiglingEntityMetadataRemapper INSTANCE = new BasePiglingEntityMetadataRemapper();

	protected BasePiglingEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Piglin.ZOMBIFICATION_IMMUNITY, 15), ProtocolVersionsHelper.UP_1_16_2);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Piglin.ZOMBIFICATION_IMMUNITY, 16), ProtocolVersionsHelper.RANGE__1_16__1_16_1);
	}

}
