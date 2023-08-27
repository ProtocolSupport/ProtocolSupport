package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.BaseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class InteractionNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.InteractionIndexRegistry> extends BaseNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final InteractionNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.InteractionIndexRegistry> INSTANCE = new InteractionNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.InteractionIndexRegistry.INSTANCE);

	protected InteractionNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.WIDTH, 8), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HEIGHT, 9), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.INTERACTABLE, 10), ProtocolVersionsHelper.UP_1_20);
	}

}
