package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.AgeableNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class AxolotlNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.AxolotlIndexRegistry> extends AgeableNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final AxolotlNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.AxolotlIndexRegistry> INSTANCE = new AxolotlNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.AxolotlIndexRegistry.INSTANCE);

	protected AxolotlNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 17), ProtocolVersionsHelper.UP_1_17);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.PLAYING_DEAD, 18), ProtocolVersionsHelper.UP_1_17);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.FROM_BUCKET, 19), ProtocolVersionsHelper.UP_1_17);
	}

}
