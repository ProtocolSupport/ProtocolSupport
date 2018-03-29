package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.typeremapper.watchedentity.remapper.DataWatcherDataRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class IndexValueRemapper<V, T extends DataWatcherObject<V>> extends DataWatcherDataRemapper {

	private final DataWatcherObjectIndex<T> fromIndex;
	private final int toIndex;
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
