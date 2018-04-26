package protocolsupport.api.events;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.utils.Utils;

/**
 * This event is fired when player login finishes (after online-mode processing and uuid generation, but before actual world join)
 * This event is fired only if {@link PlayerLoginStartEvent} has fired for this client
 */
public class PlayerLoginFinishEvent extends PlayerAbstractLoginEvent {

	private final UUID uuid;
	private final boolean onlineMode;

	public PlayerLoginFinishEvent(Connection connection, GameProfile profile, boolean onlineMode) {
		super(connection, profile.getName());
		this.uuid = profile.getUUID();
		this.onlineMode = onlineMode;
		this.properties.putAll(profile.getProperties());
	}

	@Deprecated
	public PlayerLoginFinishEvent(Connection connection, String username, UUID uuid, boolean onlineMode) {
		super(connection, username);
		this.uuid = uuid;
		this.onlineMode = onlineMode;
	}

	@Deprecated
	public PlayerLoginFinishEvent(InetSocketAddress address, String username, UUID uuid, boolean onlineMode) {
		this(ProtocolSupportAPI.getConnection(address), username, uuid, onlineMode);
	}

	/**
	 * Returns player uuid
	 * @return player uuid
	 */
	public UUID getUUID() {
		return uuid;
	}

	/**
	 * Returns true if this player logged in using online-mode checks
	 * @return true if this player logged in using online-mode checks
	 */
	public boolean isOnlineMode() {
		return onlineMode;
	}

	private final Map<String, Set<ProfileProperty>> properties = new HashMap<>();

	public Map<String, Set<ProfileProperty>> getProperties() {
		return Collections.unmodifiableMap(properties);
	}

	public boolean hasProperties(String name) {
		return properties.containsKey(name);
	}

	public boolean hasProperty(ProfileProperty property) {
		Set<ProfileProperty> propertiesWithName = properties.get(property.getName());
		return propertiesWithName != null && propertiesWithName.contains(property);
	}

	public void removeProperties(String name) {
		properties.remove(name);
	}

	public void removeProperty(ProfileProperty property) {
		String propertyName = property.getName();
		Set<ProfileProperty> propertiesWithName = properties.get(propertyName);
		propertiesWithName.remove(property);
		if (propertiesWithName.isEmpty()) {
			properties.remove(propertyName);
		}
	}

	public void addProperty(ProfileProperty property) {
		Utils.getFromMapOrCreateDefault(properties, property.getName(), new HashSet<>()).add(property);
	}


	private static final HandlerList list = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return list;
	}

	public static HandlerList getHandlerList() {
		return list;
	}

}
