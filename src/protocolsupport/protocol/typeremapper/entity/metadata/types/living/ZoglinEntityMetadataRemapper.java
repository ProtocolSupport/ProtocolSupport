package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ZoglinEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final ZoglinEntityMetadataRemapper INSTANCE = new ZoglinEntityMetadataRemapper();

	protected ZoglinEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Zoglin.IS_BABY, 15), ProtocolVersionsHelper.UP_1_16);
	}

}
