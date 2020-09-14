package protocolsupport.protocol.typeremapper.entity.metadata.types.object.arrow;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class TridentEntityMetadataRemapper extends ArrowEntityMetadataRemapper {

	public static final TridentEntityMetadataRemapper INSTANCE = new TridentEntityMetadataRemapper();

	protected TridentEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Trident.LOYALTY, 9), ProtocolVersionsHelper.UP_1_16);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Trident.LOYALTY, 10), ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Trident.LOYALTY, 8), ProtocolVersionsHelper.ALL_1_13);

		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Trident.HAS_GLINT, 10), ProtocolVersionsHelper.UP_1_16);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Trident.HAS_GLINT, 11), ProtocolVersionsHelper.ALL_1_15);
	}

}
