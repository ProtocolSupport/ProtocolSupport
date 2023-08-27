package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.horse;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class LamaEntityNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.LamaIndexRegistry> extends CargoHorseNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final LamaEntityNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.LamaIndexRegistry> INSTANCE = new LamaEntityNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.LamaIndexRegistry.INSTANCE);

	protected LamaEntityNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.STRENGTH, 19), ProtocolVersionsHelper.UP_1_19);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.STRENGTH, 20), ProtocolVersionsHelper.RANGE__1_17__1_18_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.STRENGTH, 19), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.STRENGTH, 18), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.STRENGTH, 16), ProtocolVersionsHelper.RANGE__1_11__1_13_2);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.CARPET_COLOR, 20), ProtocolVersionsHelper.UP_1_19);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.CARPET_COLOR, 21), ProtocolVersionsHelper.RANGE__1_17__1_18_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.CARPET_COLOR, 20), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.CARPET_COLOR, 19), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.CARPET_COLOR, 17), ProtocolVersionsHelper.RANGE__1_11__1_13_2);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 21), ProtocolVersionsHelper.UP_1_19);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 22), ProtocolVersionsHelper.RANGE__1_17__1_18_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 21), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 20), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 18), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
	}

}
