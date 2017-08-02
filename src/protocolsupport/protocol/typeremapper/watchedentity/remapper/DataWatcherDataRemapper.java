package protocolsupport.protocol.typeremapper.watchedentity.remapper;

import java.util.Optional;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class DataWatcherDataRemapper {

	public abstract void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped);

	@SuppressWarnings("unchecked")
	protected <T extends DataWatcherObject<?>> Optional<T> getObject(ArrayMap<DataWatcherObject<?>> map, int index, Class<T> clazz) {
		DataWatcherObject<?> object = map.get(index);
		if (!clazz.isInstance(object)) {
			return Optional.empty();
		} else {
			return (Optional<T>) Optional.ofNullable(object);
		}
	}

}
