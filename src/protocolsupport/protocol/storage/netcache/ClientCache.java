package protocolsupport.protocol.storage.netcache;

import java.util.UUID;

import org.bukkit.NamespacedKey;

import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
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
	protected boolean raining;

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
			this.raining = false;
		}
	}

	public void setDimensionSkyLight(boolean hasSkyLight) {
		this.dimensionSkyLight = hasSkyLight;
	}

	public boolean hasDimensionSkyLight() {
		return dimensionSkyLight;
	}

	public boolean isRanining() {
		return raining;
	}

	public boolean updateRain(boolean raining) {
		if (raining != this.raining) {
			this.raining = raining;
			return true;
		}
		return false;
	}

	protected final NamespacedKey[] biomeById = new NamespacedKey[256];
	protected final Object2IntMap<NamespacedKey> biomeId = new Object2IntLinkedOpenHashMap<>();

	protected void registerBiome(NamespacedKey biome, int id) {
		biomeById[id] = biome;
		biomeId.put(biome, id);
	}

	@Override
	public NamespacedKey getBiomeKey(int id) {
		if ((id >= 0) && (id < biomeById.length)) {
			return biomeById[id];
		} else {
			return null;
		}
	}

	@Override
	public int getBiomeId(NamespacedKey biome) {
		return biomeId.getOrDefault(biome, -1);
	}

	@Override
	public NamespacedKey getAnyBiomeKey() {
		return biomeId.keySet().iterator().next();
	}

	@Override
	public int getAnyBiomeId() {
		return biomeId.values().iterator().nextInt();
	}

	public void setDimensionCodec(NBTCompound dimensionCodec) {
		NBTCompound biomeRegistry = dimensionCodec.getCompoundTagOrNull("minecraft:worldgen/biome");
		if ((biomeRegistry == null) || biomeRegistry.isEmpty()) {
			throw new IllegalStateException("Dimension biome registry is empty");
		}
		for (NBTCompound biomeData : biomeRegistry.getCompoundListTagOrThrow("value").getTags()) {
			registerBiome(NamespacedKeyUtils.fromString(biomeData.getStringTagValueOrThrow("name")), biomeData.getNumberTagOrThrow("id").getAsInt());
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

	protected int heldSlot;

	public void setHeldSlot(int heldSlot) {
		this.heldSlot = heldSlot;
	}

	public int getHeldSlot() {
		return heldSlot;
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
