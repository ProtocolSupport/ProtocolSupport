package protocolsupport.commands;

import org.bukkit.command.CommandSender;

public interface SubCommand {

	public int getMinArgs();

	public boolean handle(CommandSender sender, String[] args);

	public String getHelp();

}
