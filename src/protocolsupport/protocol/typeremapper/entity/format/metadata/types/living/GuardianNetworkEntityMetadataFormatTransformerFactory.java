package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNumberToIntTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.InsentientNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class GuardianNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.GuardianIndexRegistry> extends InsentientNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final GuardianNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.GuardianIndexRegistry> INSTANCE = new GuardianNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.GuardianIndexRegistry.INSTANCE, false);

	public GuardianNetworkEntityMetadataFormatTransformerFactory(R registry, boolean isLegacyElder) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SPIKES, 16), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SPIKES, 15), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SPIKES, 14), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SPIKES, 12), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<>(registry.SPIKES, 12) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectBoolean object) {
				return new NetworkEntityMetadataObjectByte((byte) createLegacyFlags(object.getValue(), isLegacyElder));
			}
		}, ProtocolVersion.MINECRAFT_1_10);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<>(registry.SPIKES, 11) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectBoolean object) {
				return new NetworkEntityMetadataObjectByte((byte) createLegacyFlags(object.getValue(), isLegacyElder));
			}
		}, ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<>(registry.SPIKES, 16) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectBoolean object) {
				return new NetworkEntityMetadataObjectInt(createLegacyFlags(object.getValue(), isLegacyElder));
			}
		}, ProtocolVersion.MINECRAFT_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET_ID, 17), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET_ID, 16), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET_ID, 15), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET_ID, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET_ID, 12), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNumberToIntTransformer(registry.TARGET_ID, 17), ProtocolVersion.MINECRAFT_1_8);
	}

	protected static int createLegacyFlags(boolean spikes, boolean elder) {
		int legacyFlags = spikes ? 0b10 : 0;
		return elder ? legacyFlags | 0b100 : legacyFlags;
	}

}
