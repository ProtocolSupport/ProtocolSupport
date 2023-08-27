package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.tameable;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.TameableNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ParrotNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.ParrotIndexRegistry> extends TameableNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final ParrotNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.ParrotIndexRegistry> INSTANCE = new ParrotNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.ParrotIndexRegistry.INSTANCE);

	protected ParrotNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 19), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 18), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 17), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VARIANT, 15), ProtocolVersionsHelper.RANGE__1_12__1_13_2);
	}

}
