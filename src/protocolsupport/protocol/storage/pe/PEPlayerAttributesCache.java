package protocolsupport.protocol.storage.pe;

import java.util.UUID;

import org.apache.commons.lang3.Validate;

public class PEPlayerAttributesCache {

	private boolean fakesetpositionswitch = true;

	public double getFakeSetPositionY() {
		fakesetpositionswitch = !fakesetpositionswitch;
		return fakesetpositionswitch ? 20.0 : 30.0;
	}



	private int gamemode = 0;
	private boolean canFly = false;
	private boolean isFlying = false;

	public void setGameMode(int gamemode) {
		this.gamemode = gamemode;
	}

	public int getGameMode() {
		return this.gamemode;
	}

	public void updateFlying(boolean canFly, boolean isFlying) {
		this.canFly = canFly;
		this.isFlying = isFlying;
	}

	public boolean canFly() {
		return this.canFly;
	}

	public boolean isFlying() {
		return this.isFlying;
	}



	private UUID peClientUUID;

	public void setPEClientUUID(UUID uuid) {
		Validate.notNull(uuid, "PE client uuid (identity) can't be null");
		this.peClientUUID = uuid;
	}

	public UUID getPEClientUUID() {
		return peClientUUID;
	}

}
