package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.AgeableNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class FoxNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.FoxIndexRegistry> extends AgeableNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final FoxNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.FoxIndexRegistry> INSTANCE = new FoxNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.FoxIndexRegistry.INSTANCE);

	protected FoxNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 17), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 16), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 15), ProtocolVersionsHelper.ALL_1_14);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.FOX_FLAGS, 18), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.FOX_FLAGS, 17), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.FOX_FLAGS, 16), ProtocolVersionsHelper.ALL_1_14);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.UNKNOWN_1, 19), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.UNKNOWN_1, 18), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.UNKNOWN_1, 17), ProtocolVersionsHelper.ALL_1_14);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.UNKNOWN_2, 19), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.UNKNOWN_2, 19), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.UNKNOWN_2, 18), ProtocolVersionsHelper.ALL_1_14);
	}

}
