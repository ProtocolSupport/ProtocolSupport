package protocolsupport.zplatform.impl.spigot.injector;

import protocolsupport.zplatform.PlatformInjector;
import protocolsupport.zplatform.impl.spigot.block.SpigotBlocksBoundsAdjust;
import protocolsupport.zplatform.impl.spigot.injector.network.SpigotNettyInjector;

public class SpigotPlatformInjector implements PlatformInjector {

	@Override
	public void onLoad() {
		try {
			SpigotBlocksBoundsAdjust.inject();
			SpigotNettyInjector.inject();
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			throw new RuntimeException("Error while injecting", e);
		}
	}

	@Override
	public void onEnable() {
//TODO: get rid of the injector completely and switch to caching entity positions
//		Bukkit.getPluginManager().registerEvents(new SpigotEntityTrackerInjector(), ProtocolSupport.getInstance());
	}

	@Override
	public void onDisable() {
	}

}
