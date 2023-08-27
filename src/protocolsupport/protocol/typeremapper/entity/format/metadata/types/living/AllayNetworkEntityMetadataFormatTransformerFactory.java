package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.InsentientNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class AllayNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.AllayIndexRegistry> extends InsentientNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final AllayNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.AllayIndexRegistry> INSTANCE = new AllayNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.AllayIndexRegistry.INSTANCE);

	protected AllayNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.DANCING, 16), ProtocolVersionsHelper.UP_1_19);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.CAN_DUPLICATE, 17), ProtocolVersionsHelper.UP_1_19);
	}

}
