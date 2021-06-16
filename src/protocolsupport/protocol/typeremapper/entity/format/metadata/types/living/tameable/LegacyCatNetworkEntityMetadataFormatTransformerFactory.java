package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.tameable;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.TameableNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class LegacyCatNetworkEntityMetadataFormatTransformerFactory extends TameableNetworkEntityMetadataFormatTransformerFactory {

	public static final LegacyCatNetworkEntityMetadataFormatTransformerFactory INSTANCE = new LegacyCatNetworkEntityMetadataFormatTransformerFactory();

	protected LegacyCatNetworkEntityMetadataFormatTransformerFactory() {
		add(new NetworkEntityMetadataObjectIndexValueTransformer<>(NetworkEntityMetadataObjectIndex.Cat.VARIANT, 15) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVarInt object) {
				return new NetworkEntityMetadataObjectVarInt(getLegacyCatVariant(object.getValue()));
			}
		}, ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<>(NetworkEntityMetadataObjectIndex.Cat.VARIANT, 14) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVarInt object) {
				return new NetworkEntityMetadataObjectVarInt(getLegacyCatVariant(object.getValue()));
			}
		}, ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<>(NetworkEntityMetadataObjectIndex.Cat.VARIANT, 18) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVarInt object) {
				return new NetworkEntityMetadataObjectByte((byte) getLegacyCatVariant(object.getValue()));
			}
		}, ProtocolVersionsHelper.DOWN_1_8);
	}

	protected static int getLegacyCatVariant(int modernType) {
		switch (modernType) {
			case 10: {
				return 1;
			}
			case 0:
			case 5:
			case 6: {
				return 2;
			}
			case 4:
			case 7:
			case 8:
			case 9: {
				return 3;
			}
			default: {
				return modernType;
			}
		}
	}

}
