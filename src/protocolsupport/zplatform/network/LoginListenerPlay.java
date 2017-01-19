package protocolsupport.zplatform.network;

import org.apache.commons.lang3.NotImplementedException;

import com.mojang.authlib.GameProfile;

import protocolsupport.protocol.packet.handler.IHasProfile;
import protocolsupport.zplatform.ServerImplementationType;
import protocolsupport.zplatform.impl.spigot.network.SpigotLoginListenerPlay;

public abstract class LoginListenerPlay implements IHasProfile {

	public static LoginListenerPlay create(NetworkManagerWrapper networkmanager, GameProfile gameprofile, boolean onlineMode, String hostname) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return new SpigotLoginListenerPlay(networkmanager, gameprofile, onlineMode, hostname);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	protected final NetworkManagerWrapper networkManager;
	protected final GameProfile profile;
	protected final boolean onlineMode;
	protected final String hostname;

	protected LoginListenerPlay(NetworkManagerWrapper networkmanager, GameProfile profile, boolean onlineMode, String hostname) {
		this.networkManager = networkmanager;
		this.profile = profile;
		this.onlineMode = onlineMode;
		this.hostname = hostname;
	}

	@Override
	public GameProfile getProfile() {
		return profile;
	}

	public abstract void finishLogin();

}
