package protocolsupport.zplatform.impl.spigot.injector;

import protocolsupport.zplatform.PlatformInjector;
import protocolsupport.zplatform.impl.spigot.injector.network.NettyInjector;

public class SpigotPlatformInjector implements PlatformInjector {

	@Override
	public void inject() {
		try {
			ServerInjector.inject();
			NettyInjector.inject();
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			throw new RuntimeException("Error while injecting", e);
		}
	}

}
