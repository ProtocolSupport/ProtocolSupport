package protocolsupport.zplatform;

import org.spigotmc.SpigotConfig;

import net.minecraft.server.v1_16_R3.NetworkManager;
import protocolsupport.api.ServerPlatformIdentifier;
import protocolsupport.zplatform.impl.spigot.SpigotMiscUtils;
import protocolsupport.zplatform.impl.spigot.SpigotPacketFactory;
import protocolsupport.zplatform.impl.spigot.injector.SpigotPlatformInjector;

public class ServerPlatform {

	private static ServerPlatform current;

	public static void detect() {
		if (current != null) {
			throw new IllegalStateException("Implementation already detected");
		}
		UnsupportedOperationException e = new UnsupportedOperationException("No supported platform detected");
		try {
			NetworkManager.class.getDeclaredFields();
			SpigotConfig.class.getDeclaredFields();
			current = new ServerPlatform(ServerPlatformIdentifier.SPIGOT, new SpigotPlatformInjector(), new SpigotMiscUtils(), new SpigotPacketFactory());
			return;
		} catch (Throwable t) {
			e.addSuppressed(new UnsupportedOperationException("Failed to init spigot platform", t));
		}
		try {
//			GlowServer.class.getDeclaredFields();
//			current = new ServerPlatform(ServerPlatformIdentifier.GLOWSTONE, new GlowstonePlatformInjector(), new GlowStoneMiscUtils(), new GlowStonePacketFactory(), new GlowStoneWrapperFactory());
		} catch (Throwable t) {
			e.addSuppressed(new UnsupportedOperationException("Failed to init glowstone platform", t));
		}
		throw e;
	}

	public static ServerPlatform get() {
		if (current == null) {
			throw new IllegalStateException("Access to implementation before detect");
		}
		return current;
	}

	private final ServerPlatformIdentifier identifier;
	private final PlatformInjector injector;
	private final PlatformUtils utils;
	private final PlatformPacketFactory packetfactory;
	private ServerPlatform(ServerPlatformIdentifier identifier, PlatformInjector injector, PlatformUtils miscutils, PlatformPacketFactory packetfactory) {
		this.identifier = identifier;
		this.injector = injector;
		this.utils = miscutils;
		this.packetfactory = packetfactory;
	}

	public ServerPlatformIdentifier getIdentifier() {
		return identifier;
	}

	public PlatformInjector getInjector() {
		return injector;
	}

	public PlatformUtils getMiscUtils() {
		return utils;
	}

	public PlatformPacketFactory getPacketFactory() {
		return packetfactory;
	}

}
