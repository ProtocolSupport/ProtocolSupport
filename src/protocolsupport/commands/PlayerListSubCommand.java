package protocolsupport.commands;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;

public class PlayerListSubCommand implements SubCommand {

	@Override
	public int getMinArgs() {
		return 0;
	}

	@Override
	public boolean handle(CommandSender sender, String[] args) {
		boolean verbose = (args.length == 1) && (args[0].equalsIgnoreCase("v") || args[0].equalsIgnoreCase("verbose"));
		sender.sendMessage(ChatColor.YELLOW + "ProtocolSupport Players:");
		for (ProtocolVersion version : ProtocolVersion.getAllSupported()) {
			List<String> players = Bukkit.getOnlinePlayers().stream()
			.filter(player -> ProtocolSupportAPI.getProtocolVersion(player) == version)
			.map(player -> player.getName())
			.collect(Collectors.toList());
			if (!players.isEmpty() || verbose) {
				sender.sendMessage(ChatColor.YELLOW + "[" + version.getName() + "]: " + ChatColor.GREEN + String.join(", ", players));
			}
		}
		if (!verbose) {
			sender.sendMessage(ChatColor.YELLOW + "List all compatible versions using by adding verbose or v argument to this command");
		}
		return true;
	}

	@Override
	public String getHelp() {
		return "prints online players protocol versions";
	}

}
