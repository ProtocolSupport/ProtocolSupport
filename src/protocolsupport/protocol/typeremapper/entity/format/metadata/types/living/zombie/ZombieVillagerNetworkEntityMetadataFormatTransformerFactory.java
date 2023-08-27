package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.zombie;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueVillagerDataToProfessionVarIntTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ZombieVillagerNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.ZombieVillagerIndexRegistry> extends ZombieNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final ZombieVillagerNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.ZombieVillagerIndexRegistry> INSTANCE = new ZombieVillagerNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.ZombieVillagerIndexRegistry.INSTANCE);

	protected ZombieVillagerNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.CONVERTING, 19), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.CONVERTING, 18), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.CONVERTING, 17), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.CONVERTING, 15), ProtocolVersionsHelper.RANGE__1_11__1_13_2);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VDATA, 20), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VDATA, 19), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VDATA, 18), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueVillagerDataToProfessionVarIntTransformer(registry.VDATA, 16), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
	}

}
