package protocolsupport.protocol.typeremapper.entity.legacy.metadata;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class SheepInitDefaultMetadataTransformer extends EntityMetadataTransformer<NetworkEntityMetadataObjectIndexRegistry.SheepIndexRegistry> {

	public static final SheepInitDefaultMetadataTransformer INSTANCE = new SheepInitDefaultMetadataTransformer(NetworkEntityMetadataObjectIndexRegistry.SheepIndexRegistry.INSTANCE);

	protected SheepInitDefaultMetadataTransformer(NetworkEntityMetadataObjectIndexRegistry.SheepIndexRegistry registry) {
		super(registry);
	}

	@Override
	public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
		registry.SHEEP_FLAGS.setObject(t, new NetworkEntityMetadataObjectByte((byte) 0));
	}

}
