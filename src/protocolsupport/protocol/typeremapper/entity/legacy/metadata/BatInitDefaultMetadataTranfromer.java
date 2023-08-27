package protocolsupport.protocol.typeremapper.entity.legacy.metadata;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class BatInitDefaultMetadataTranfromer extends EntityMetadataTransformer<NetworkEntityMetadataObjectIndexRegistry.BatIndexRegistry> {

	public static final BatInitDefaultMetadataTranfromer INSTANCE = new BatInitDefaultMetadataTranfromer(NetworkEntityMetadataObjectIndexRegistry.BatIndexRegistry.INSTANCE);

	protected BatInitDefaultMetadataTranfromer(NetworkEntityMetadataObjectIndexRegistry.BatIndexRegistry registry) {
		super(registry);
	}

	@Override
	public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
		registry.HANGING.setObject(t, new NetworkEntityMetadataObjectByte((byte) 0));
	}

}