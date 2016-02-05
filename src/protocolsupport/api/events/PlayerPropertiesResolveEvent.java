package protocolsupport.api.events;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.bukkit.event.HandlerList;

public class PlayerPropertiesResolveEvent extends PlayerEvent {

	private final HashMap<String, ProfileProperty> properties = new HashMap<>();

	public PlayerPropertiesResolveEvent(InetSocketAddress address, String username, List<ProfileProperty> properties) {
		super(address, username);
		for (ProfileProperty property : properties) {
			addProperty(property);
		}
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
			Validate.notNull(name, "Name cannot be null");
			Validate.notNull(value, "Value cannot be null");
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
