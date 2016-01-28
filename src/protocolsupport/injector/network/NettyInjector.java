package protocolsupport.injector.network;

import org.spigotmc.SpigotConfig;

public class NettyInjector {

	public static void inject() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		if (!SpigotConfig.lateBind) {
			BasicInjector.inject();
		} else {
			NonBlockingServerConnection.inject();
		}
	}

}
