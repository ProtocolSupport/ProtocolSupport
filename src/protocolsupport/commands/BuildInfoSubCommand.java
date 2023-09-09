package protocolsupport.commands;

import org.bukkit.command.CommandSender;

import protocolsupport.ProtocolSupport;
import protocolsupport.api.chat.ChatFormat;

public class BuildInfoSubCommand implements SubCommand {

	@Override
	public int getMinArgs() {
		return 0;
	}

	@Override
	public boolean handle(CommandSender sender, String[] args) {
		sender.sendMessage(ChatFormat.YELLOW.toStyle() + ProtocolSupport.getInstance().getBuildInfo());
		return true;
	}

	@Override
	public String getHelp() {
		return "prints ProtocolSupport build info";
	}

}
