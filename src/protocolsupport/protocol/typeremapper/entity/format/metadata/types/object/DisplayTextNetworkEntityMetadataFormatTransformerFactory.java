package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class DisplayTextNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.DisplayTextIndexRegistry> extends DisplayNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final DisplayTextNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.DisplayTextIndexRegistry> INSTANCE = new DisplayTextNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.DisplayTextIndexRegistry.INSTANCE);

	protected DisplayTextNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TEXT, 22), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.WIDTH, 23), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.COLOR, 24), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.OPACITY, 25), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.FLAGS, 26), ProtocolVersionsHelper.UP_1_20);
	}

}
