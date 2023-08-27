package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNumberToIntTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.BaseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class BoatNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.BoatIndexRegistry> extends BaseNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final BoatNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.BoatIndexRegistry> INSTANCE = new BoatNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.BoatIndexRegistry.INSTANCE);

	protected BoatNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TIME_SINCE_LAST_HIT, 8), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TIME_SINCE_LAST_HIT, 7), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TIME_SINCE_LAST_HIT, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TIME_SINCE_LAST_HIT, 5), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNumberToIntTransformer(registry.TIME_SINCE_LAST_HIT, 17), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.FORWARD_DIRECTION, 9), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.FORWARD_DIRECTION, 8), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.FORWARD_DIRECTION, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.FORWARD_DIRECTION, 6), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNumberToIntTransformer(registry.FORWARD_DIRECTION, 18), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.DAMAGE_TAKEN, 10), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.DAMAGE_TAKEN, 9), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.DAMAGE_TAKEN, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.DAMAGE_TAKEN, 7), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.DAMAGE_TAKEN, 19), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1));
		add(new NetworkEntityMetadataObjectIndexValueNumberToIntTransformer(registry.DAMAGE_TAKEN, 19), ProtocolVersionsHelper.DOWN_1_5_2);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 11), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 10), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 9), ProtocolVersionsHelper.RANGE__1_10__1_13_2);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LEFT_PADDLE, 12), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LEFT_PADDLE, 11), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LEFT_PADDLE, 10), ProtocolVersionsHelper.RANGE__1_10__1_13_2);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RIGHT_PADDLE, 13), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RIGHT_PADDLE, 12), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RIGHT_PADDLE, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_2);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SPLASH_TIMER, 14), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SPLASH_TIMER, 13), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_13, ProtocolVersion.MINECRAFT_1_16_4));
	}

}
