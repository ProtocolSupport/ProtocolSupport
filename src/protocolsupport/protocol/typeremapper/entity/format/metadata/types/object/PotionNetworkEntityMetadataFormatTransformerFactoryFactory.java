package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.BaseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class PotionNetworkEntityMetadataFormatTransformerFactoryFactory extends BaseNetworkEntityMetadataFormatTransformerFactory {

	public static final PotionNetworkEntityMetadataFormatTransformerFactoryFactory INSTANCE = new PotionNetworkEntityMetadataFormatTransformerFactoryFactory();

	protected PotionNetworkEntityMetadataFormatTransformerFactoryFactory() {
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.Potion.ITEM, 8, version), ProtocolVersionsHelper.UP_1_17);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.Potion.ITEM, 7, version), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.Potion.ITEM, 6, version), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.Potion.ITEM, 7, version), ProtocolVersion.MINECRAFT_1_10);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.Potion.ITEM, 5, version), ProtocolVersionsHelper.ALL_1_9);
	}

}
