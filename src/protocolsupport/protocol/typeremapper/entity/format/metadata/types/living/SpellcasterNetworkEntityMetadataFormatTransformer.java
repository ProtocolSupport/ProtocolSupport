package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class SpellcasterNetworkEntityMetadataFormatTransformer<R extends NetworkEntityMetadataObjectIndexRegistry.SpellcasterIllagerIndexRegistry> extends IllagerNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final SpellcasterNetworkEntityMetadataFormatTransformer<NetworkEntityMetadataObjectIndexRegistry.SpellcasterIllagerIndexRegistry> INSTANCE = new SpellcasterNetworkEntityMetadataFormatTransformer<>(NetworkEntityMetadataObjectIndexRegistry.SpellcasterIllagerIndexRegistry.INSTANCE);

	protected SpellcasterNetworkEntityMetadataFormatTransformer(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SPELL, 17), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SPELL, 16), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SPELL, 15), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SPELL, 13), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
	}

}
