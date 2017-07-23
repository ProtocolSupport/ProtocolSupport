package protocolsupport.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import protocolsupport.zplatform.ServerPlatform;

public class DebugSubCommand implements SubCommand {

	@Override
	public int getMinArgs() {
		return 0;
	}

	@Override
	public boolean handle(CommandSender sender, String[] args) {
		if (ServerPlatform.get().getMiscUtils().isDebugging()) {
			ServerPlatform.get().getMiscUtils().disableDebug();
			sender.sendMessage(ChatColor.YELLOW + "Disabled debug");
		} else {
			ServerPlatform.get().getMiscUtils().enableDebug();
			sender.sendMessage(ChatColor.YELLOW + "Enabled debug");
		}
		return true;
	}

	@Override
	public String getHelp() {
		return "enables/disables debug";
	}

}
