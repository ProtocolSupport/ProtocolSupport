package protocolsupport.api.events;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;

/**
 * This event is fired after receiving player properties
 */
public class PlayerPropertiesResolveEvent extends PlayerEvent {

	private final HashMap<String, ProfileProperty> properties = new HashMap<>();

	public PlayerPropertiesResolveEvent(Connection connection, String username, Collection<ProfileProperty> properties) {
		super(connection, username);
		for (ProfileProperty property : properties) {
			addProperty(property);
		}
	}

	@Deprecated
	public PlayerPropertiesResolveEvent(InetSocketAddress address, String username, Collection<ProfileProperty> properties) {
		this(ProtocolSupportAPI.getConnection(address), username, properties);
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

	public static class ProfileProperty {
		private final String name;
		private final String value;
		private final String signature;

		public ProfileProperty(String name, String value, String signature) {
			Validate.notNull(name, "Name cannot be null");
			Validate.notNull(value, "Value cannot be null");
			this.name = name;
			this.value = value;
			this.signature = signature;
		}

		public ProfileProperty(String name, String value) {
			this(name, value, null);
		}

		/**
		 * Returns name of the property
		 * @return name of the property
		 */
		public String getName() {
			return name;
		}

		/**
		 * Returns value of the property
		 * @return value of the property
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Returns signature of the property or null if doesn't have one
		 * @return signature of the property or null
		 */
		public String getSignature() {
			return signature;
		}

		/**
		 * Checks if property has signature
		 * @return true if property has signature
		 */
		public boolean hasSignature() {
			return signature != null;
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
