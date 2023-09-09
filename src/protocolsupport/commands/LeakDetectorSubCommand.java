package protocolsupport.commands;

import org.bukkit.command.CommandSender;

import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import protocolsupport.api.chat.ChatFormat;

public class LeakDetectorSubCommand implements SubCommand {

	@Override
	public int getMinArgs() {
		return 0;
	}

	@Override
	public boolean handle(CommandSender sender, String[] args) {
		if (ResourceLeakDetector.isEnabled()) {
			ResourceLeakDetector.setLevel(Level.DISABLED);
			sender.sendMessage(ChatFormat.YELLOW.toStyle() + "Disabled leak detector");
		} else {
			ResourceLeakDetector.setLevel(Level.PARANOID);
			sender.sendMessage(ChatFormat.YELLOW.toStyle() + "Enabled leak detector");
		}
		return true;
	}

	@Override
	public String getHelp() {
		return "enables/disables netty leak detector";
	}

}
