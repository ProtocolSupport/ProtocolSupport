package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.tameable;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.TameableNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class CatNetworkEntityMetadataFormatTransformerFactory extends TameableNetworkEntityMetadataFormatTransformerFactory {

	public static final CatNetworkEntityMetadataFormatTransformerFactory INSTANCE = new CatNetworkEntityMetadataFormatTransformerFactory();

	protected CatNetworkEntityMetadataFormatTransformerFactory() {
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Cat.VARIANT, 18), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Cat.VARIANT, 17), ProtocolVersionsHelper.ALL_1_14);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Cat.UNKNOWN_1, 19), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Cat.UNKNOWN_1, 18), ProtocolVersionsHelper.ALL_1_14);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Cat.UNKNOWN_2, 20), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Cat.UNKNOWN_2, 19), ProtocolVersionsHelper.ALL_1_14);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Cat.COLLAR_COLOR, 21), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Cat.COLLAR_COLOR, 20), ProtocolVersionsHelper.ALL_1_14);
	}

}
