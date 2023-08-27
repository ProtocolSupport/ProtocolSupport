package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.arrow;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.BaseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ArrowEntityNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.ArrowIndexRegistry> extends BaseNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final ArrowEntityNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.ArrowIndexRegistry> INSTANCE = new ArrowEntityNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.ArrowIndexRegistry.INSTANCE);

	protected ArrowEntityNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.CRITICAL, 8), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.CRITICAL, 7), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.CRITICAL, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.CRITICAL, 5), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.CRITICAL, 15), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.PIERCING_LEVEL, 9), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.PIERCING_LEVEL, 8), ProtocolVersionsHelper.ALL_1_16);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.PIERCING_LEVEL, 9), ProtocolVersionsHelper.RANGE__1_14__1_15_2);
	}

}
