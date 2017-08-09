package protocolsupport.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import protocolsupport.protocol.utils.i18n.I18NData;

public class TranslationAPI {

	public void registerTranslation(String locale, InputStream langistream) {
		I18NData.loadAndRegisterI18N(locale, new BufferedReader(new InputStreamReader(langistream, StandardCharsets.UTF_8)));
	}

	public static Set<String> getTranslationKeys() {
		return I18NData.getI18N(I18NData.DEFAULT_LOCALE).getKeys();
	}

	public static String translate(String locale, String key, String... args) {
		return I18NData.translate(locale, key, args);
	}

	public static String getTranslationString(String locale, String key) {
		return I18NData.getTranslationString(locale, key);
	}

}
