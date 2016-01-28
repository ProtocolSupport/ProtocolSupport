package protocolsupport.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

	public static String clampString(String string, int limit) {
		return string.substring(0, string.length() > limit ? limit : string.length());
	}

	public static <T extends AccessibleObject> T setAccessible(T object) {
		object.setAccessible(true);
		return object;
	}

	public static void setStaticFinalField(Field field, Object newValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		setAccessible(Field.class.getDeclaredField("modifiers")).setInt(field, field.getModifiers() & ~Modifier.FINAL);
		setAccessible(Field.class.getDeclaredField("root")).set(field, null);
		setAccessible(Field.class.getDeclaredField("overrideFieldAccessor")).set(field, null);
		setAccessible(field).set(null, newValue);
	}

	public static List<int[]> splitArray(int[] array, int limit) {
		List<int[]> list = new ArrayList<int[]>();
		if (array.length <= limit) {
			list.add(array);
			return list;
		}
		int copied = 0;
		int count = array.length / limit;
		if ((array.length % limit) != 0) {
			count++;
		}
		for (int i = 0; i < count; i++) {
			list.add(Arrays.copyOfRange(array, copied, Math.min(array.length, copied + limit)));
			copied+= limit;
		}
		return list;
	}

	public static <T> T getJavaPropertyValue(String property, T defaultValue, Converter<String, T> converter) {
		try {
			String value = System.getProperty(property);
			if (value != null) {
				return converter.convert(value);
			}
		} catch (Throwable t) {
		}
		return defaultValue;
	}

	public static interface Converter<T, R> {
		public static final Converter<String, Integer> STRING_TO_INT = new Converter<String, Integer>() {
			@Override
			public Integer convert(String t) {
				return Integer.parseInt(t);
			}
		};
		public static final Converter<String, Boolean> STRING_TO_BOOLEAN = new Converter<String, Boolean>() {
			@Override
			public Boolean convert(String t) {
				return Boolean.getBoolean(t);
			}
		};
		public R convert(T t);
	}

}
