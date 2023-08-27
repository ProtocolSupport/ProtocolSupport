package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.AgeableNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class BeeNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.BeeIndexRegistry> extends AgeableNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final BeeNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.BeeIndexRegistry> INSTANCE = new BeeNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.BeeIndexRegistry.INSTANCE);

	protected BeeNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BEE_FLAGS, 17), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BEE_FLAGS, 16), ProtocolVersionsHelper.RANGE__1_15__1_16_4);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ANGER, 18), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ANGER, 17), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
	}

}
