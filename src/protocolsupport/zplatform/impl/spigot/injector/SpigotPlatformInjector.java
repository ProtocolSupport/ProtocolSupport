package protocolsupport.zplatform.impl.spigot.injector;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import protocolsupport.ProtocolSupport;
import protocolsupport.zplatform.PlatformInjector;
import protocolsupport.zplatform.impl.spigot.injector.network.SpigotNettyInjector;

public class SpigotPlatformInjector implements PlatformInjector {

	@Override
	public void inject() {
		try {
			SpigotServerInjector.inject();
			SpigotNettyInjector.inject();
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			throw new RuntimeException("Error while injecting", e);
		}
	}

	@Override
	public void injectOnEnable() {
		Bukkit.getPluginManager().registerEvents(new SpigotEntityTrackerInjector(), JavaPlugin.getPlugin(ProtocolSupport.class));
	}

}
