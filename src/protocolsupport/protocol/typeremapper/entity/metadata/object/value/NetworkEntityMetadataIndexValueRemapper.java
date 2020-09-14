package protocolsupport.protocol.typeremapper.entity.metadata.object.value;

import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.entity.metadata.object.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class NetworkEntityMetadataIndexValueRemapper<T extends NetworkEntityMetadataObject<?>> extends NetworkEntityMetadataObjectRemapper {

	protected final NetworkEntityMetadataObjectIndex<T> fromIndex;
	protected final int toIndex;

	public NetworkEntityMetadataIndexValueRemapper(NetworkEntityMetadataObjectIndex<T> fromIndex, int toIndex) {
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	@Override
	public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, NetworkEntityMetadataList remapped) {
		T object = fromIndex.getObject(original);
		if (object != null) {
			remapped.add(toIndex, remapValue(object));
		}
	}

	public abstract NetworkEntityMetadataObject<?> remapValue(T object);

}
