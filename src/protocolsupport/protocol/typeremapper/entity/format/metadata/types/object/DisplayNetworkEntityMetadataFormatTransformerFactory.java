package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.BaseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class DisplayNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.DisplayIndexRegistry> extends BaseNetworkEntityMetadataFormatTransformerFactory<R> {

	protected DisplayNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.INTERP_DEPLAY, 8), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.INTERP_DURATION, 9), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.TRANSLATION, 10), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SCALE, 11), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ROTATION_LEFT, 12), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ROTATION_RIGHT, 13), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.CONSTRAINTS, 14), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BRIGHTNESS, 15), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VIEW_RANGE, 16), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SHADOW_RADIUS, 17), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SHADOW_STRENGTH, 18), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.WIDTH, 19), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.HEIGHT, 20), ProtocolVersionsHelper.UP_1_20);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.GLOW_COLOR, 21), ProtocolVersionsHelper.UP_1_20);
	}

}
