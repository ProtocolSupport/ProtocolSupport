package protocolsupport.protocol.typeremapper.entity.legacy.metadata;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class SpiderInitDefaultMetadataTransformer extends EntityMetadataTransformer<NetworkEntityMetadataObjectIndexRegistry.SpiderIndexRegistry> {

	public static final SpiderInitDefaultMetadataTransformer INSTANCE = new SpiderInitDefaultMetadataTransformer(NetworkEntityMetadataObjectIndexRegistry.SpiderIndexRegistry.INSTANCE);

	protected SpiderInitDefaultMetadataTransformer(NetworkEntityMetadataObjectIndexRegistry.SpiderIndexRegistry registry) {
		super(registry);
	}

	@Override
	public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
		registry.CLIMBING.setObject(t, new NetworkEntityMetadataObjectByte((byte) 0));
	}

}