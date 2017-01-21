package protocolsupport.zplatform;

import org.spigotmc.SpigotConfig;

import net.minecraft.server.v1_11_R1.NetworkManager;
import protocolsupport.zplatform.impl.spigot.SpigotPacketFactory;
import protocolsupport.zplatform.impl.spigot.SpigotWrapperFactory;
import protocolsupport.zplatform.impl.spigot.SpigotMiscUtils;
import protocolsupport.zplatform.impl.spigot.injector.SpigotPlatformInjector;

public enum ServerPlatform {

	SPIGOT(new SpigotPlatformInjector(), new SpigotMiscUtils(), new SpigotPacketFactory(), new SpigotWrapperFactory()),
	GLOWSTONE(null, null, null, null), //TODO: implement for GlowStone
	UNKNOWN(null, null, null, null);

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
	private final PlatformWrapperFactory wrapperfactory;
	ServerPlatform(PlatformInjector injector, PlatformUtils miscutils, PlatformPacketFactory packetfactory, PlatformWrapperFactory wrapperfactory) {
		this.injector = injector;
		this.miscutils = miscutils;
		this.packetfactory = packetfactory;
		this.wrapperfactory = wrapperfactory;
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

	public PlatformWrapperFactory getWrapperFactory() {
		return wrapperfactory;
	}

}
