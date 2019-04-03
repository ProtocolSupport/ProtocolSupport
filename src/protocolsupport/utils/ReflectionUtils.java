package protocolsupport.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionUtils {

	public static void setStaticFinalField(Field field, Object newValue) throws NoSuchFieldException, IllegalAccessException {
		ReflectionUtils.setAccessible(Field.class.getDeclaredField("modifiers")).setInt(field, field.getModifiers() & ~Modifier.FINAL);
		ReflectionUtils.setAccessible(Field.class.getDeclaredField("root")).set(field, null);
		ReflectionUtils.setAccessible(Field.class.getDeclaredField("overrideFieldAccessor")).set(field, null);
		ReflectionUtils.setAccessible(field).set(null, newValue);
	}

	public static <T extends AccessibleObject> T setAccessible(T object) {
		object.setAccessible(true);
		return object;
	}

	public static Field getField(Class<?> clazz, String name) {
		do {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.getName().equals(name)) {
					return setAccessible(field);
				}
			}
		} while ((clazz = clazz.getSuperclass()) != null);
		throw new RuntimeException("Can't find field " + name);
	}

	public static Method getMethod(Class<?> clazz, String name, int paramlength) {
		do {
			for (Method method : clazz.getDeclaredMethods()) {
				if (method.getName().equals(name) && (method.getParameterTypes().length == paramlength)) {
					return setAccessible(method);
				}
			}
		} while ((clazz = clazz.getSuperclass()) != null);
		throw new RuntimeException("Can't find method " + name + " with params length " + paramlength);
	}

}
