package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.BaseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class PotionNetworkEntityMetadataFormatTransformerFactoryFactory<R extends NetworkEntityMetadataObjectIndexRegistry.PotionIndexRegistry> extends BaseNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final PotionNetworkEntityMetadataFormatTransformerFactoryFactory<NetworkEntityMetadataObjectIndexRegistry.PotionIndexRegistry> INSTANCE = new PotionNetworkEntityMetadataFormatTransformerFactoryFactory<>(NetworkEntityMetadataObjectIndexRegistry.PotionIndexRegistry.INSTANCE);

	protected PotionNetworkEntityMetadataFormatTransformerFactoryFactory(R registry) {
		super(registry);

		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(registry.ITEM, 8, version), ProtocolVersionsHelper.UP_1_17);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(registry.ITEM, 7, version), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(registry.ITEM, 6, version), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(registry.ITEM, 7, version), ProtocolVersion.MINECRAFT_1_10);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(registry.ITEM, 5, version), ProtocolVersionsHelper.ALL_1_9);
	}

}
