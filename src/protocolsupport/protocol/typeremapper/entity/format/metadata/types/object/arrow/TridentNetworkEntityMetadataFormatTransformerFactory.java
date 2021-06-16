package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.arrow;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class TridentNetworkEntityMetadataFormatTransformerFactory extends ArrowEntityNetworkEntityMetadataFormatTransformerFactory {

	public static final TridentNetworkEntityMetadataFormatTransformerFactory INSTANCE = new TridentNetworkEntityMetadataFormatTransformerFactory();

	protected TridentNetworkEntityMetadataFormatTransformerFactory() {
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Trident.LOYALTY, 10), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Trident.LOYALTY, 9), ProtocolVersionsHelper.ALL_1_16);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Trident.LOYALTY, 10), ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Trident.LOYALTY, 8), ProtocolVersionsHelper.ALL_1_13);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Trident.HAS_GLINT, 11), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Trident.HAS_GLINT, 10), ProtocolVersionsHelper.ALL_1_16);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Trident.HAS_GLINT, 11), ProtocolVersionsHelper.ALL_1_15);
	}

}
