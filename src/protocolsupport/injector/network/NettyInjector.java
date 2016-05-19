package protocolsupport.injector.network;

import net.minecraft.server.v1_9_R2.MinecraftServer;

import protocolsupport.ProtocolSupport;

public class NettyInjector {

	@SuppressWarnings("deprecation")
	public static void inject() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		if (MinecraftServer.getServer().ae()) {
			ProtocolSupport.logWarning("Native transport is enabled, this may cause issues. Disable it by setting use-native-transport in server.properties to false.");
		}
		BasicInjector.inject();
	}

}
