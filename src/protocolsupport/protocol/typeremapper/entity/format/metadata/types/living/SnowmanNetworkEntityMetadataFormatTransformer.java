package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.InsentientNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class SnowmanNetworkEntityMetadataFormatTransformer<R extends NetworkEntityMetadataObjectIndexRegistry.SnowmanIndexRegistry> extends InsentientNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final SnowmanNetworkEntityMetadataFormatTransformer<NetworkEntityMetadataObjectIndexRegistry.SnowmanIndexRegistry> INSTANCE = new SnowmanNetworkEntityMetadataFormatTransformer<>(NetworkEntityMetadataObjectIndexRegistry.SnowmanIndexRegistry.INSTANCE);

	protected SnowmanNetworkEntityMetadataFormatTransformer(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.NO_HAT, 16), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.NO_HAT, 15), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.NO_HAT, 14), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.NO_HAT, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.NO_HAT, 11), ProtocolVersionsHelper.ALL_1_9);
	}

}
