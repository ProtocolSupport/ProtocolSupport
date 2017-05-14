package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import gnu.trove.map.TIntObjectMap;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.DataWatcherDataRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.NetworkEntity;

public abstract class WatchedDataIndexValueRemapper<T extends DataWatcherObject<?>> extends DataWatcherDataRemapper {

	private final int fromIndex;
	private final int toIndex;
	public WatchedDataIndexValueRemapper(int fromIndex, int toIndex) {
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	private final Class<T> typeClass = getTypeClassImpl();
	@SuppressWarnings("unchecked")
	private Class<T> getTypeClassImpl() {
		ParameterizedType ptype = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type type = ptype.getActualTypeArguments()[0];
		try {
			return (Class<T>) Class.forName(type.getTypeName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unable to get generic type", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void remap(NetworkEntity entity, TIntObjectMap<DataWatcherObject<?>> original, TIntObjectMap<DataWatcherObject<?>> remapped) {
		getObject(original, fromIndex, typeClass).ifPresent(o -> remapped.put(toIndex, remapValue((T) o.getValue())));
	}

	public abstract DataWatcherObject<?> remapValue(T object);

}
