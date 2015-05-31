package protocolsupport;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import protocolsupport.commands.CommandHandler;
import protocolsupport.injector.NettyInjector;
import protocolsupport.injector.ServerInjector;
import protocolsupport.server.listeners.PlayerListener;

public class ProtocolSupport extends JavaPlugin {

	@Override
	public void onLoad() {
		try {
			NettyInjector.inject();
			ServerInjector.inject();
		} catch (Throwable t) {
			t.printStackTrace();
			Bukkit.shutdown();
		}
	}

	@Override
	public void onEnable() {
		getCommand("protocolsupport").setExecutor(new CommandHandler());
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
	}

	@Override
	public void onDisable() {
		Bukkit.shutdown();
	}

}
