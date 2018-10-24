package protocolsupport.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLocaleChangeEvent;

import protocolsupport.ProtocolSupport;
import protocolsupport.api.TranslationAPI;

public class LocaleUseLoader implements Listener {

	@EventHandler
	public void onLocaleChange(PlayerLocaleChangeEvent event) {
		if (TranslationAPI.isBuiltInLoaderEnabled()) {
			String locale = event.getLocale();
			if (TranslationAPI.getBuiltInTranslations().contains(locale) && !TranslationAPI.isTranslationRegistered(locale)) {
				Bukkit.getScheduler().runTaskAsynchronously(ProtocolSupport.getInstance(), () -> TranslationAPI.loadBuiltTranslation(locale));
			}
		}
	}

}
