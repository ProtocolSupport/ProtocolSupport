package protocolsupport;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import protocolsupport.injector.NettyInjector;
import protocolsupport.injector.ServerInjector;
import protocolsupport.listeners.PlayerListener;
import protocolsupport.listeners.WorldListener;

public class ProtocolSupport extends JavaPlugin {

	@Override
	public void onLoad() {
		try {
			NettyInjector.inject();
			ServerInjector.inject();
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			Bukkit.shutdown();
		}
	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		getServer().getPluginManager().registerEvents(new WorldListener(), this);
	}

	@Override
	public void onDisable() {
		Bukkit.shutdown();
	}

}
