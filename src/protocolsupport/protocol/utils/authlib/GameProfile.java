package protocolsupport.protocol.utils.authlib;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;

public class GameProfile {

	private final UUID uuid;
	private final String name;
	private final HashMap<String, ProfileProperty> properties = new HashMap<>();

	public GameProfile(UUID uuid, String name) {
		if ((uuid == null) && (name == null)) {
			throw new IllegalArgumentException("Both name and uuid can't be null");
		}
		this.uuid = uuid;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public UUID getUUID() {
		return uuid;
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

	public void clearProperties() {
		properties.clear();
	}

	@Override
	public String toString() {
		return MessageFormat.format("GameProfile(id={0},name={1})", uuid, name);
	}

}
