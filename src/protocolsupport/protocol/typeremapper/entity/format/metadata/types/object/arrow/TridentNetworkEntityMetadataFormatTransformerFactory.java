package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.arrow;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class TridentNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.TridentIndexRegistry> extends ArrowEntityNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final TridentNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.TridentIndexRegistry> INSTANCE = new TridentNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.TridentIndexRegistry.INSTANCE);

	protected TridentNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LOYALTY, 10), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LOYALTY, 9), ProtocolVersionsHelper.ALL_1_16);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LOYALTY, 10), ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LOYALTY, 8), ProtocolVersionsHelper.ALL_1_13);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HAS_GLINT, 11), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HAS_GLINT, 10), ProtocolVersionsHelper.ALL_1_16);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HAS_GLINT, 11), ProtocolVersionsHelper.ALL_1_15);
	}

}
