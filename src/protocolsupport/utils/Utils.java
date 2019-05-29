package protocolsupport.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.IntStream;

import com.google.gson.Gson;

public class Utils {

	public static final Gson GSON = new Gson();

	public static String toStringAllFields(Object obj) {
		StringJoiner joiner = new StringJoiner(", ");
		Class<?> clazz = obj.getClass();
		do {
			try {
				for (Field field : clazz.getDeclaredFields()) {
					if (!Modifier.isStatic(field.getModifiers())) {
						ReflectionUtils.setAccessible(field);
						Object value = field.get(obj);
						if ((value == null) || !value.getClass().isArray()) {
							joiner.add(field.getName() + ": " + Objects.toString(value));
						} else {
							joiner.add(field.getName() + ": " + Arrays.deepToString(new Object[] {value}));
						}
					}
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Unable to get object fields values", e);
			}
		} while ((clazz = clazz.getSuperclass()) != null);
		return obj.getClass().getName() + "(" + joiner.toString() + ")";
	}

	public static <T> T getFromArrayOrNull(T[] array, int index) {
		if ((index >= 0) && (index < array.length)) {
			return array[index];
		} else {
			return null;
		}
	}

	public static String clampString(String string, int limit) {
		return string.substring(0, string.length() > limit ? limit : string.length());
	}

	public static List<int[]> splitArray(int[] array, int limit) {
		List<int[]> list = new ArrayList<>();
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

	public static int ceilToBase(int number, int base) {
		if (base == 0) {
			return 0;
		}
		if (number == 0) {
			return base;
		}
		if (number < 0) {
			base *= -1;
		}
		final int mod = number % base;
		if (mod == 0) {
			return number;
		}
		return (number + base) - mod;
	}

	public static int shortDegree(int number, int system) {
		if (number <= (system/-2)) { number += system; }
		if (number > (system/2)) { number -= system; }
		return number;
	}

	public static void repeat(int count, Runnable action) {
		IntStream.range(0, count).forEach(i -> action.run());
	}

	public static boolean isBitSet(int bitmask, int bit) {
		return ((bitmask & (1 << bit)) != 0);
	}

}
