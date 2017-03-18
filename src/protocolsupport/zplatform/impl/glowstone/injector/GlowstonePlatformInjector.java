package protocolsupport.zplatform.impl.glowstone.injector;

import protocolsupport.zplatform.PlatformInjector;
import protocolsupport.zplatform.impl.glowstone.injector.packets.GlowStonePacketsInjector;

public class GlowstonePlatformInjector implements PlatformInjector {

	@Override
	public void inject() {
		try {
			GlowStonePacketsInjector.inject();
			GlowStoneNettyInjector.inject();
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException | InstantiationException e) {
			throw new RuntimeException("Error while injecting", e);
		}
	}

	@Override
	public void injectOnEnable() {
	}

}
