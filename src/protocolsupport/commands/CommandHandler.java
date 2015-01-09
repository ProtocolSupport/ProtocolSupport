package protocolsupport.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import protocolsupport.protocol.ProtocolVersion;
import protocolsupport.utils.Utils;

public class CommandHandler implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("protocolsupport.admin")) {
			sender.sendMessage(ChatColor.RED + "No permission");
			return true;
		}
		if ((args.length == 1) && args[0].equalsIgnoreCase("list")) {
			sender.sendMessage(ChatColor.GOLD+"[1.8]: "+getPlayersStringForProtocol(ProtocolVersion.MINECRAFT_1_8));
			sender.sendMessage(ChatColor.GOLD+"[1.7.10]: "+getPlayersStringForProtocol(ProtocolVersion.MINECRAFT_1_7_10));
			sender.sendMessage(ChatColor.GOLD+"[1.7.5]: "+getPlayersStringForProtocol(ProtocolVersion.MINECRAFT_1_7_5));
			sender.sendMessage(ChatColor.GOLD+"[1.6.4]: "+getPlayersStringForProtocol(ProtocolVersion.MINECRAFT_1_6_4));
			sender.sendMessage(ChatColor.GOLD+"[1.6.2]: "+getPlayersStringForProtocol(ProtocolVersion.MINECRAFT_1_6_2));
			sender.sendMessage(ChatColor.GOLD+"[1.5.2]: "+getPlayersStringForProtocol(ProtocolVersion.MINECRAFT_1_5_2));
			return true;
		}
		if ((args.length == 2) && args[0].equalsIgnoreCase("entity")) {
			sender.sendMessage(ChatColor.GOLD+"Entity: "+CraftPlayer.class.cast((sender)).getHandle().world.a(Integer.parseInt(args[1])).toString());
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	private String getPlayersStringForProtocol(ProtocolVersion version) {
		StringBuilder sb = new StringBuilder();
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (Utils.getVersion(((CraftPlayer)player).getHandle()) == version) {
				sb.append(player.getName());
				sb.append(", ");
			}
		}
		return sb.toString();
	}

}
