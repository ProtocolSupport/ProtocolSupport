package protocolsupport.commands;

import org.bukkit.command.CommandSender;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.chat.ChatFormat;

public class ConnectionsListSubCommand implements SubCommand {

	@Override
	public int getMinArgs() {
		return 0;
	}

	@Override
	public boolean handle(CommandSender sender, String[] args) {
		for (Connection connection : ProtocolSupportAPI.getConnections()) {
			sender.sendMessage(ChatFormat.YELLOW.toString() + connection.toString());
		}
		return true;
	}

	@Override
	public String getHelp() {
		return "prints all currently active connections";
	}

}
