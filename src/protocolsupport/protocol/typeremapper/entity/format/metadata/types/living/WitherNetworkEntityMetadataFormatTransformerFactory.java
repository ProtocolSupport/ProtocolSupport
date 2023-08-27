package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNumberToIntTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.InsentientNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class WitherNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.WitherIndexRegistry> extends InsentientNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final WitherNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.WitherIndexRegistry> INSTANCE = new WitherNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.WitherIndexRegistry.INSTANCE);

	protected WitherNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET1, 16), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET1, 15), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET1, 14), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET1, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET1, 11), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNumberToIntTransformer(registry.TARGET1, 17), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET2, 17), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET2, 16), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET2, 15), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET2, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET2, 12), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNumberToIntTransformer(registry.TARGET2, 18), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET3, 18), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET3, 17), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET3, 16), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET3, 14), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TARGET3, 13), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNumberToIntTransformer(registry.TARGET3, 19), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.INVULNERABLE_TIME, 19), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.INVULNERABLE_TIME, 18), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.INVULNERABLE_TIME, 17), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.INVULNERABLE_TIME, 15), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.INVULNERABLE_TIME, 14), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNumberToIntTransformer(registry.INVULNERABLE_TIME, 20), ProtocolVersionsHelper.DOWN_1_8);
	}

}
