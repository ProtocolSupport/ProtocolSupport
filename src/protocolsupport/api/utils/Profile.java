package protocolsupport.api.utils;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.UUID;

import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import protocolsupport.api.events.PlayerProfileCompleteEvent;

/**
 * Client profile (name, uuid, properties) <br>
 * This profile may not be complete and may change until {@link AsyncPlayerPreLoginEvent}
 */
public abstract class Profile {

	/**
	 * Returns offline mode uuid for name
	 * @param name name
	 * @return offline mode uuid
	 */
	public static UUID generateOfflineModeUUID(String name) {
		return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
	}

	protected volatile boolean onlineMode;

	protected volatile String originalname;
	protected volatile UUID originaluuid;

	/**
	 * Returns true if this player logged in using online-mode
	 * @return true if this player logged in using online-mode
	 */
	public boolean isOnlineMode() {
		return onlineMode;
	}

	/**
	 * Returns original name of the player <br>
	 * This name is set from handshake and from online mode query response
	 * @return original name of the player
	 */
	public String getOriginalName() {
		return originalname;
	}

	/**
	 * Returns original uuid of the player <br>
	 * This uuid is set from online mode profile query response or offline mode uuid generation or spoofed data <br>
	 * @return original name of the player
	 */
	public UUID getOriginalUUID() {
		return originaluuid;
	}

	/**
	 * Returns current name <br>
	 * This name can be changed by {@link PlayerProfileCompleteEvent#setForcedName}
	 * @return current name
	 */
	public abstract String getName();

	/**
	 * Returns current uuid <br>
	 * This uuid can be changed by {@link PlayerProfileCompleteEvent#setForcedUUID}
	 * @return current uuid
	 */
	public abstract UUID getUUID();

	/**
	 * Returns current properties <br>
	 * These properties can be changed by {@link PlayerProfileCompleteEvent} property management methods
	 * @return current properties
	 */
	public abstract Set<String> getPropertiesNames();

	/**
	 * Returns current properties <br>
	 * These properties can be changed by {@link PlayerProfileCompleteEvent} property management methods
	 * @param name property name
	 * @return current properties
	 */
	public abstract Set<ProfileProperty> getProperties(String name);

}
