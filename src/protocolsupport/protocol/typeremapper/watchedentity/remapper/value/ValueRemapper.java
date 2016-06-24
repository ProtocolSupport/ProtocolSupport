package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.spigotmc.SneakyThrow;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public abstract class ValueRemapper<T extends DataWatcherObject<?>> {

	private final Class<?> typeClass = getTypeClassImpl();
	private Class<?> getTypeClassImpl() {
		ParameterizedType ptype = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type type = ptype.getActualTypeArguments()[0];
		try {
			return Class.forName(extractRawType(type).getTypeName());
		} catch (ClassNotFoundException e) {
			SneakyThrow.sneaky(e);
			return null;
		}
	}

	private static Type extractRawType(Type type) {
		if (type instanceof ParameterizedType) {
			return ((ParameterizedType) type).getRawType();
		}
		return type;
	}

	public boolean isValid(DataWatcherObject<?> object) {
		return typeClass.isInstance(object);
	}

	public abstract DataWatcherObject<?> remap(T object);

}
