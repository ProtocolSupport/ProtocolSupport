package protocolsupport.protocol.typeremapper.entity.format.metadata.types.special;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.NetworkEntityMetadataCodec.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.NetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNumberToIntTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.LivingNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.BitUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class PlayerNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.PlayerIndexRegistry> extends LivingNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final PlayerNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.PlayerIndexRegistry> INSTANCE = new PlayerNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.PlayerIndexRegistry.INSTANCE);

	public static final String DATA_KEY_BASE_FLAGS = "Player_BaseFlags";

	protected static class HandUseAsBaseFlagsTransformer {

		protected byte baseflags;
		protected byte usehands;

		public void updateBaseFlags(byte baseflags) {
			this.baseflags = baseflags;
		}

		public void updateUseHands(byte usehands) {
			this.usehands = usehands;
		}

		public byte getComputedBaseFlags() {
			return (byte) BitUtils.setIBit(baseflags, 4, usehands);
		}

	}

	protected PlayerNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ADDITIONAL_HEARTS, 15), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ADDITIONAL_HEARTS, 14), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ADDITIONAL_HEARTS, 13), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ADDITIONAL_HEARTS, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ADDITIONAL_HEARTS, 10), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ADDITIONAL_HEARTS, 17), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SCORE, 16), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SCORE, 15), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SCORE, 14), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SCORE, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SCORE, 11), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNumberToIntTransformer(registry.SCORE, 18), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SKIN_FLAGS, 17), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SKIN_FLAGS, 16), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SKIN_FLAGS, 15), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SKIN_FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SKIN_FLAGS, 12), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SKIN_FLAGS, 10), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1));

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.MAIN_HAND, 18), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.MAIN_HAND, 17), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.MAIN_HAND, 16), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.MAIN_HAND, 14), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.MAIN_HAND, 13), ProtocolVersionsHelper.ALL_1_9);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LEFT_SHOULDER_ENTITY, 19), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LEFT_SHOULDER_ENTITY, 18), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LEFT_SHOULDER_ENTITY, 17), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LEFT_SHOULDER_ENTITY, 15), ProtocolVersionsHelper.RANGE__1_12__1_13_2);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RIGHT_SHOULDER_ENTITY, 20), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RIGHT_SHOULDER_ENTITY, 19), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RIGHT_SHOULDER_ENTITY, 18), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RIGHT_SHOULDER_ENTITY, 16), ProtocolVersionsHelper.RANGE__1_12__1_13_2);

		add(new NetworkEntityMetadataFormatTransformer() {
			@Override
			public void transform(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, NetworkEntityMetadataList remapped) {
				NetworkEntityMetadataObjectByte baseflags = registry.BASE_FLAGS.getObject(original);
				NetworkEntityMetadataObjectByte handuse = registry.HAND_USE.getObject(original);
				if ((baseflags != null) || (handuse != null)) {
					HandUseAsBaseFlagsTransformer transformer = entity.getDataCache().computeDataIfAbsent(DATA_KEY_BASE_FLAGS, k -> new HandUseAsBaseFlagsTransformer());
					if (baseflags != null) {
						transformer.updateBaseFlags(baseflags.getValue());
					}
					if (handuse != null) {
						transformer.updateUseHands((byte) (handuse.getValue() & 1));
					}
					remapped.add(0, new NetworkEntityMetadataObjectByte(transformer.getComputedBaseFlags()));
				}
			}
		}, ProtocolVersionsHelper.DOWN_1_8);
	}

}
