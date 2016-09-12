package protocolsupport.commands;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.ReloadCommand;
import org.bukkit.plugin.PluginManager;
import protocolsupport.utils.ReflectionUtils;

public class ReloadCommandRemover {

	@SuppressWarnings("unchecked")
	public static void remove() {
		try {
			PluginManager pluginmanager = Bukkit.getPluginManager();
			CommandMap commandMap = (CommandMap) ReflectionUtils.getField(pluginmanager.getClass(), "commandMap").get(pluginmanager);
			Collection<Command> commands = (Collection<Command>) ReflectionUtils.getMethod(commandMap.getClass(), "getCommands", 0).invoke(commandMap);
			for (Command cmd : new ArrayList<Command>(commands)) {
				if (cmd instanceof ReloadCommand) {
					removeCommand(commandMap, commands, cmd);
				}
			}
		} catch (Throwable t) {
		}
	}

	private static void removeCommand(CommandMap commandMap, Collection<Command> commands, Command cmd) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		cmd.unregister(commandMap);
		if (commands.getClass().getSimpleName().equals("UnmodifiableCollection")) {
			Field originalField = commands.getClass().getDeclaredField("c");
			originalField.setAccessible(true);
			@SuppressWarnings("unchecked")
			Collection<Command> original = (Collection<Command>) originalField.get(commands);
			original.remove(cmd);
		} else {
			commands.remove(cmd);
		}
	}

}
