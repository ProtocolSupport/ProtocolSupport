package protocolsupport.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import javax.annotation.Nonnull;

import protocolsupport.protocol.utils.i18n.I18NData;

public class TranslationAPI {

	private static boolean builtInLoaderEnabled = true;

	/**
	 * Returns true if built-in locale loader in enabled <br>
	 * Built-in locale loader loads translation on player locale change event <br>
	 * @return true if built in locale loader in enabled
	 */
	public static boolean isBuiltInLoaderEnabled() {
		return builtInLoaderEnabled;
	}

	/**
	 * Disables built-in locale loader
	 */
	public static void disableBuiltInLoader() {
		builtInLoaderEnabled = false;
	}

	/**
	 * Enables built-in locale loader
	 */
	public static void enableBuiltInLoader() {
		builtInLoaderEnabled = true;
	}


	/**
	 * Returns built-in translations locales
	 * @return built-in translations locales
	 */
	public static @Nonnull Set<String> getBuiltInTranslations() {
		return I18NData.getBuiltInLocales();
	}

	/**
	 * Loads and registers built-in translation
	 * @param locale locale
	 * @deprecated use {@link TranslationAPI#loadBuiltInTranslation(String)}}
	 */
	@Deprecated
	public static void loadBuiltTranslation(@Nonnull String locale) {
		I18NData.loadBuiltInI18N(locale);
	}

	/**
	 * Loads and registers built-in translation
	 * @param locale locale
	 */
	public static void loadBuiltInTranslation(@Nonnull String locale) {
		I18NData.loadBuiltInI18N(locale);
	}

	/**
	 * Returns true if locale has a registered translation
	 * @param locale locale
	 * @return true if locale has a registered translation
	 */
	public static boolean isTranslationRegistered(@Nonnull String locale) {
		return I18NData.isI18NLoaded(locale);
	}

	/**
	 * Unregisters translation for locale from translation service
	 * @param locale locale
	 */
	public static void unregisterTranslation(@Nonnull String locale) {
		I18NData.unloadI18N(locale);
	}

	/**
	 * Registers translation in translation service
	 * @param locale locale
	 * @param langistream inputstream of language .json file
	 */
	public void registerTranslation(@Nonnull String locale, @Nonnull InputStream langistream) {
		I18NData.loadAndRegisterI18N(locale, new BufferedReader(new InputStreamReader(langistream, StandardCharsets.UTF_8)));
	}

	/**
	 * Returns all possible translation keys copy
	 * @return all possible translation keys copy
	 */
	public static @Nonnull Set<String> getTranslationKeys() {
		return I18NData.getI18N(I18NData.DEFAULT_LOCALE).getKeys();
	}

	/**
	 * Translates key using given locale and arguments
	 * @param locale locale
	 * @param key translation key
	 * @param args translation arguments
	 * @return translation
	 */
	public static @Nonnull String translate(@Nonnull String locale, @Nonnull String key, @Nonnull String... args) {
		return I18NData.translate(locale, key, args);
	}

	/**
	 * Returns the translation string for given locale and translation key
	 * @param locale locale
	 * @param key translation key
	 * @return translation string
	 */
	public static @Nonnull String getTranslationString(@Nonnull String locale, @Nonnull String key) {
		return I18NData.getTranslationString(locale, key);
	}

}
