package protocolsupport.protocol.utils.i18n;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class I18N {

	private final HashMap<String, String> i18n = new HashMap<>();

	private final String lang;
	public I18N(String locale, Map<String, String> i18n) {
		this.lang = locale;
		this.i18n.putAll(i18n);
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
