package protocolsupport.protocol.typeremapper.entity.format.metadata.types.special;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.LivingNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ArmorStandNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.ArmorStandIndexRegistry> extends LivingNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final ArmorStandNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.ArmorStandIndexRegistry> INSTANCE = new ArmorStandNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.ArmorStandIndexRegistry.INSTANCE);

	protected ArmorStandNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ARMORSTAND_FLAGS, 15), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ARMORSTAND_FLAGS, 14), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ARMORSTAND_FLAGS, 13), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ARMORSTAND_FLAGS, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ARMORSTAND_FLAGS, 10), ProtocolVersionsHelper.RANGE__1_8__1_9);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HEAD_ROT, 16), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HEAD_ROT, 15), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HEAD_ROT, 14), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HEAD_ROT, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HEAD_ROT, 11), ProtocolVersionsHelper.RANGE__1_8__1_9);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BODY_ROT, 17), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BODY_ROT, 16), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BODY_ROT, 15), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BODY_ROT, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BODY_ROT, 12), ProtocolVersionsHelper.RANGE__1_8__1_9);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LEFT_ARM_ROT, 18), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LEFT_ARM_ROT, 17), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LEFT_ARM_ROT, 16), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LEFT_ARM_ROT, 14), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LEFT_ARM_ROT, 13), ProtocolVersionsHelper.RANGE__1_8__1_9);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RIGHT_ARM_ROT, 19), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RIGHT_ARM_ROT, 18), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RIGHT_ARM_ROT, 17), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RIGHT_ARM_ROT, 15), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RIGHT_ARM_ROT, 14), ProtocolVersionsHelper.RANGE__1_8__1_9);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LEFT_LEG_ROT, 20), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LEFT_LEG_ROT, 19), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LEFT_LEG_ROT, 18), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LEFT_LEG_ROT, 16), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LEFT_LEG_ROT, 15), ProtocolVersionsHelper.RANGE__1_8__1_9);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RIGHT_LEG_ROT, 21), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RIGHT_LEG_ROT, 20), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RIGHT_LEG_ROT, 19), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RIGHT_LEG_ROT, 17), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RIGHT_LEG_ROT, 16), ProtocolVersionsHelper.RANGE__1_8__1_9);
	}

}
