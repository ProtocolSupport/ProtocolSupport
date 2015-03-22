package protocolsupport.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;

public class CommandHandler implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("protocolsupport.admin")) {
			sender.sendMessage(ChatColor.RED + "No permission");
			return true;
		}
		if ((args.length == 1) && args[0].equalsIgnoreCase("list")) {
			sender.sendMessage(ChatColor.GOLD+"[1.8]: "+ChatColor.GREEN+getPlayersStringForProtocol(ProtocolVersion.MINECRAFT_1_8));
			sender.sendMessage(ChatColor.GOLD+"[1.7.10]: "+ChatColor.GREEN+getPlayersStringForProtocol(ProtocolVersion.MINECRAFT_1_7_10));
			sender.sendMessage(ChatColor.GOLD+"[1.7.5]: "+ChatColor.GREEN+getPlayersStringForProtocol(ProtocolVersion.MINECRAFT_1_7_5));
			sender.sendMessage(ChatColor.GOLD+"[1.6.4]: "+ChatColor.GREEN+getPlayersStringForProtocol(ProtocolVersion.MINECRAFT_1_6_4));
			sender.sendMessage(ChatColor.GOLD+"[1.6.2]: "+ChatColor.GREEN+getPlayersStringForProtocol(ProtocolVersion.MINECRAFT_1_6_2));
			sender.sendMessage(ChatColor.GOLD+"[1.5.2]: "+ChatColor.GREEN+getPlayersStringForProtocol(ProtocolVersion.MINECRAFT_1_5_2));
			return true;
		}
		return false;
	}

	private String getPlayersStringForProtocol(ProtocolVersion version) {
		StringBuilder sb = new StringBuilder();
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (ProtocolSupportAPI.getProtocolVersion(player) == version) {
				sb.append(player.getName());
				sb.append(", ");
			}
		}
		if (sb.length() > 2) {
			sb.delete(sb.length() - 2, sb.length());
		}
		return sb.toString();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if ("list".startsWith(args[0])) {
			return Collections.singletonList("list");
		}
		return Collections.emptyList();
	}

}
