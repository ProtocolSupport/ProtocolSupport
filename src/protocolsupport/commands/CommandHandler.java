package protocolsupport.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R2.MinecraftServer;
import net.minecraft.server.v1_9_R2.PropertyManager;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.chat.modifiers.Modifier;
import protocolsupport.api.tab.TabAPI;
import protocolsupport.api.title.TitleAPI;

public class CommandHandler implements CommandExecutor, TabCompleter {

	private static final String DEBUG_PROPERTY = "debug";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("protocolsupport.admin")) {
			sender.sendMessage(ChatColor.RED + "You have no power here!");
			return true;
		}
		if ((args.length == 1) && args[0].equalsIgnoreCase("list")) {
			for (ProtocolVersion version : ProtocolVersion.values()) {
				if (version.getName() != null) {
					sender.sendMessage(ChatColor.GOLD+"["+version.getName()+"]: "+ChatColor.GREEN+getPlayersStringForProtocol(version));
				}
			}
			return true;
		}
		if (args.length == 1 && args[0].equalsIgnoreCase("debug")) {
			@SuppressWarnings("deprecation")
			PropertyManager manager = MinecraftServer.getServer().getPropertyManager();
			if (!manager.getBoolean(DEBUG_PROPERTY, false)) {
				manager.setProperty(DEBUG_PROPERTY, Boolean.TRUE);
				sender.sendMessage(ChatColor.GOLD + "Enabled debug");
			} else {
				manager.setProperty(DEBUG_PROPERTY, Boolean.FALSE);
				sender.sendMessage(ChatColor.GOLD + "Disabled debug");
			}
			return true;
		}
		if (args.length == 1 && args[0].equalsIgnoreCase("testtitle")) {
			TextComponent title = new TextComponent("Test title");
			Modifier titlemodifier = new Modifier();
			titlemodifier.setColor(ChatColor.AQUA);
			titlemodifier.setBold(true);
			title.setModifier(titlemodifier);
			TextComponent subtitle = new TextComponent("Test subtitle");
			Modifier subtitlemodifier = new Modifier();
			subtitlemodifier.setColor(ChatColor.BLUE);
			subtitlemodifier.setUnderlined(true);
			subtitle.setModifier(subtitlemodifier);
			for (Player player : Bukkit.getOnlinePlayers()) {
				TitleAPI.sendSimpleTitle(player, title, subtitle, 20, 60, 20);
			}
			return true;
		}
		if (args.length == 1 && args[0].equalsIgnoreCase("testtab")) {
			TextComponent header = new TextComponent("Test header");
			Modifier headermodifier = new Modifier();
			headermodifier.setColor(ChatColor.AQUA);
			headermodifier.setBold(true);
			header.setModifier(headermodifier);
			TextComponent footer = new TextComponent("Test footer");
			Modifier footermodifier = new Modifier();
			footermodifier.setColor(ChatColor.BLUE);
			footermodifier.setUnderlined(true);
			footer.setModifier(footermodifier);
			for (Player player : Bukkit.getOnlinePlayers()) {
				TabAPI.sendHeaderFooter(player, header, footer);
			}
			return true;
		}
		return false;
	}

	private String getPlayersStringForProtocol(ProtocolVersion version) {
		StringBuilder sb = new StringBuilder();
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (ProtocolSupportAPI.getProtocolVersion(player) == version) {
				sb.append(player.getName());
				sb.append(", ");
			}
		}
		if (sb.length() > 2) {
			sb.delete(sb.length() - 2, sb.length());
		}
		return sb.toString();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		ArrayList<String> completions = new ArrayList<String>();
		if ("list".startsWith(args[0])) {
			completions.add("list");
		}
		if ("debug".startsWith(args[0])) {
			completions.add("debug");
		}
		if ("testtitle".startsWith(args[0])) {
			completions.add("testtitle");
		}
		if ("testtab".startsWith(args[0])) {
			completions.add("testtab");
		}
		return completions;
	}

}
