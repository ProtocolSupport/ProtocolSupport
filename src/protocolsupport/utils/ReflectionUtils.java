package protocolsupport.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;

import org.apache.commons.lang3.Validate;

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

	public static void cloneFields(Object from, Object to) {
		Validate.isTrue(from.getClass() == to.getClass(), MessageFormat.format("Object types missmatch: {0},{1}", from.getClass(), to.getClass()));
		try {
		Class<?> clazz = from.getClass();
			do {
				for (Field field : clazz.getDeclaredFields()) {
					if (!Modifier.isStatic(field.getModifiers())) {
						ReflectionUtils.setAccessible(field);
						field.set(to, field.get(from));
					}
				}
			} while ((clazz = clazz.getSuperclass()) != null);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Unable to get/set object fields values", e);
		}
	}

}
