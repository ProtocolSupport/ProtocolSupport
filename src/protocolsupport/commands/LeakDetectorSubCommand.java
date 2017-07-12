package protocolsupport.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;

public class LeakDetectorSubCommand implements SubCommand {

	@Override
	public int getMinArgs() {
		return 0;
	}

	@Override
	public boolean handle(CommandSender sender, String[] args) {
		if (ResourceLeakDetector.isEnabled()) {
			ResourceLeakDetector.setLevel(Level.DISABLED);
			sender.sendMessage(ChatColor.YELLOW + "Disabled leak detector");
		} else {
			ResourceLeakDetector.setLevel(Level.PARANOID);
			sender.sendMessage(ChatColor.YELLOW + "Enabled leak detector");
		}
		return true;
	}

	@Override
	public String getHelp() {
		return "enables/disables netty leak detector";
	}

}
