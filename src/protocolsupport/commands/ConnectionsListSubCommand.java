package protocolsupport.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;

public class ConnectionsListSubCommand implements SubCommand {

	@Override
	public int getMinArgs() {
		return 0;
	}

	@Override
	public boolean handle(CommandSender sender, String[] args) {
		for (Connection connection : ProtocolSupportAPI.getConnections()) {
			sender.sendMessage(ChatColor.YELLOW + connection.toString());
		}
		return true;
	}

	@Override
	public String getHelp() {
		return "prints all currently active connections";
	}

}
