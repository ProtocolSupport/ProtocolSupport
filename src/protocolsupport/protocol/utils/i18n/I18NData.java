package protocolsupport.protocol.utils.i18n;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.stream.Collectors;

import protocolsupport.utils.Utils;

public class I18NData {

	public static final String DEFAULT_LOCALE = "en_us";

	private static final HashMap<String, I18N> i18ns = new HashMap<>();
	private static final I18N defaulti18n = loadLocale(DEFAULT_LOCALE);

	private static I18N loadLocale(String lang) {
		I18N i18n = new I18N(lang);
		i18n.load(new BufferedReader(new InputStreamReader(Utils.getResource("i18n/" + lang + ".lang"))).lines().collect(Collectors.toList()));
		return i18n;
	}

	public static String i18n(String lang, String key, String... args) {
		I18N i18n = i18ns.getOrDefault(lang, defaulti18n);
		return String.format(i18n.getI18N(key), (Object[]) args);
	}

}
