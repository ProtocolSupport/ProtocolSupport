package protocolsupport.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Utils {

	private Utils() {
	}

	public static @Nonnull String toStringAllFields(@Nonnull Object obj) {
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
				throw new UncheckedReflectionException("Unable to get object fields values", e);
			}
		} while ((clazz = clazz.getSuperclass()) != null);
		return obj.getClass().getName() + "(" + joiner.toString() + ")";
	}

	public static @Nullable <T> T getFromArrayOrNull(@Nonnull T[] array, @Nonnegative int index) {
		if ((index >= 0) && (index < array.length)) {
			return array[index];
		} else {
			return null;
		}
	}

	public static @Nonnull String clampString(@Nonnull String string, @Nonnegative int limit) {
		return string.substring(0, string.length() > limit ? limit : string.length());
	}

	public static @Nonnull <T> ArrayList<T> createSingletonArrayList(@Nullable T element) {
		ArrayList<T> list = new ArrayList<>(1);
		list.add(element);
		return list;
	}

	public static int ceilToBase(@Nonnegative int number, @Nonnegative int base) {
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

	public static final long currentTimeMillisFromNanoTime() {
		return TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
	}

}
