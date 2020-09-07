package protocolsupport.utils;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionUtils {

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
		throw new RuntimeException("Can't find field " + name + " in class " + clazz + " or it's superclasses");
	}

	public static void setField(Field field, Object object, Object value) {
		try {
			field.set(object, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static Method getMethod(Class<?> clazz, String name, int paramlength) {
		do {
			for (Method method : clazz.getDeclaredMethods()) {
				if (method.getName().equals(name) && (method.getParameterTypes().length == paramlength)) {
					return setAccessible(method);
				}
			}
		} while ((clazz = clazz.getSuperclass()) != null);
		throw new RuntimeException("Can't find method " + name + " with params length " + paramlength + " in class " + clazz + " or it's superclasses");
	}

	public static void setStaticFinalField(Class<?> clazz, String fieldname, Object value) {
		try {
			Field field = setAccessible(clazz.getDeclaredField(fieldname));
			((MethodHandles.Lookup) setAccessible(MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP")).get(null))
			.findSetter(Field.class, "modifiers", int.class)
			.invokeExact(field, field.getModifiers() & ~Modifier.FINAL);
			field.set(null, value);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

}
