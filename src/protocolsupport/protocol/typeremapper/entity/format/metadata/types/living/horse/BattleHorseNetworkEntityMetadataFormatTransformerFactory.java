package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.horse;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNumberToIntTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class BattleHorseNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.BattleHorseIndexRegistry> extends HorseNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final BattleHorseNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.BattleHorseIndexRegistry> INSTANCE = new BattleHorseNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.BattleHorseIndexRegistry.INSTANCE);

	protected BattleHorseNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 18), ProtocolVersionsHelper.UP_1_19);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 19), ProtocolVersionsHelper.RANGE__1_17__1_18_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 18), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 17), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 15), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 14), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNumberToIntTransformer(registry.VARIANT, 20), ProtocolVersionsHelper.DOWN_1_8);
	}

}
