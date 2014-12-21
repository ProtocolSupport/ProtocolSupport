package protocolsupport;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import protocolsupport.injector.NettyInjector;
import protocolsupport.injector.ServerInjector;
import protocolsupport.listeners.PlayerListener;

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
	}

	@Override
	public void onDisable() {
		Bukkit.shutdown();
	}

}
