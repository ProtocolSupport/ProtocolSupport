package protocolsupport.protocol.typeremapper.entity.legacy.metadata;

import java.util.function.Consumer;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class EntityMetadataTransformer<R extends NetworkEntityMetadataObjectIndexRegistry> implements Consumer<ArrayMap<NetworkEntityMetadataObject<?>>>  {

	protected final R registry;

	public EntityMetadataTransformer(R registry) {
		this.registry = registry;
	}

}
