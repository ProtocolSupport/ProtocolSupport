package protocolsupport.protocol.typeremapper.entity.legacy.metadata;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class BlazeInitDefaultMetadataTransformer extends EntityMetadataTransformer<NetworkEntityMetadataObjectIndexRegistry.BlazeIndexRegistry> {

	public static final BlazeInitDefaultMetadataTransformer INSTANCE = new BlazeInitDefaultMetadataTransformer(NetworkEntityMetadataObjectIndexRegistry.BlazeIndexRegistry.INSTANCE);

	protected BlazeInitDefaultMetadataTransformer(NetworkEntityMetadataObjectIndexRegistry.BlazeIndexRegistry registry) {
		super(registry);
	}

	@Override
	public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
		registry.ON_FIRE.setObject(t, new NetworkEntityMetadataObjectByte((byte) 0));
	}

}