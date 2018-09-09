package protocolsupport.protocol.utils.authlib;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import protocolsupport.api.utils.Profile;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.utils.Utils;

public class GameProfile extends Profile {

	public GameProfile() {
	}

	public GameProfile(UUID uuid, String name, ProfileProperty... properties) {
		this.uuid = uuid;
		this.name = name;
		Arrays.stream(properties).forEach(this::addProperty);
	}

	public void setOnlineMode(boolean onlineMode) {
		this.onlineMode = onlineMode;
	}

	public void setOriginalName(String name) {
		this.originalname = name;
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOriginalUUID(UUID uuid) {
		this.originaluuid = uuid;
		this.uuid = uuid;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public Map<String, Set<ProfileProperty>> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Set<ProfileProperty>> propertiesMap) {
		properties.clear();
		properties.putAll(propertiesMap);
	}

	public void clearProperties() {
		properties.clear();
	}

	public void addProperty(ProfileProperty profileProperty) {
		properties.computeIfAbsent(profileProperty.getName(), k -> new HashSet<>()).add(profileProperty);
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
