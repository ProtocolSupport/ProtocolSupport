package protocolsupport.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

public class Utils {

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
		int ceil = (number / base) * base;
		if (number != ceil) {
			ceil += base;
		}
		return ceil;
	}

	public static long getColorDiff(int r1, int r2, int g1, int g2, int b1, int b2) {
		long rmean = (r1 + r2) / 2;
		long r = r1 - r2;
		long g = g1 - g2;
		long b = b1 - b2;
		return (((512 + rmean) * r * r) >> 8) + (4 * g * g) + (((767 - rmean) * b * b) >> 8);
	}

	public static void repeat(int count, Runnable action) {
		for (int i = 0; i < count; i++) {
			action.run();
		}
	}

	public static final long currentTimeMillisFromNanoTime() {
		return TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
	}

}
