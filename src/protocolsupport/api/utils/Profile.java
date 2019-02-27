package protocolsupport.api.utils;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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

	protected volatile String name;
	protected volatile UUID uuid;
	protected final Map<String, Set<ProfileProperty>> properties = new ConcurrentHashMap<>();

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
	public String getName() {
		return name;
	}

	/**
	 * Returns current uuid <br>
	 * This uuid can be changed by {@link PlayerProfileCompleteEvent#setForcedUUID}
	 * @return current uuid
	 */
	public UUID getUUID() {
		return uuid;
	}

	/**
	 * Returns current properties <br>
	 * These properties can be changed by {@link PlayerProfileCompleteEvent} property management methods
	 * @return current properties
	 */
	public Set<String> getPropertiesNames() {
		return Collections.unmodifiableSet(properties.keySet());
	}

	/**
	 * Returns current properties <br>
	 * These properties can be changed by {@link PlayerProfileCompleteEvent} property management methods
	 * @param name property name
	 * @return current properties
	 */
	public Set<ProfileProperty> getProperties(String name) {
		return Collections.unmodifiableSet(properties.getOrDefault(name, Collections.emptySet()));
	}

}
