package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.tameable;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.TameableNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectCatVariant;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class LegacyCatNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.CatIndexRegistry> extends TameableNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final LegacyCatNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.CatIndexRegistry> INSTANCE = new LegacyCatNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.CatIndexRegistry.INSTANCE);

	protected LegacyCatNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueTransformer<>(registry.VARIANT, 15) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectCatVariant object) {
				return new NetworkEntityMetadataObjectVarInt(getLegacyCatVariant(object.getValue()));
			}
		}, ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<>(registry.VARIANT, 14) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectCatVariant object) {
				return new NetworkEntityMetadataObjectVarInt(getLegacyCatVariant(object.getValue()));
			}
		}, ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<>(registry.VARIANT, 18) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectCatVariant object) {
				return new NetworkEntityMetadataObjectByte((byte) getLegacyCatVariant(object.getValue()));
			}
		}, ProtocolVersionsHelper.DOWN_1_8);
	}

	protected static int getLegacyCatVariant(int modernType) {
		return switch (modernType) {
			case 10 -> 1;
			case 0, 5, 6 -> 2;
			case 4, 7, 8, 9 -> 3;
			default -> modernType;
		};
	}

}
