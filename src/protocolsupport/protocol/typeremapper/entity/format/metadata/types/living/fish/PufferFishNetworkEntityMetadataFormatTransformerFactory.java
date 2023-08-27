package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.fish;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class PufferFishNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.PufferFishIndexRegistry> extends FishNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final PufferFishNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.PufferFishIndexRegistry> INSTANCE = new PufferFishNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.PufferFishIndexRegistry.INSTANCE);

	protected PufferFishNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.PUFF_STATE, 17), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.PUFF_STATE, 16), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.PUFF_STATE, 15), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.PUFF_STATE, 13), ProtocolVersionsHelper.ALL_1_13);
	}

}
