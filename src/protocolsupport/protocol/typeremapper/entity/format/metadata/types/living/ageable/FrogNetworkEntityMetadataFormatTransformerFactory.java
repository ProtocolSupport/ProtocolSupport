package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.AgeableNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class FrogNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.FrogIndexRegistry> extends AgeableNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final FrogNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.FrogIndexRegistry> INSTANCE = new FrogNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.FrogIndexRegistry.INSTANCE);

	protected FrogNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 17), ProtocolVersionsHelper.UP_1_19);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TONGOUE_TARGET, 18), ProtocolVersionsHelper.UP_1_19);
	}

}
