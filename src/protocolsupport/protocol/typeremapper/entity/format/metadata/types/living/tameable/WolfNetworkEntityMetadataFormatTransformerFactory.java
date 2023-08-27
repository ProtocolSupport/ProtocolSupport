package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.tameable;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNumberToByteTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.TameableNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class WolfNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.WolfIndexRegistry> extends TameableNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final WolfNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.WolfIndexRegistry> INSTANCE = new WolfNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.WolfIndexRegistry.INSTANCE);

	protected WolfNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BEGGING, 19), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BEGGING, 18), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BEGGING, 18), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BEGGING, 16), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BEGGING, 16), ProtocolVersion.MINECRAFT_1_10);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BEGGING, 15), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer(registry.BEGGING, 19), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.COLLAR_COLOR, 20), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.COLLAR_COLOR, 19), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.COLLAR_COLOR, 19), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.COLLAR_COLOR, 17), ProtocolVersionsHelper.ALL_1_13);
		add(new VarIntWolfCollarColorIndexValueRemapper(registry.COLLAR_COLOR, 17), ProtocolVersionsHelper.RANGE__1_10__1_12_2);
		add(new VarIntWolfCollarColorIndexValueRemapper(registry.COLLAR_COLOR, 16), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<>(registry.COLLAR_COLOR, 20) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVarInt object) {
				return new NetworkEntityMetadataObjectByte((byte) (15 - object.getValue()));
			}
		}, ProtocolVersion.MINECRAFT_1_8);
		add(new NetworkEntityMetadataObjectIndexValueNumberToByteTransformer(registry.COLLAR_COLOR, 20), ProtocolVersionsHelper.DOWN_1_7_10);
	}

	protected static class VarIntWolfCollarColorIndexValueRemapper extends NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectVarInt> {

		public VarIntWolfCollarColorIndexValueRemapper(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVarInt> fromIndex, int toIndex) {
			super(fromIndex, toIndex);
		}

		@Override
		public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVarInt object) {
			return new NetworkEntityMetadataObjectVarInt(15 - object.getValue());
		}

	}

}
