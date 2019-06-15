package protocolsupport.protocol.typeremapper.entity.metadata;

import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class NetworkEntityMetadataObjectRemapper {

	public abstract void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, ArrayMap<NetworkEntityMetadataObject<?>> remapped);

}
