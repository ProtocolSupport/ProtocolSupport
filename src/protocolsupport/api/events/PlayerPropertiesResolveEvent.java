package protocolsupport.api.events;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.HandlerList;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.utils.ApacheCommonsUtils;

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

	public Map<String, ProfileProperty> getProperties() {
		return new HashMap<>(properties);
	}

	public boolean hasProperty(String name) {
		return properties.containsKey(name);
	}

	public void removeProperty(String name) {
		properties.remove(name);
	}

	public void addProperty(ProfileProperty property) {
		properties.put(property.getName(), property);
	}

	public static class ProfileProperty {
		private final String name;
		private final String value;
		private final String signature;

		public ProfileProperty(String name, String value, String signature) {
			ApacheCommonsUtils.notNull(name, "Name cannot be null");
			ApacheCommonsUtils.notNull(value, "Value cannot be null");
			this.name = name;
			this.value = value;
			this.signature = signature;
		}

		public ProfileProperty(String name, String value) {
			this(name, value, null);
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public String getSignature() {
			return signature;
		}

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
