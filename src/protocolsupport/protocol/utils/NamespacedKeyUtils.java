package protocolsupport.protocol.utils;

import org.bukkit.NamespacedKey;

public class NamespacedKeyUtils {

	public static final String SEPARATOR = ":";

	public static String combine(String namespace, String key) {
		return namespace + SEPARATOR + key;
	}

	@SuppressWarnings("deprecation")
	public static NamespacedKey fromString(String s) {
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
