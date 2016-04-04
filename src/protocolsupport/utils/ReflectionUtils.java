package protocolsupport.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.spigotmc.SneakyThrow;

public class ReflectionUtils {

	public static void setStaticFinalField(Field field, Object newValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		ReflectionUtils.setAccessible(Field.class.getDeclaredField("modifiers")).setInt(field, field.getModifiers() & ~Modifier.FINAL);
		ReflectionUtils.setAccessible(Field.class.getDeclaredField("root")).set(field, null);
		ReflectionUtils.setAccessible(Field.class.getDeclaredField("overrideFieldAccessor")).set(field, null);
		ReflectionUtils.setAccessible(field).set(null, newValue);
	}

	public static <T extends AccessibleObject> T setAccessible(T object) {
		object.setAccessible(true);
		return object;
	}

	public static Field getField(Class<?> clazz, String fieldName) {
		try {
			return setAccessible(clazz.getDeclaredField(fieldName));
		} catch (Throwable t) {
			SneakyThrow.sneaky(t);
		}
		return null;
	}

}
