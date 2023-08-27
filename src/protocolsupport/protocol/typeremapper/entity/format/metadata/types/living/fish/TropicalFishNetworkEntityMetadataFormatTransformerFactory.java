package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.fish;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class TropicalFishNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.TropicalFishIndexRegistry> extends FishNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final TropicalFishNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.TropicalFishIndexRegistry> INSTANCE = new TropicalFishNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.TropicalFishIndexRegistry.INSTANCE);

	protected TropicalFishNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 17), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 16), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 15), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 13), ProtocolVersionsHelper.ALL_1_13);
	}

}
