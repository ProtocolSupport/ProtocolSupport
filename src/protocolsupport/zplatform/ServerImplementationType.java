package protocolsupport.zplatform;

import org.spigotmc.SpigotConfig;

import net.minecraft.server.v1_11_R1.NetworkManager;
import protocolsupport.zplatform.impl.spigot.injector.SpigotPlatformInjector;

public enum ServerImplementationType {

	SPIGOT(new SpigotPlatformInjector()),
	GLOWSTONE(null),
	UNKNOWN(null);

	private static ServerImplementationType current;

	public static void detect() {
		if (current != null) {
			throw new IllegalStateException("Implementation already detected");
		}
		try {
			NetworkManager.a.getName();
			SpigotConfig.config.contains("test");
			current = SPIGOT;
			return;
		} catch (Throwable t) {
		}
		current = UNKNOWN;
	}

	public static ServerImplementationType get() {
		return current;
	}

	private final PlatformInjector injector;
	ServerImplementationType(PlatformInjector injector) {
		this.injector = injector;
	}

	public void inject() {
		injector.inject();
	}

}
