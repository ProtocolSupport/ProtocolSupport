package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.BaseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ItemEntityNetworkEntityMetadataFormatTransformerFactory extends BaseNetworkEntityMetadataFormatTransformerFactory {

	public static final ItemEntityNetworkEntityMetadataFormatTransformerFactory INSTANCE = new ItemEntityNetworkEntityMetadataFormatTransformerFactory();

	protected ItemEntityNetworkEntityMetadataFormatTransformerFactory() {
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.Item.ITEM, 7, version), ProtocolVersionsHelper.UP_1_14);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.Item.ITEM, 6, version), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.Item.ITEM, 5, version), ProtocolVersionsHelper.ALL_1_9);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.Item.ITEM, 10, version), ProtocolVersionsHelper.DOWN_1_8);
	}

}
