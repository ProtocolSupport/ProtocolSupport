package protocolsupport;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;

import protocolsupport.commands.CommandHandler;
import protocolsupport.injector.NettyInjector;
import protocolsupport.injector.ServerInjector;
import protocolsupport.injector.protocollib.ProtocolLibFixer;

public class ProtocolSupport extends JavaPlugin implements Listener {

	@Override
	public void onLoad() {
		try {
			NettyInjector.inject();
			ServerInjector.inject();
			ProtocolLibFixer.init();
		} catch (Throwable t) {
			t.printStackTrace();
			Bukkit.shutdown();
		}
	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("protocolsupport").setExecutor(new CommandHandler());
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onPluginDisable(PluginDisableEvent event) {
		if (event.getPlugin().getName().equals("ProtocolLib")) {
			System.err.println("[ProtocolSupport] ProtocolLib was unloaded, shutting down");
			Bukkit.shutdown();
		}
	}

	@Override
	public void onDisable() {
		Bukkit.shutdown();
	}

}
