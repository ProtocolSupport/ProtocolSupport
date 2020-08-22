package protocolsupport.protocol.storage.netcache;

import java.util.UUID;

import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.Utils;

public class ClientCache {

	protected UUID uuid;

	public UUID getUUID() {
		return uuid;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	protected boolean respawnScreenEnabled;

	public void setRespawnScreenEnabled(boolean enableRespawnScreen) {
		this.respawnScreenEnabled = enableRespawnScreen;
	}

	public boolean isRespawnScreenEnabled() {
		return respawnScreenEnabled;
	}

	protected NBTCompound dimension;

	public NBTCompound setCurrentDimension(NBTCompound dimension) {
		NBTCompound oldDimension = this.dimension;
		this.dimension = dimension;
		return oldDimension;
	}

	public boolean hasSkyLightInCurrentDimension() {
		return dimension.getNumberTag("has_skylight").getAsInt() == 1;
	}

	protected float maxHealth = 20.0F;

	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	protected boolean sneaking;

	public void setSneaking(boolean sneaking) {
		this.sneaking = sneaking;
	}

	public boolean isSneaking() {
		return sneaking;
	}

	protected String locale = I18NData.DEFAULT_LOCALE;

	public void setLocale(String locale) {
		this.locale = locale.toLowerCase();
	}

	public String getLocale() {
		return locale;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
