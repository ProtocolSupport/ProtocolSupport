package protocolsupport.listeners;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ReloadCommandBlocker implements Listener {

	private final Set<String> blacklist = new HashSet<>();

	public ReloadCommandBlocker() {
		blacklist.add("/reload");
		blacklist.add("/reload confirm");
		blacklist.add("/rl");
		blacklist.add("/rl confirm");
		blacklist.add("/bukkit:reload");
		blacklist.add("/bukkit:reload confirm");
		blacklist.add("/bukkit:rl");
		blacklist.add("/bukkit:rl confirm");
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		if (event.getPlayer().hasPermission("bukkit.command.reload") && blacklist.contains(event.getMessage().toLowerCase())) {
			event.setCancelled(true);
			event.getPlayer().sendMessage("The reload command has been disabled by ProtocolSupport");
		}
	}

}
