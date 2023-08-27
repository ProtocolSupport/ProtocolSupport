package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.tameable;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.TameableNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectCatVariant;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class CatNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.CatIndexRegistry> extends TameableNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final CatNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.CatIndexRegistry> INSTANCE = new CatNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.CatIndexRegistry.INSTANCE);

	protected CatNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 19), ProtocolVersionsHelper.UP_1_19);
		add(new NetworkEntityMetadataObjectIndexValueCatVariantVarIntTransformer(registry.VARIANT, 19), ProtocolVersionsHelper.RANGE__1_17__1_18_2);
		add(new NetworkEntityMetadataObjectIndexValueCatVariantVarIntTransformer(registry.VARIANT, 18), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueCatVariantVarIntTransformer(registry.VARIANT, 17), ProtocolVersionsHelper.ALL_1_14);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LYING, 20), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LYING, 19), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LYING, 18), ProtocolVersionsHelper.ALL_1_14);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RELAXING, 21), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RELAXING, 20), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.RELAXING, 19), ProtocolVersionsHelper.ALL_1_14);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.COLLAR_COLOR, 22), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.COLLAR_COLOR, 21), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.COLLAR_COLOR, 20), ProtocolVersionsHelper.ALL_1_14);
	}

	protected static class NetworkEntityMetadataObjectIndexValueCatVariantVarIntTransformer extends NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectCatVariant> {

		protected NetworkEntityMetadataObjectIndexValueCatVariantVarIntTransformer(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectCatVariant> fromIndex, int toIndex) {
			super(fromIndex, toIndex);
		}

		@Override
		public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectCatVariant object) {
			return new NetworkEntityMetadataObjectVarInt(object.getValue());
		}

	}

}
