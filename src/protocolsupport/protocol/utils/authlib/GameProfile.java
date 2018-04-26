package protocolsupport.protocol.utils.authlib;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.utils.Utils;

public class GameProfile {

	private final UUID uuid;
	private final String name;
	private final Map<String, Set<ProfileProperty>> properties = new HashMap<>();

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

	public Map<String, Set<ProfileProperty>> getProperties() {
		return Collections.unmodifiableMap(properties);
	}

	public boolean hasProperties(String name) {
		return properties.containsKey(name);
	}

	public void removeProperties(String name) {
		properties.remove(name);
	}

	public void addProperty(ProfileProperty property) {
		Utils.getFromMapOrCreateDefault(properties, property.getName(), new HashSet<>()).add(property);
	}

	public void addProperties(Map<String, Set<ProfileProperty>> propertiesMap) {
		propertiesMap.entrySet().forEach(entry -> Utils.getFromMapOrCreateDefault(properties, entry.getKey(), new HashSet<>()).addAll(entry.getValue()));
	}

	public void clearProperties() {
		properties.clear();
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
