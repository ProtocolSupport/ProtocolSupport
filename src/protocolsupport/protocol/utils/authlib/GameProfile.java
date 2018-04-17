package protocolsupport.protocol.utils.authlib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.utils.Utils;

public class GameProfile {

	private final UUID uuid;
	private final String name;
	private final Map<String, List<ProfileProperty>> properties = new HashMap<>();

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

	public Map<String, List<ProfileProperty>> getProperties() {
		return Collections.unmodifiableMap(properties);
	}

	public boolean hasProperty(String name) {
		return properties.containsKey(name);
	}

	public void removeProperty(String name) {
		properties.remove(name);
	}

	public void addProperty(ProfileProperty property) {
		Utils.getFromMapOrCreateDefault(properties, property.getName(), new ArrayList<>()).add(property);
	}

	public void addProperties(Map<String, List<ProfileProperty>> properties) {
		properties.values().stream()
		.flatMap(List::stream)
		.forEach(this::addProperty);
	}

	public void clearProperties() {
		properties.clear();
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
