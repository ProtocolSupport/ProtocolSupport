package protocolsupport.utils.reflection;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FieldReader<T> {

	public static <T> FieldReader<T> of(@Nonnull Class<?> holder, @Nonnull String name) {
		return new FieldReader<>(ReflectionUtils.findField(holder, name));
	}

	public static <T> FieldReader<T> of(@Nonnull Class<?> holder, @Nonnull String name, @Nonnull Class<T> fieldType) {
		Field field = ReflectionUtils.findField(holder, name);
		if (fieldType.isAssignableFrom(field.getType())) {
			return new FieldReader<>(field);
		} else {
			throw new UncheckedReflectionException("Field type missmatch, expected extends " + fieldType + ", got " + field.getType());
		}
	}

	protected final Field field;

	public FieldReader(@Nonnull Field field) {
		this.field = ReflectionUtils.setAccessible(field);
	}

	@SuppressWarnings("unchecked")
	public @Nullable T get(@Nullable Object holder) {
		try {
			return (T) field.get(holder);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new UncheckedReflectionException(e);
		}
	}

}
