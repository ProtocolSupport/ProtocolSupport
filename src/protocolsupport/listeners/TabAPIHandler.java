package protocolsupport.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import protocolsupport.ProtocolSupport;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.tab.TabAPI;

public class TabAPIHandler implements Listener {

	@EventHandler
	public void onJoin(final PlayerJoinEvent event) {
		Bukkit.getScheduler().runTaskLater(ProtocolSupport.getInstance(), () -> {
			BaseComponent header = TabAPI.getDefaultHeader();
			BaseComponent footer = TabAPI.getDefaultFooter();
			if ((header != null) || (footer != null)) {
				TabAPI.sendHeaderFooter(event.getPlayer(), header, footer);
			}
		}, 1);
	}

}
