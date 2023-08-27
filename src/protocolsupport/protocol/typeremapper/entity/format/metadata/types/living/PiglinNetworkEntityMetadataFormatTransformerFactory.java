package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class PiglinNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.PiglinIndexRegistry> extends BasePiglingNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final PiglinNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.PiglinIndexRegistry> INSTANCE = new PiglinNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.PiglinIndexRegistry.INSTANCE);

	protected PiglinNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.IS_BABY, 17), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.IS_BABY, 16), ProtocolVersionsHelper.RANGE__1_16_2__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.IS_BABY, 15), ProtocolVersionsHelper.RANGE__1_16__1_16_1);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.USING_CROSSBOW, 18), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.USING_CROSSBOW, 17), ProtocolVersionsHelper.ALL_1_16);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.DANDCING, 19), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.DANDCING, 18), ProtocolVersionsHelper.ALL_1_16);
	}

}
