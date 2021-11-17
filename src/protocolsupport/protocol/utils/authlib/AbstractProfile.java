package protocolsupport.protocol.utils.authlib;

import java.util.UUID;

import protocolsupport.api.utils.Profile;

public abstract class AbstractProfile extends Profile {

	protected volatile boolean onlineMode;

	protected volatile String originalname;
	protected volatile UUID originaluuid;

	@Override
	public boolean isOnlineMode() {
		return onlineMode;
	}

	@Override
	public String getOriginalName() {
		return originalname;
	}

	@Override
	public UUID getOriginalUUID() {
		return originaluuid;
	}

}
