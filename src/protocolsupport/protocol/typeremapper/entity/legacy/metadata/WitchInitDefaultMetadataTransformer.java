package protocolsupport.protocol.typeremapper.entity.legacy.metadata;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class WitchInitDefaultMetadataTransformer extends EntityMetadataTransformer<NetworkEntityMetadataObjectIndexRegistry.WitchIndexRegistry> {

	public static final WitchInitDefaultMetadataTransformer INSTANCE = new WitchInitDefaultMetadataTransformer(NetworkEntityMetadataObjectIndexRegistry.WitchIndexRegistry.INSTANCE);

	protected WitchInitDefaultMetadataTransformer(NetworkEntityMetadataObjectIndexRegistry.WitchIndexRegistry registry) {
		super(registry);
	}

	@Override
	public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
		registry.DRINKING_POTION.setObject(t, new NetworkEntityMetadataObjectBoolean(false));
	}

}