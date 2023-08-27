package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.horse;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class CamelNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.CamelIndexRegistry> extends HorseNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final CamelNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.CamelIndexRegistry> INSTANCE = new CamelNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.CamelIndexRegistry.INSTANCE);

	protected CamelNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.DASING, 18), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LAST_POSE_CHANGE_TIME, 19), ProtocolVersionsHelper.UP_1_20);
	}

}
