package protocolsupport.commands;

import org.bukkit.command.CommandSender;

import protocolsupport.api.chat.ChatFormat;
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
			sender.sendMessage(ChatFormat.YELLOW.toStyle() + "Disabled debug");
		} else {
			ServerPlatform.get().getMiscUtils().enableDebug();
			sender.sendMessage(ChatFormat.YELLOW.toStyle() + "Enabled debug");
		}
		return true;
	}

	@Override
	public String getHelp() {
		return "enables/disables debug";
	}

}
