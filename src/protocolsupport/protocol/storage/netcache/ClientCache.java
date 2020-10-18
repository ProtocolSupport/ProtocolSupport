package protocolsupport.protocol.storage.netcache;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Registry;
import org.bukkit.block.Biome;

import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.NamespacedKeyUtils;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.Utils;

public class ClientCache implements IBiomeRegistry {

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

	protected String world;
	protected NBTCompound dimension;
	protected boolean dimensionSkyLight;

	public String getWorld() {
		return world;
	}

	public NBTCompound getDimension() {
		return dimension;
	}

	public void setCurrentWorld(String world, NBTCompound dimension) {
		if (!world.equals(this.world)) {
			this.world = world;
			this.dimension = dimension;
			this.dimensionSkyLight = dimension.getNumberTagOrThrow("has_skylight").getAsInt() == 1;
		}
	}

	public void setDimensionSkyLight(boolean hasSkyLight) {
		this.dimensionSkyLight = hasSkyLight;
	}

	public boolean hasDimensionSkyLight() {
		return dimensionSkyLight;
	}

	protected final Biome[] biomeById = new Biome[256];
	protected final int[] biomeToId = new int[Biome.values().length];

	{
		Arrays.fill(biomeToId, -1);
	}

	protected void registerBiome(Biome biome, int id) {
		biomeById[id] = biome;
		biomeToId[biome.ordinal()] = id;
	}

	@Override
	public Biome getBiome(int id) {
		if ((id >= 0) && (id < biomeById.length)) {
			return biomeById[id];
		} else {
			return null;
		}
	}

	@Override
	public int getBiomeId(Biome biome) {
		return biomeToId[biome.ordinal()];
	}

	public void setDimensionCodec(NBTCompound dimensionCodec) {
		NBTCompound biomeRegistry = dimensionCodec.getCompoundTagOrNull("minecraft:worldgen/biome");
		if (biomeRegistry != null) {
			for (NBTCompound biomeData : biomeRegistry.getCompoundListTagOrThrow("value").getTags()) {
				registerBiome(
					Registry.BIOME.get(NamespacedKeyUtils.fromString(biomeData.getStringTagValueOrThrow("name"))),
					biomeData.getNumberTagOrThrow("id").getAsInt()
				);
			}
		}
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
