package protocolsupport.protocol.utils;

import org.bukkit.NamespacedKey;

public class NamespacedKeyUtils {

	@SuppressWarnings("deprecation")
	public static NamespacedKey fromString(String s) {
		if (s == null || s.isEmpty()) {
			return null;
		}
		String[] split = s.split("[:]", 2);
		if (split.length == 1) {
			return NamespacedKey.minecraft(split[0]);
		} else {
			return new NamespacedKey(split[0], split[1]);
		}
	}

}
