package protocolsupport.api.events;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;

/**
 * This event is fired after receiving player properties
 * @deprecated Use {@link PlayerLoginFinishEvent}
 */
@Deprecated
public class PlayerPropertiesResolveEvent extends PlayerEvent {

	private final HashMap<String, ProfileProperty> properties = new HashMap<>();

	public PlayerPropertiesResolveEvent(Connection connection, String username, Collection<ProfileProperty> properties) {
		super(connection, username);
		for (ProfileProperty property : properties) {
			addProperty(property);
		}
	}

	public PlayerPropertiesResolveEvent(InetSocketAddress address, String username, Collection<ProfileProperty> properties) {
		this(ProtocolSupportAPI.getConnection(address), username, properties);
	}

	public PlayerPropertiesResolveEvent(Connection connection) {
		super(connection);
		for (String name : connection.getProfile().getPropertiesNames()) {
			addProperty(new ProfileProperty(connection.getProfile().getProperties(name).iterator().next()));
		}
	}

	/**
	 * Returns player properties copy
	 * @return player properties copy
	 */
	public Map<String, ProfileProperty> getProperties() {
		return new HashMap<>(properties);
	}

	/**
	 * Checks if player has property by name
	 * @param name property name
	 * @return true if has property
	 */
	public boolean hasProperty(String name) {
		return properties.containsKey(name);
	}

	/**
	 * Removes property value by name
	 * @param name property name
	 */
	public void removeProperty(String name) {
		properties.remove(name);
	}

	/**
	 * Adds property
	 * @param property property
	 */
	public void addProperty(ProfileProperty property) {
		properties.put(property.getName(), property);
	}

	public static class ProfileProperty extends protocolsupport.api.utils.ProfileProperty {

		public ProfileProperty(String name, String value, String signature) {
			super(name, value, signature);
		}

		public ProfileProperty(String name, String value) {
			this(name, value, null);
		}

		public ProfileProperty(protocolsupport.api.utils.ProfileProperty newProperty) {
			this(newProperty.getName(), newProperty.getValue(), newProperty.getSignature());
		}

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
