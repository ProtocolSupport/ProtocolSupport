package protocolsupport.protocol.storage.netcache;

import java.util.UUID;

import org.apache.commons.lang3.Validate;

import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.protocol.utils.types.Environment;
import protocolsupport.protocol.utils.types.GameMode;
import protocolsupport.utils.Utils;

public class AttributesCache {

	protected Environment dimension;

	public void setCurrentDimension(Environment dimension) {
		this.dimension = dimension;
	}

	public boolean hasSkyLightInCurrentDimension() {
		return dimension == Environment.OVERWORLD;
	}

	public Environment getDimension() {
		return dimension;
	}


	protected String locale = I18NData.DEFAULT_LOCALE;

	public void setLocale(String locale) {
		this.locale = locale.toLowerCase();
	}

	public String getLocale() {
		return locale;
	}



	protected UUID peClientUUID;

	public void setPEClientUUID(UUID uuid) {
		Validate.notNull(uuid, "PE client uuid (identity) can't be null");
		this.peClientUUID = uuid;
	}

	public UUID getPEClientUUID() {
		return this.peClientUUID;
	}



	protected boolean peFakeSetPositionSwitch = true;

	public double getPEFakeSetPositionY() {
		peFakeSetPositionSwitch = !peFakeSetPositionSwitch;
		return peFakeSetPositionSwitch ? 20.0 : 30.0;
	}



	protected GameMode peGameMode = GameMode.SURVIVAL;
	protected boolean peCanFly = false;
	protected boolean peIsFlying = false;

	public void setPEGameMode(GameMode gamemode) {
		this.peGameMode = gamemode;
	}

	public GameMode getPEGameMode() {
		return this.peGameMode;
	}

	public void updatePEFlying(boolean canFly, boolean isFlying) {
		this.peCanFly = canFly;
		this.peIsFlying = isFlying;
	}

	public boolean canPEFly() {
		return this.peCanFly;
	}

	public boolean isPEFlying() {
		return this.peIsFlying;
	}


	protected byte peLastVehicleYaw;

	public void setPELastVehicleYaw(byte peLastVehicleYaw) {
		this.peLastVehicleYaw = peLastVehicleYaw;
	}

	public byte getPELastVehicleYaw() {
		return peLastVehicleYaw;
	}


	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
