package protocolsupport.utils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;

public class Utils {

	public static String clampString(String string, int limit) {
		return string.substring(0, string.length() > limit ? limit : string.length());
	}

	public static List<int[]> splitArray(int[] array, int limit) {
		List<int[]> list = new ArrayList<int[]>();
		if (array.length <= limit) {
			list.add(array);
			return list;
		}
		int count = getSplitCount(array.length, limit);
		int copied = 0;
		for (int i = 0; i < count; i++) {
			list.add(Arrays.copyOfRange(array, copied, Math.min(array.length, copied + limit)));
			copied += limit;
		}
		return list;
	}

	public static int getSplitCount(int length, int maxlength) {
		int count = length / maxlength;
		if ((length % maxlength) != 0) {
			count++;
		}
		return count;
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
				return Boolean.parseBoolean(t);
			}
		};
		public R convert(T t);
	}

	public static MethodHandle getFieldSetter(Class<?> classIn, String fieldName) {
		try {
			return MethodHandles
					.lookup()
					.unreflectSetter(ReflectionUtils.setAccessible(classIn.getDeclaredField(fieldName)));
		} catch (Throwable t) {
			t.printStackTrace();
			Bukkit.shutdown();
		}
		return null;
	}

	public static MethodHandle getFieldGetter(Class<?> classIn, String fieldName) {
		try {
			return MethodHandles
					.lookup()
					.unreflectGetter(ReflectionUtils.setAccessible(classIn.getDeclaredField(fieldName)));
		} catch (Throwable t) {
			t.printStackTrace();
			Bukkit.shutdown();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] reverseArray(T[] array) {
		int length = array.length;
		T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), length);
		for (int i = 0; i < length; i++) {
			newArray[i] = array[length - 1 - i];
		}
		return newArray;
	}

	public static int divideAndCeilWithBase(int number, int base) {
		int fp = number / base;
		int m = number % base;
		if (m == 0) {
			return fp;
		} else {
			return fp + 1;
		}
	}
	
	public static boolean isTrue(Boolean b) {
		return b != null && b;
	}

}
