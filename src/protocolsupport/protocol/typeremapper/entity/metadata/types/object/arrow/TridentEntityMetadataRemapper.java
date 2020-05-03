package protocolsupport.protocol.typeremapper.entity.metadata.types.object.arrow;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class TridentEntityMetadataRemapper extends ArrowEntityMetadataRemapper {

	public static final TridentEntityMetadataRemapper INSTANCE = new TridentEntityMetadataRemapper();

	protected TridentEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Trident.LOYALTY, 10), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Trident.LOYALTY, 8), ProtocolVersionsHelper.ALL_1_13);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Trident.HAS_GLINT, 11), ProtocolVersionsHelper.UP_1_15);
	}

}
