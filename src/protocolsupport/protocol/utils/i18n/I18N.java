package protocolsupport.protocol.utils.i18n;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class I18N {

	private final HashMap<String, String> i18n = new HashMap<>();

	private final String lang;
	public I18N(String locale, List<String> lines) {
		this.lang = locale;
		for (String line : lines) {
			if (line.isEmpty()) {
				continue;
			}
			if (line.startsWith("#")) {
				continue;
			}
			String[] split = line.split("[=]", 2);
			if (split.length != 2) {
				continue;
			}
			i18n.put(split[0], split[1]);
		}
	}

	public String getLang() {
		return lang;
	}

	public Set<String> getKeys() {
		return new HashSet<>(i18n.keySet());
	}

	public String getTranslationString(String key) {
		return i18n.get(key);
	}

}
