package protocolsupport.api;

import javax.annotation.Nonnull;

import protocolsupport.zplatform.ServerPlatform;

public enum ServerPlatformIdentifier {

	SPIGOT("Spigot"),
	GLOWSTONE("GlowStone");

	/**
	 * Returns current platform identifier
	 * @return current platform identifier
	 */
	public static @Nonnull ServerPlatformIdentifier get() {
		return ServerPlatform.get().getIdentifier();
	}

	private final String name;

	private ServerPlatformIdentifier(@Nonnull String name) {
		this.name = name;
	}

	/**
	 * Returns user friendly server platform name <br>
	 * This name can change, so it shouldn't be used as a key anywhere
	 * @return user friendly server platform name
	 */
	public @Nonnull String getName() {
		return name;
	}

}
