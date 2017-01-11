package protocolsupport.injector;

import protocolsupport.server.block.BlockWaterLilyBoundsFixer;

public class ServerInjector {

	public static void inject() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		BlockWaterLilyBoundsFixer.inject();
	}

}
