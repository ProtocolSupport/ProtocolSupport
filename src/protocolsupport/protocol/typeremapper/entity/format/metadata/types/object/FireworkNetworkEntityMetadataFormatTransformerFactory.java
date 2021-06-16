package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.BaseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class FireworkNetworkEntityMetadataFormatTransformerFactory extends BaseNetworkEntityMetadataFormatTransformerFactory {

	public static final FireworkNetworkEntityMetadataFormatTransformerFactory INSTANCE = new FireworkNetworkEntityMetadataFormatTransformerFactory();

	protected FireworkNetworkEntityMetadataFormatTransformerFactory() {
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.Firework.ITEM, 8, version), ProtocolVersionsHelper.UP_1_17);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.Firework.ITEM, 7, version), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.Firework.ITEM, 6, version), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.Firework.ITEM, 5, version), ProtocolVersionsHelper.ALL_1_9);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(NetworkEntityMetadataObjectIndex.Firework.ITEM, 8, version), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Firework.USER, 8), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Firework.USER, 8), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<>(NetworkEntityMetadataObjectIndex.Firework.USER, 7) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectOptionalVarInt object) {
				return new NetworkEntityMetadataObjectVarInt(object.getValue() != null ? object.getValue() : 0);
			}
		}, ProtocolVersionsHelper.RANGE__1_11_1__1_13_2);
	}

}
