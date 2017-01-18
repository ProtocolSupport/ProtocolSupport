package protocolsupport.zplatform.impl.spigot.injector;

import protocolsupport.zplatform.impl.spigot.block.BlockWaterLilyBoundsFixer;

public class ServerInjector {

	public static void inject() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		BlockWaterLilyBoundsFixer.inject();
	}

}
