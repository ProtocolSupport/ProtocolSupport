package protocolsupport.zplatform.impl.spigot;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.mojang.authlib.GameProfile;

import protocolsupport.api.utils.Profile;
import protocolsupport.api.utils.ProfileProperty;

public class SpigotWrappedGameProfile extends Profile {

	protected final GameProfile platformProfile;

	public SpigotWrappedGameProfile(Profile loginProfile, GameProfile platformProfile) {
		this.onlineMode = loginProfile.isOnlineMode();
		this.originalname = loginProfile.getOriginalName();
		this.originaluuid = loginProfile.getOriginalUUID();
		this.platformProfile = platformProfile;
	}

	@Override
	public String getName() {
		return platformProfile.getName();
	}

	@Override
	public UUID getUUID() {
		return platformProfile.getId();
	}

	@Override
	public Set<String> getPropertiesNames() {
		return Collections.unmodifiableSet(platformProfile.getProperties().keySet());
	}

	@Override
	public Set<ProfileProperty> getProperties(String name) {
		return
			platformProfile.getProperties().get(name).stream()
			.map(mojangproperty -> new ProfileProperty(mojangproperty.getName(), mojangproperty.getValue(), mojangproperty.getSignature()))
			.collect(Collectors.toSet());
	}

}
