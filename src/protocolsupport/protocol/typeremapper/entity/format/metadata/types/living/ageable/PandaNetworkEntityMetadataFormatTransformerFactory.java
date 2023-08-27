package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.AgeableNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class PandaNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.PandaIndexRegistry> extends AgeableNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final PandaNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.PandaIndexRegistry> INSTANCE = new PandaNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.PandaIndexRegistry.INSTANCE);

	protected PandaNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BREED_TIMER, 17), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BREED_TIMER, 16), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BREED_TIMER, 15), ProtocolVersionsHelper.ALL_1_14);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SNEEZE_TIMER, 18), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SNEEZE_TIMER, 17), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SNEEZE_TIMER, 16), ProtocolVersionsHelper.ALL_1_14);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.EAT_TIMER, 19), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.EAT_TIMER, 18), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.EAT_TIMER, 17), ProtocolVersionsHelper.ALL_1_14);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.GENE_MAIN, 20), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.GENE_MAIN, 19), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.GENE_MAIN, 18), ProtocolVersionsHelper.ALL_1_14);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.GENE_HIDDEN, 21), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.GENE_HIDDEN, 20), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.GENE_HIDDEN, 19), ProtocolVersionsHelper.ALL_1_14);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.PANDA_FLAGS, 22), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.PANDA_FLAGS, 21), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.PANDA_FLAGS, 20), ProtocolVersionsHelper.ALL_1_14);
	}

}
