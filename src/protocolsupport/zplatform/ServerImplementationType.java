package protocolsupport.zplatform;

import org.spigotmc.SpigotConfig;

import net.minecraft.server.v1_11_R1.NetworkManager;

public enum ServerImplementationType {

	SPIGOT, GLOWSTONE, UNKNOWN;

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

}
