package protocolsupport.protocol.typeremapper.watchedentity.remapper;

import java.util.Optional;

import gnu.trove.map.TIntObjectMap;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.NetworkEntity;

public abstract class DataWatcherDataRemapper {

	public abstract void remap(NetworkEntity entity, TIntObjectMap<DataWatcherObject<?>> original, TIntObjectMap<DataWatcherObject<?>> remapped);

	@SuppressWarnings("unchecked")
	protected <T extends DataWatcherObject<?>> Optional<T> getObject(TIntObjectMap<DataWatcherObject<?>> map, int index, Class<T> clazz) {
		DataWatcherObject<?> object = map.get(index);
		if (!clazz.isInstance(object)) {
			return Optional.empty();
		} else {
			return (Optional<T>) Optional.ofNullable(object);
		}
	}

}
