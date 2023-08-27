package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.BaseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ItemEntityNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.ItemIndexRegistry> extends BaseNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final ItemEntityNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.ItemIndexRegistry> INSTANCE = new ItemEntityNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.ItemIndexRegistry.INSTANCE);

	protected ItemEntityNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(registry.ITEM, 8, version), ProtocolVersionsHelper.UP_1_17);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(registry.ITEM, 7, version), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(registry.ITEM, 6, version), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(registry.ITEM, 5, version), ProtocolVersionsHelper.ALL_1_9);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(registry.ITEM, 10, version), ProtocolVersionsHelper.DOWN_1_8);
	}

}
