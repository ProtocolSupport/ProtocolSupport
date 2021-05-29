package protocolsupport.protocol.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.NamespacedKey;

public class NamespacedKeyUtils {

	private NamespacedKeyUtils() {
	}

	public static final String SEPARATOR = ":";

	public static @Nonnull String combine(@Nonnull String namespace, @Nonnull String key) {
		return namespace + SEPARATOR + key;
	}

	@SuppressWarnings("deprecation")
	public static @Nullable NamespacedKey fromString(@Nullable String s) {
		if ((s == null) || s.isEmpty()) {
			return null;
		}
		String[] split = s.split(SEPARATOR, 2);
		if (split.length == 1) {
			return NamespacedKey.minecraft(split[0]);
		} else {
			return new NamespacedKey(split[0], split[1]);
		}
	}

}
