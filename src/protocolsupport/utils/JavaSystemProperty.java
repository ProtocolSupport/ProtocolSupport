package protocolsupport.utils;

import java.util.function.Function;

import javax.annotation.Nonnull;

public class JavaSystemProperty {

	private JavaSystemProperty() {
	}

	public static @Nonnull <T> T getValue(@Nonnull String property, @Nonnull T defaultValue, @Nonnull Function<String, T> converter) {
		return JavaSystemProperty.getRawValue("protocolsupport." + property, defaultValue, converter);
	}

	public static @Nonnull <T> T getRawValue(@Nonnull String property, @Nonnull T defaultValue, @Nonnull Function<String, T> converter) {
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
