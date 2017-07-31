package protocolsupport.api;

import java.io.InputStream;
import java.util.Set;

import protocolsupport.protocol.utils.i18n.I18NData;

public class TranslationAPI {

	public void registerTranslation(String locale, InputStream langistream) {
		I18NData.loadAndRegisterI18N(locale, langistream);
	}

	public static Set<String> getTranslationKeys() {
		return I18NData.getI18N(I18NData.DEFAULT_LOCALE).getKeys();
	}

	public static String translate(String locale, String key, String... args) {
		return I18NData.i18n(locale, key, args);
	}

}
