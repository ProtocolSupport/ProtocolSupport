package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.AgeableNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class StriderNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.StriderIndexRegistry> extends AgeableNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final StriderNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.StriderIndexRegistry> INSTANCE = new StriderNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.StriderIndexRegistry.INSTANCE);

	protected StriderNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BOOST_TIME, 17), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BOOST_TIME, 16), ProtocolVersionsHelper.ALL_1_16);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.DISPLAY_NAMETAG, 18), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.DISPLAY_NAMETAG, 17), ProtocolVersionsHelper.ALL_1_16);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HAS_SADDLE, 19), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HAS_SADDLE, 18), ProtocolVersionsHelper.ALL_1_16);
	}

}
