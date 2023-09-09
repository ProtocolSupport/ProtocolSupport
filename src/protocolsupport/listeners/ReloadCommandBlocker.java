package protocolsupport.listeners;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import protocolsupport.api.chat.ChatFormat;

public class ReloadCommandBlocker implements Listener {

	private final Set<String> blacklist = new HashSet<>();

	public ReloadCommandBlocker() {
		blacklist.add("reload");
		blacklist.add("reload confirm");
		blacklist.add("rl");
		blacklist.add("rl confirm");
		blacklist.add("bukkit:reload");
		blacklist.add("bukkit:reload confirm");
		blacklist.add("bukkit:rl");
		blacklist.add("bukkit:rl confirm");
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		String command = event.getMessage().toLowerCase(Locale.ENGLISH);
		if (command.startsWith("/") && blacklist.contains(command.substring(1))) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatFormat.DARK_RED.toStyle() + "The reload command has been disabled by ProtocolSupport");
		}
	}

	@EventHandler
	public void onConsoleCommand(ServerCommandEvent event) {
		String command = event.getCommand().toLowerCase(Locale.ENGLISH);
		if (command.startsWith("/")) {
			command = command.substring(1);
		}
		if (blacklist.contains(command)) {
			event.setCancelled(true);
			event.getSender().sendMessage(ChatFormat.DARK_RED.toStyle() + "The reload command has been disabled by ProtocolSupport");
		}
	}

}
