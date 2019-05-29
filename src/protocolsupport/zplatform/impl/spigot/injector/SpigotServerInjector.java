package protocolsupport.zplatform.impl.spigot.injector;

import protocolsupport.zplatform.impl.spigot.block.SpigotBlocksBoundsAdjust;

public class SpigotServerInjector {

	public static void inject() throws IllegalAccessException, NoSuchFieldException {
		SpigotBlocksBoundsAdjust.inject();
	}

}
