package protocolsupport.commands;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor, TabCompleter {

	private final Map<String, SubCommand> subcommands = new LinkedHashMap<>();
	{
		subcommands.put("buildinfo", new BuildInfoSubCommand());
		subcommands.put("debug", new DebugSubCommand());
		subcommands.put("leakdetector", new LeakDetectorSubCommand());
		subcommands.put("list", new PlayerListSubCommand());
		subcommands.put("connections", new ConnectionsListSubCommand());
		subcommands.put("help", new SubCommand() {
			@Override
			public int getMinArgs() {
				return 0;
			}

			@Override
			public boolean handle(CommandSender sender, String[] args) {
				String prepend = sender instanceof Player ? "/" : "";
				for (Entry<String, SubCommand> entry : subcommands.entrySet()) {
					sender.sendMessage(ChatColor.YELLOW + prepend + "ps " + entry.getKey() + " - " + entry.getValue().getHelp());
				}
				return true;
			}

			@Override
			public String getHelp() {
				return "prints help";
			}
		});
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("protocolsupport.admin")) {
			sender.sendMessage(ChatColor.DARK_RED + "You have no power here!");
			return true;
		}
		if (args.length == 0) {
			return false;
		}
		SubCommand subcommand = subcommands.get(args[0]);
		if (subcommand == null) {
			return false;
		}
		String[] subcommandargs = Arrays.copyOfRange(args, 1, args.length);
		if (subcommandargs.length < subcommand.getMinArgs()) {
			sender.sendMessage(ChatColor.DARK_RED + "Not enough args");
			return true;
		}
		return subcommand.handle(sender, subcommandargs);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return subcommands.keySet().stream()
		.filter(subcommand -> subcommand.startsWith(args[0]))
		.collect(Collectors.toList());
	}

}
