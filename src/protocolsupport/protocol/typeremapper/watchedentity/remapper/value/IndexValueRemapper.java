package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;

import protocolsupport.protocol.typeremapper.watchedentity.remapper.DataWatcherDataRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class IndexValueRemapper<T extends DataWatcherObject<?>> extends DataWatcherDataRemapper {

	private final int fromIndex;
	private final int toIndex;
	public IndexValueRemapper(int fromIndex, int toIndex) {
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	private final Class<T> typeClass = getTypeClassImpl();
	@SuppressWarnings("unchecked")
	private Class<T> getTypeClassImpl() {
		ParameterizedType ptype = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type type = ptype.getActualTypeArguments()[0];
		try {
			return (Class<T>) Class.forName(getTypeName(type));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unable to get generic type", e);
		}
	}

	private static String getTypeName(Type type) {
		if (type instanceof Class) {
			return ((Class<?>) type).getName();
		} else if (type instanceof ParameterizedType) {
			return getTypeName(((ParameterizedType) type).getRawType());
		} else {
			throw new IllegalArgumentException(MessageFormat.format("Cant extract generic type from {0}:{1}", type.getClass().getName(), type));
		}
	}

	@Override
	public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
		getObject(original, fromIndex, typeClass).ifPresent(o -> remapped.put(toIndex, remapValue(o)));
	}

	public abstract DataWatcherObject<?> remapValue(T object);

}
