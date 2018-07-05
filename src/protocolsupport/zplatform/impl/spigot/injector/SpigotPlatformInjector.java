package protocolsupport.zplatform.impl.spigot.injector;

import org.bukkit.Bukkit;

import protocolsupport.ProtocolSupport;
import protocolsupport.zplatform.PlatformInjector;
import protocolsupport.zplatform.impl.spigot.entitytracker.SpigotEntityTracker;
import protocolsupport.zplatform.impl.spigot.entitytracker.SpigotEntityTrackerEntry;
import protocolsupport.zplatform.impl.spigot.injector.network.SpigotNettyInjector;

public class SpigotPlatformInjector implements PlatformInjector {

	@Override
	public void onLoad() {
		try {
			Class.forName(SpigotEntityTracker.class.getName());
			Class.forName(SpigotEntityTrackerEntry.class.getName());
			SpigotServerInjector.inject();
			SpigotNettyInjector.inject();
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | ClassNotFoundException e) {
			throw new RuntimeException("Error while injecting", e);
		}
	}

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new SpigotEntityTrackerInjector(), ProtocolSupport.getInstance());
	}

	@Override
	public void onDisable() {
	}

}
