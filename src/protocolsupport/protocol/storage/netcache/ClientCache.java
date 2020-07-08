package protocolsupport.protocol.storage.netcache;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTType;
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

	protected Map<String, NBTCompound> dimensions;
	protected NBTCompound dimension;

	public void setDimensions(NBTCompound dimensionsNBT) {
		Map<String, NBTCompound> dimensions = new HashMap<>();
		for (NBTCompound dimensionNBT : dimensionsNBT.getTagListOfType("dimension", NBTType.COMPOUND).getTags()) {
			dimensions.put(dimensionNBT.getTagOfType("name", NBTType.STRING).getValue(), dimensionNBT);
		}
		this.dimensions = dimensions;
	}

	public void setCurrentDimension(String dimension) {
		this.dimension = dimensions.get(dimension);

		if (this.dimension == null) {
			throw new IllegalArgumentException(MessageFormat.format("Dimension {0} doesn''t exist in dimension registry {1}", dimension, dimensions));
		}
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
