package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.horse;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class CargoHorseNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.CargoHorseIndexRegistry> extends HorseNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final CargoHorseNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.CargoHorseIndexRegistry> INSTANCE = new CargoHorseNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.CargoHorseIndexRegistry.INSTANCE);

	protected CargoHorseNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HAS_CHEST, 18), ProtocolVersionsHelper.UP_1_19);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HAS_CHEST, 19), ProtocolVersionsHelper.RANGE__1_17__1_18_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HAS_CHEST, 18), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HAS_CHEST, 17), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HAS_CHEST, 15), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
	}

}
