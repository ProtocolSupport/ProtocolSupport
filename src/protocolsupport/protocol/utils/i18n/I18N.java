package protocolsupport.protocol.utils.i18n;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;

public class I18N {

	private final HashMap<String, String> i18n = new HashMap<>();

	private final String lang;
	public I18N(String locale) {
		this.lang = locale;
	}

	public String getLang() {
		return lang;
	}

	public void load(List<String> lines) {
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
		String filelang = getI18N("language.code");
		if (filelang == null) {
			throw new IllegalArgumentException("Invalid language: code not found");
		}
		if (!filelang.equalsIgnoreCase(lang)) {
			throw new IllegalArgumentException(MessageFormat.format("Invalid language: expected {0} got {1}", lang, filelang));
		}
	}

	public String getI18N(String key) {
		return i18n.getOrDefault(key, key);
	}

}
