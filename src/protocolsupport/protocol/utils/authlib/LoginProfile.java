package protocolsupport.protocol.utils.authlib;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import protocolsupport.api.utils.Profile;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.utils.Utils;

public class LoginProfile extends Profile {

	protected volatile String name;
	protected volatile UUID uuid;
	protected final Map<String, Set<ProfileProperty>> properties = new ConcurrentHashMap<>();

	public LoginProfile() {
	}

	public LoginProfile(UUID uuid, String name, ProfileProperty... properties) {
		this.uuid = uuid;
		this.name = name;
		Arrays.stream(properties).forEach(this::addProperty);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public Set<String> getPropertiesNames() {
		return Collections.unmodifiableSet(properties.keySet());
	}

	@Override
	public Set<ProfileProperty> getProperties(String name) {
		return Collections.unmodifiableSet(properties.getOrDefault(name, Collections.emptySet()));
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

	public void clearProperties() {
		properties.clear();
	}

	public void addProperty(ProfileProperty profileProperty) {
		properties.computeIfAbsent(profileProperty.getName(), k -> Collections.newSetFromMap(new ConcurrentHashMap<>())).add(profileProperty);
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
