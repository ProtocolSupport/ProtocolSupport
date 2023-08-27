package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class DisplayItemNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.DisplayItemIndexRegistry> extends DisplayNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final DisplayItemNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.DisplayItemIndexRegistry> INSTANCE = new DisplayItemNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.DisplayItemIndexRegistry.INSTANCE);

	protected DisplayItemNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ITEM, 22), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TYPE, 23), ProtocolVersionsHelper.UP_1_20);
	}

}
