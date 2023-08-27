package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.AgeableNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class TurtleNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.TurtleIndexRegistry> extends AgeableNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final TurtleNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.TurtleIndexRegistry> INSTANCE = new TurtleNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.TurtleIndexRegistry.INSTANCE);

	protected TurtleNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HOME_POS, 17), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HOME_POS, 16), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HOME_POS, 15), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HOME_POS, 13), ProtocolVersionsHelper.ALL_1_13);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HAS_EGG, 18), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HAS_EGG, 17), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HAS_EGG, 16), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HAS_EGG, 14), ProtocolVersionsHelper.ALL_1_13);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LAYING_EGG, 19), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LAYING_EGG, 18), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LAYING_EGG, 17), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LAYING_EGG, 15), ProtocolVersionsHelper.ALL_1_13);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TRAVEL_POS, 20), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TRAVEL_POS, 19), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TRAVEL_POS, 18), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TRAVEL_POS, 16), ProtocolVersionsHelper.ALL_1_13);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.GOING_HOME, 21), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.GOING_HOME, 20), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.GOING_HOME, 19), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.GOING_HOME, 17), ProtocolVersionsHelper.ALL_1_13);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TRAVELING, 22), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TRAVELING, 21), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TRAVELING, 20), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TRAVELING, 18), ProtocolVersionsHelper.ALL_1_13);
	}

}
