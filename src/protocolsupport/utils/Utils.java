package protocolsupport.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import protocolsupport.ProtocolSupport;

public class Utils {

	public static <K, V> V getOrCreateDefault(Map<K, V> map, K key, V defaultValue) {
		if (map.containsKey(key)) {
			return map.get(key);
		} else {
			map.put(key, defaultValue);
			return defaultValue;
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

	public static <T> T getJavaPropertyValue(String property, T defaultValue, Converter<String, T> converter) {
		return getRawJavaPropertyValue("protocolsupport."+property, defaultValue, converter);
	}

	public static <T> T getRawJavaPropertyValue(String property, T defaultValue, Converter<String, T> converter) {
		try {
			String value = System.getProperty(property);
			if (value != null) {
				return converter.convert(value);
			}
		} catch (Throwable t) {
		}
		return defaultValue;
	}

	@FunctionalInterface
	public static interface Converter<T, R> {
		public static final Converter<String, Integer> STRING_TO_INT = Integer::parseInt;
		public static final Converter<String, Long> STRING_TO_LONG = Long::parseLong;
		public static final Converter<String, Boolean> STRING_TO_BOOLEAN = Boolean::parseBoolean;
		public static final Converter<String, String> NONE = t -> t;
		public R convert(T t);
	}

	public static boolean isTrue(Boolean b) {
		return (b != null) && b;
	}

	private static final String resourcesDirName = "resources";
	public static InputStream getResource(String name) {
		return ProtocolSupport.class.getClassLoader().getResourceAsStream(resourcesDirName + "/" + name);
	}

}
