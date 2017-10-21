package protocolsupport.zplatform.impl.paper.injector;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import protocolsupport.ProtocolSupport;
import protocolsupport.zplatform.PlatformInjector;
import protocolsupport.zplatform.impl.spigot.injector.SpigotEntityTrackerInjector;
import protocolsupport.zplatform.impl.spigot.injector.SpigotServerInjector;
import protocolsupport.zplatform.impl.spigot.injector.network.SpigotNettyInjector;

public class PaperPlatformInjector implements PlatformInjector {

	@Override
	public void inject() {
		// We are going to reuse the Spigot***Injector, there isn't any differences between Spigot's and Paper's impl here
		try {
			SpigotServerInjector.inject();
			SpigotNettyInjector.inject();
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			throw new RuntimeException("Error while injecting", e);
		}
	}

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new PaperEntityTrackerInjector(), JavaPlugin.getPlugin(ProtocolSupport.class));
	}

	@Override
	public void onDisable() {
	}

}
