package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.typeremapper.entity.metadata.DataWatcherObjectRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class IndexValueRemapper<T extends DataWatcherObject<?>> extends DataWatcherObjectRemapper {

	protected final DataWatcherObjectIndex<T> fromIndex;
	protected final int toIndex;
	public IndexValueRemapper(DataWatcherObjectIndex<T> fromIndex, int toIndex) {
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	@Override
	public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
		fromIndex.getValue(original).ifPresent(v -> remapped.put(toIndex, remapValue(v)));
	}

	public abstract DataWatcherObject<?> remapValue(T object);

}
