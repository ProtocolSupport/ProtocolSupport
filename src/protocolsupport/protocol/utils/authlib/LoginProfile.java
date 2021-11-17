package protocolsupport.protocol.utils.authlib;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import protocolsupport.api.utils.ProfileProperty;

public class LoginProfile extends AbstractProfile {

	protected volatile String name;
	protected volatile UUID uuid;
	protected final Map<String, Set<ProfileProperty>> properties = new ConcurrentHashMap<>();

	public LoginProfile() {
	}

	public LoginProfile(@Nonnull UUID uuid, @Nonnull String name, @Nonnull ProfileProperty... properties) {
		this.uuid = uuid;
		this.name = name;
		Arrays.stream(properties).forEach(this::addProperty);
	}

	@Override
	public @Nullable String getName() {
		return name;
	}

	@Override
	public @Nullable UUID getUUID() {
		return uuid;
	}

	@Override
	public @Nonnull Set<String> getPropertiesNames() {
		return Collections.unmodifiableSet(properties.keySet());
	}

	@Override
	public @Nonnull Set<ProfileProperty> getProperties(String name) {
		return Collections.unmodifiableSet(properties.getOrDefault(name, Collections.emptySet()));
	}

	public void setOnlineMode(boolean onlineMode) {
		this.onlineMode = onlineMode;
	}

	public void setOriginalName(@Nonnull String name) {
		this.originalname = name;
		this.name = name;
	}

	public void setName(@Nonnull String name) {
		this.name = name;
	}

	public void setOriginalUUID(@Nonnull UUID uuid) {
		this.originaluuid = uuid;
		this.uuid = uuid;
	}

	public void setUUID(@Nonnull UUID uuid) {
		this.uuid = uuid;
	}

	public @Nonnull Map<String, Set<ProfileProperty>> getProperties() {
		return properties;
	}

	public void clearProperties() {
		properties.clear();
	}

	public void addProperty(@Nonnull ProfileProperty profileProperty) {
		properties.computeIfAbsent(profileProperty.getName(), k -> Collections.newSetFromMap(new ConcurrentHashMap<>())).add(profileProperty);
	}

}
