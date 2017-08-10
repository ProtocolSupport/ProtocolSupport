package protocolsupport.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;

import com.google.gson.Gson;

import protocolsupport.ProtocolSupport;

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

	public static <K, V> V getFromMapOrCreateDefault(Map<K, V> map, K key, V defaultValue) {
		return map.computeIfAbsent(key, k -> defaultValue);
	}

	public static <T> T getFromArrayOrNull(T[] array, int index) {
		if ((index >= 0) && (index < array.length)) {
			return array[index];
		} else {
			return null;
		}
	}

	public static String exceptionMessage(Object... strings) {
		StringBuilder msg = new StringBuilder();
		msg.append(strings[0]).append(System.lineSeparator());
		msg.append("Additional exception info:").append(System.lineSeparator());
		for (int i = 1; i < strings.length; i++) {
			msg.append("\t").append(strings[i]).append(System.lineSeparator());
		}
		msg.append("Stacktrace:");
		return msg.toString();
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

	public static <T> T getJavaPropertyValue(String property, T defaultValue, Function<String, T> converter) {
		return getRawJavaPropertyValue("protocolsupport."+property, defaultValue, converter);
	}

	public static <T> T getRawJavaPropertyValue(String property, T defaultValue, Function<String, T> converter) {
		try {
			String value = System.getProperty(property);
			if (value != null) {
				return converter.apply(value);
			}
		} catch (Throwable t) {
		}
		return defaultValue;
	}

	public static boolean isTrue(Boolean b) {
		return (b != null) && b;
	}

	private static final String resourcesDirName = "resources";
	public static BufferedReader getResource(String name) {
		return new BufferedReader(new InputStreamReader(getResourceAsStream(name), StandardCharsets.UTF_8));
	}

	public static InputStream getResourceAsStream(String name) {
		return ProtocolSupport.class.getClassLoader().getResourceAsStream(resourcesDirName + "/" + name);
	}

}
