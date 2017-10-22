package protocolsupport.api;

import protocolsupport.zplatform.ServerPlatform;

public enum ServerPlatformIdentifier {

	SPIGOT("Spigot"),
	PAPER("Paper"),
	GLOWSTONE("GlowStone");

	/**
	 * Returns current platform identifier
	 * @return current platform identifier
	 */
	public static ServerPlatformIdentifier get() {
		return ServerPlatform.get().getIdentifier();
	}

	private final String name;
	private ServerPlatformIdentifier(String name) {
		this.name = name;
	}

	/**
	 * Returns user friendly server platform name <br>
	 * This name can change, so it shouldn't be used as a key anywhere
	 * @return user friendly server platform name
	 */
	public String getName() {
		return name;
	}

}
