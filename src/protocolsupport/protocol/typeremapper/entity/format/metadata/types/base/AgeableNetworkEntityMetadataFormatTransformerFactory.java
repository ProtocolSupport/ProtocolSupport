package protocolsupport.protocol.typeremapper.entity.format.metadata.types.base;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNumberToIntTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class AgeableNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.AgeableIndexRegistry> extends InsentientNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final AgeableNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.AgeableIndexRegistry> INSTANCE = new AgeableNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.AgeableIndexRegistry.INSTANCE);

	protected AgeableNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.IS_BABY, 16), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.IS_BABY, 15), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.IS_BABY, 14), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.IS_BABY, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.IS_BABY, 11), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<>(registry.IS_BABY, 12) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectBoolean object) {
				return new NetworkEntityMetadataObjectByte((byte) (object.getValue() ? -1 : 0));
			}
		}, ProtocolVersion.MINECRAFT_1_8);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<>(registry.IS_BABY, 12) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectBoolean object) {
				return new NetworkEntityMetadataObjectInt((object.getValue() ? -1 : 0));
			}
		}, ProtocolVersionsHelper.DOWN_1_7_10);

		add(new NetworkEntityMetadataObjectIndexValueNumberToIntTransformer(registry.AGE_HACK, 12), ProtocolVersionsHelper.RANGE__1_6__1_7);
	}

}
