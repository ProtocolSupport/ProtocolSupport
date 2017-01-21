package protocolsupport.zplatform;

import org.spigotmc.SpigotConfig;

import net.minecraft.server.v1_11_R1.NetworkManager;
import protocolsupport.zplatform.impl.spigot.SpigotPacketFactory;
import protocolsupport.zplatform.impl.spigot.SpigotWrapperFactory;
import protocolsupport.utils.Utils.LazyLoad;
import protocolsupport.zplatform.impl.spigot.SpigotMiscUtils;
import protocolsupport.zplatform.impl.spigot.injector.SpigotPlatformInjector;

public enum ServerPlatform {

	SPIGOT(() -> new SpigotPlatformInjector(), () -> new SpigotMiscUtils(), () -> new SpigotPacketFactory(), () -> new SpigotWrapperFactory()),
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
		} catch (Throwable t) {
		}
		if (current != null) {
			current.init();
		} else {
			current = UNKNOWN;
		}
	}

	public static ServerPlatform get() {
		if (current == null) {
			throw new IllegalStateException("Access to implementation before detect");
		}
		return current;
	}

	private final LazyLoad<PlatformInjector> lazyInjector;
	private final LazyLoad<PlatformUtils> lazyUtils;
	private final LazyLoad<PlatformPacketFactory> lazyPacketFactory;
	private final LazyLoad<PlatformWrapperFactory> lazyWrapperFactory;
	private PlatformInjector injector;
	private PlatformUtils utils;
	private PlatformPacketFactory packetfactory;
	private PlatformWrapperFactory wrapperfactory;
	ServerPlatform(LazyLoad<PlatformInjector> injector, LazyLoad<PlatformUtils> miscutils, LazyLoad<PlatformPacketFactory> packetfactory, LazyLoad<PlatformWrapperFactory> wrapperfactory) {
		this.lazyInjector = injector;
		this.lazyUtils = miscutils;
		this.lazyPacketFactory = packetfactory;
		this.lazyWrapperFactory = wrapperfactory;
	}

	private void init() {
		injector = lazyInjector.create();
		utils = lazyUtils.create();
		packetfactory = lazyPacketFactory.create();
		wrapperfactory = lazyWrapperFactory.create();
	}

	public void inject() {
		injector.inject();
	}

	public PlatformUtils getMiscUtils() {
		return utils;
	}

	public PlatformPacketFactory getPacketFactory() {
		return packetfactory;
	}

	public PlatformWrapperFactory getWrapperFactory() {
		return wrapperfactory;
	}

}
