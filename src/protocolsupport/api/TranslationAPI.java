package protocolsupport.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import protocolsupport.protocol.utils.i18n.I18NData;

public class TranslationAPI {

	/**
	 * Registers translation in translation service
	 * @param locale locale
	 * @param langistream inputstream of .lang file
	 */
	public void registerTranslation(String locale, InputStream langistream) {
		I18NData.loadAndRegisterI18N(locale, new BufferedReader(new InputStreamReader(langistream, StandardCharsets.UTF_8)));
	}

	/**
	 * Returns all possible translation keys copy
	 * @return all possible translation keys copy
	 */
	public static Set<String> getTranslationKeys() {
		return I18NData.getI18N(I18NData.DEFAULT_LOCALE).getKeys();
	}

	/**
	 * Translates key using given locale and arguments
	 * @param locale locale
	 * @param key translation key
	 * @param args translation arguments
	 * @return translation
	 */
	public static String translate(String locale, String key, String... args) {
		return I18NData.translate(locale, key, args);
	}

	/**
	 * Returns the translation string for given locale and translation key
	 * @param locale locale
	 * @param key translation key
	 * @return translation string
	 */
	public static String getTranslationString(String locale, String key) {
		return I18NData.getTranslationString(locale, key);
	}

}
