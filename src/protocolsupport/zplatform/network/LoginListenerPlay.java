package protocolsupport.zplatform.network;

import com.mojang.authlib.GameProfile;

import protocolsupport.protocol.packet.handler.IHasProfile;

public abstract class LoginListenerPlay implements IHasProfile {

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
