package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class IndexValueRemapper<T extends NetworkEntityMetadataObject<?>> extends NetworkEntityMetadataObjectRemapper {

	protected final NetworkEntityMetadataObjectIndex<T> fromIndex;
	protected final int toIndex;
	public IndexValueRemapper(NetworkEntityMetadataObjectIndex<T> fromIndex, int toIndex) {
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	@Override
	public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, ArrayMap<NetworkEntityMetadataObject<?>> remapped) {
		fromIndex.getValue(original).ifPresent(v -> remapped.put(toIndex, remapValue(v)));
	}

	public abstract NetworkEntityMetadataObject<?> remapValue(T object);

}
