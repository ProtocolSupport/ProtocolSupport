package protocolsupport.zplatform;

import org.spigotmc.SpigotConfig;

import net.minecraft.server.v1_11_R1.NetworkManager;
import protocolsupport.zplatform.impl.spigot.SpigotPacketFactory;
import protocolsupport.zplatform.impl.spigot.SpigotMiscUtils;
import protocolsupport.zplatform.impl.spigot.injector.SpigotPlatformInjector;

public enum ServerPlatform {

	SPIGOT(new SpigotPlatformInjector(), new SpigotMiscUtils(), new SpigotPacketFactory()),
	GLOWSTONE(null, null, null),
	UNKNOWN(null, null, null);

	private static ServerPlatform current;

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

	public static ServerPlatform get() {
		if (current == null) {
			throw new IllegalStateException("Access to implementation before detect");
		}
		return current;
	}

	private final PlatformInjector injector;
	private final PlatformUtils miscutils;
	private final PlatformPacketFactory packetfactory;
	ServerPlatform(PlatformInjector injector, PlatformUtils miscutils, PlatformPacketFactory packetfactory) {
		this.injector = injector;
		this.miscutils = miscutils;
		this.packetfactory = packetfactory;
	}

	public void inject() {
		injector.inject();
	}

	public PlatformUtils getMiscUtils() {
		return miscutils;
	}

	public PlatformPacketFactory getPacketFactory() {
		return packetfactory;
	}

}
