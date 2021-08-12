package protocolsupport.utils.reflection;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FieldWriter<T> {

	public static <T> FieldWriter<T> of(@Nonnull Class<?> holder, @Nonnull String name) {
		return new FieldWriter<>(ReflectionUtils.findField(holder, name));
	}

	public static <T> FieldWriter<T> of(@Nonnull Class<?> holder, @Nonnull String name, @Nonnull Class<T> fieldType) {
		Field field = ReflectionUtils.findField(holder, name);
		if (field.getType().isAssignableFrom(fieldType)) {
			return new FieldWriter<>(field);
		} else {
			throw new UncheckedReflectionException("Field type missmatch, expected super " + fieldType + ", got " + field.getType());
		}
	}

	protected final Field field;
	protected final boolean staticfinal;

	public FieldWriter(@Nonnull Field field) {
		this.field = ReflectionUtils.setAccessible(field);
		this.staticfinal = ReflectionUtils.isStaticFinalField(field);
	}

	public @Nullable void set(@Nullable Object holder, @Nullable T value) {
		if (!staticfinal) {
			try {
				field.set(holder, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new UncheckedReflectionException(e);
			}
		} else {
			ReflectionUtils.setStaticFinalFieldValue(field, value);
		}
	}

}
