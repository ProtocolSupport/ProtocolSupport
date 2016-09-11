package protocolsupport.injector.network;

import protocolsupport.ProtocolSupport;
import protocolsupport.utils.Utils;

public class NettyInjector {

	public static void inject() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		if (Utils.getServer().ae()) {
			ProtocolSupport.logWarning("Native transport is enabled, this may cause issues. Disable it by setting use-native-transport in server.properties to false.");
		}
		BasicInjector.inject();
	}

}
