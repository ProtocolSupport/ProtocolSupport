package protocolsupport.zplatform.impl.spigot.injector;

import protocolsupport.zplatform.impl.spigot.block.SpigotBlockWaterLilyBoundsFixer;

public class SpigotServerInjector {

	public static void inject() throws IllegalAccessException, NoSuchFieldException {
		SpigotBlockWaterLilyBoundsFixer.inject();
	}

}
