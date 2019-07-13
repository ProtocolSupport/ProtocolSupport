package protocolsupport.utils;

import java.util.function.Function;

public class JavaSystemProperty {

	public static <T> T getValue(String property, T defaultValue, Function<String, T> converter) {
		return JavaSystemProperty.getRawValue("protocolsupport." + property, defaultValue, converter);
	}

	public static <T> T getRawValue(String property, T defaultValue, Function<String, T> converter) {
		try {
			String value = System.getProperty(property);
			if (value != null) {
				return converter.apply(value);
			}
		} catch (Throwable t) {
		}
		return defaultValue;
	}

}
