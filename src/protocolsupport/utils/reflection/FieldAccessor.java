package protocolsupport.utils.reflection;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FieldAccessor<T> {

	public static <T> FieldAccessor<T> of(@Nonnull Class<?> holder, @Nonnull String name) {
		return new FieldAccessor<>(ReflectionUtils.findField(holder, name));
	}

	public static <T> FieldAccessor<T> of(@Nonnull Class<?> holder, @Nonnull String name, @Nonnull Class<T> fieldType) {
		Field field = ReflectionUtils.findField(holder, name);
		if (fieldType.equals(field.getType())) {
			return new FieldAccessor<>(field);
		} else {
			throw new UncheckedReflectionException("Field type missmatch, expected " + fieldType + ", got " + field.getType());
		}
	}

	protected final FieldReader<T> reader;
	protected final FieldWriter<T> writer;

	public FieldAccessor(@Nonnull Field field) {
		field = ReflectionUtils.setAccessible(field);
		this.reader = new FieldReader<>(field);
		this.writer = new FieldWriter<>(field);
	}

	public @Nullable T get(@Nullable Object holder) {
		return reader.get(holder);
	}

	public @Nullable void set(@Nullable Object holder, @Nullable T value) {
		writer.set(holder, value);
	}

}
