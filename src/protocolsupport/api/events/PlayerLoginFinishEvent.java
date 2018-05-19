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
import protocolsupport.api.utils.Profile;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.utils.Utils;

/**
 * This event is fired when player login finishes (after online-mode processing and uuid generation, but before actual world join)
 * This event is fired only if {@link PlayerLoginStartEvent} has fired for this client
 */
public class PlayerLoginFinishEvent extends PlayerAbstractLoginEvent {

	@Deprecated
	public PlayerLoginFinishEvent(Connection connection, String username, UUID uuid, boolean onlineMode) {
		super(connection);
	}

	@Deprecated
	public PlayerLoginFinishEvent(InetSocketAddress address, String username, UUID uuid, boolean onlineMode) {
		this(ProtocolSupportAPI.getConnection(address));
	}

	public PlayerLoginFinishEvent(Connection connection) {
		super(connection);
		connection.getProfile().getPropertiesNames().forEach(name -> properties.put(name, connection.getProfile().getProperties(name)));
	}

	private static final HandlerList list = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return list;
	}

	public static HandlerList getHandlerList() {
		return list;
	}
















	/**
	 * Returns true if this player logged in using online-mode checks
	 * @return true if this player logged in using online-mode checks
	 * @deprecated Use {@link Profile#isOnlineMode()}
	 */
	@Deprecated
	public boolean isOnlineMode() {
		return getConnection().getProfile().isOnlineMode();
	}

	/**
	 * Returns player uuid
	 * @return player uuid
	 * @deprecated Use {@link Profile#getUUID()} (profile can be acquired using {@link Connection#getProfile()})
	 */
	@Deprecated
	public UUID getUUID() {
		return getConnection().getProfile().getUUID();
	}

	protected final Map<String, Set<ProfileProperty>> properties = new HashMap<>();

	/**
	 * @deprecated Use {@link PlayerProfileCompleteEvent}
	 */
	@SuppressWarnings("javadoc")
	@Deprecated
	public Map<String, Set<ProfileProperty>> getProperties() {
		return Collections.unmodifiableMap(properties);
	}

	/**
	 * @deprecated Use {@link PlayerProfileCompleteEvent}
	 */
	@SuppressWarnings("javadoc")
	@Deprecated
	public boolean hasProperties(String name) {
		return properties.containsKey(name);
	}

	/**
	 * @deprecated Use {@link PlayerProfileCompleteEvent}
	 */
	@SuppressWarnings("javadoc")
	@Deprecated
	public boolean hasProperty(ProfileProperty property) {
		Set<ProfileProperty> propertiesWithName = properties.get(property.getName());
		return (propertiesWithName != null) && propertiesWithName.contains(property);
	}

	/**
	 * @deprecated Use {@link PlayerProfileCompleteEvent}
	 */
	@SuppressWarnings("javadoc")
	@Deprecated
	public void removeProperties(String name) {
		properties.remove(name);
	}

	/**
	 * @deprecated Use {@link PlayerProfileCompleteEvent}
	 */
	@SuppressWarnings("javadoc")
	@Deprecated
	public void removeProperty(ProfileProperty property) {
		String propertyName = property.getName();
		Set<ProfileProperty> propertiesWithName = properties.get(propertyName);
		propertiesWithName.remove(property);
		if (propertiesWithName.isEmpty()) {
			properties.remove(propertyName);
		}
	}

	@Deprecated
	public void addProperty(ProfileProperty property) {
		Utils.getFromMapOrCreateDefault(properties, property.getName(), new HashSet<>()).add(property);
	}

}
