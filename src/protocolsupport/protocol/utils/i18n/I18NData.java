package protocolsupport.protocol.utils.i18n;

import java.io.BufferedReader;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import protocolsupport.utils.Utils;

public class I18NData {

	public static final String DEFAULT_LOCALE = "en_us";

	private static final Set<String> available;
	private static final HashMap<String, I18N> i18ns = new HashMap<>();
	private static final ReadWriteLock lock = new ReentrantReadWriteLock();

	static {
		available = Collections.unmodifiableSet(Utils.getResource("i18n/languages").lines().collect(Collectors.toSet()));
		// load the default locale, we'll need it
		loadI18N(DEFAULT_LOCALE);
	}

	public static I18N loadI18N(String locale) {
		return loadAndRegisterI18N(locale, Utils.getResource("i18n/" + locale + ".lang"));
	}

	public static I18N loadAndRegisterI18N(String locale, BufferedReader stream) {
		lock.writeLock().lock();
		try {
			I18N i18N = new I18N(locale, stream.lines().collect(Collectors.toList()));
			i18ns.put(locale, i18N);
			return i18N;
		} finally {
			lock.writeLock().unlock();
		}
	}

	private static I18N getDefaultI18N() {
		lock.readLock().lock();
		try {
			return i18ns.get(DEFAULT_LOCALE);
		} finally {
			lock.readLock().unlock();
		}
	}

	public static I18N getI18N(String locale) {
		lock.readLock().lock();
		try {
			I18N i18N = i18ns.get(locale);
			if (i18N != null) {
				return i18N;
			}
		} finally {
			lock.readLock().unlock();
		}

		if (available.contains(locale)) {
			// Load this locale.
			return loadI18N(locale);
		}

		return getDefaultI18N();
	}

	public static String getTranslationString(String locale, String key) {
		String tlstring = getI18N(locale).getTranslationString(key);
		if (tlstring != null) {
			return tlstring;
		}
		String deftlstring = getDefaultI18N().getTranslationString(key);
		if (deftlstring != null) {
			return deftlstring;
		}
		return MessageFormat.format("Unknown translation key: {0}", key);
	}

	public static String translate(String locale, String key, String... args) {
		return String.format(getTranslationString(locale, key), (Object[]) args);
	}

}
