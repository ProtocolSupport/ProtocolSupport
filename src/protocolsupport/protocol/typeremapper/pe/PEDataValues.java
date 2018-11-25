package protocolsupport.protocol.typeremapper.pe;

import java.io.BufferedReader;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

import org.bukkit.Particle;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.util.Vector;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.legacy.LegacyEnchantmentId;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.HashMapBasedIdRemappingTable;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.utils.Utils;

public class PEDataValues {

	public static String getResourcePath(String name) {
		return "pe/" + name;
	}

	public static BufferedReader getResource(String name) {
		return Utils.getResourceBuffered(getResourcePath(name));
	}

	private static JsonObject getFileObject(String name) {
		return new JsonParser().parse(getResource(name)).getAsJsonObject();
	}

	private static final EnumMap<NetworkEntityType, Integer> entityType = new EnumMap<>(NetworkEntityType.class);
	private static final Int2ObjectOpenHashMap<NetworkEntityType> livingTypeFromNetwork = new Int2ObjectOpenHashMap<>();
	private static void registerLivingType(NetworkEntityType type, int networkId) {
		entityType.put(type, networkId);
		livingTypeFromNetwork.put(networkId, type);
	}
	static {
		registerLivingType(NetworkEntityType.WITHER_SKELETON, 48);
		registerLivingType(NetworkEntityType.WOLF, 14);
		registerLivingType(NetworkEntityType.RABBIT, 18);
		registerLivingType(NetworkEntityType.CHICKEN, 10);
		registerLivingType(NetworkEntityType.COW, 11);
		registerLivingType(NetworkEntityType.SHEEP, 13);
		registerLivingType(NetworkEntityType.PIG, 12);
		registerLivingType(NetworkEntityType.MUSHROOM_COW, 16);
		registerLivingType(NetworkEntityType.SHULKER, 54);
		registerLivingType(NetworkEntityType.GUARDIAN, 49);
		registerLivingType(NetworkEntityType.ENDERMITE, 55);
		registerLivingType(NetworkEntityType.WITCH, 45);
		registerLivingType(NetworkEntityType.BAT, 19);
		registerLivingType(NetworkEntityType.WITHER, 52);
		registerLivingType(NetworkEntityType.ENDER_DRAGON, 53);
		registerLivingType(NetworkEntityType.MAGMA_CUBE, 42);
		registerLivingType(NetworkEntityType.BLAZE, 43);
		registerLivingType(NetworkEntityType.SILVERFISH, 39);
		registerLivingType(NetworkEntityType.CAVE_SPIDER, 40);
		registerLivingType(NetworkEntityType.ENDERMAN, 38);
		registerLivingType(NetworkEntityType.ZOMBIE_PIGMAN, 36);
		registerLivingType(NetworkEntityType.GHAST, 41);
		registerLivingType(NetworkEntityType.SLIME, 37);
		registerLivingType(NetworkEntityType.ZOMBIE, 32);
		registerLivingType(NetworkEntityType.SPIDER, 35);
		registerLivingType(NetworkEntityType.SKELETON, 34);
		registerLivingType(NetworkEntityType.CREEPER, 33);
		registerLivingType(NetworkEntityType.VILLAGER, 15);
		registerLivingType(NetworkEntityType.MULE, 25);
		registerLivingType(NetworkEntityType.DONKEY, 24);
		registerLivingType(NetworkEntityType.ZOMBIE_HORSE, 27);
		registerLivingType(NetworkEntityType.SKELETON_HORSE, 26);
		registerLivingType(NetworkEntityType.ZOMBIE_VILLAGER, 44);
		registerLivingType(NetworkEntityType.HUSK, 47);
		registerLivingType(NetworkEntityType.SQUID, 17);
		registerLivingType(NetworkEntityType.STRAY, 46);
		registerLivingType(NetworkEntityType.POLAR_BEAR, 28);
		registerLivingType(NetworkEntityType.ELDER_GUARDIAN, 50);
		registerLivingType(NetworkEntityType.COMMON_HORSE, 23);
		registerLivingType(NetworkEntityType.IRON_GOLEM, 20);
		registerLivingType(NetworkEntityType.OCELOT, 22);
		registerLivingType(NetworkEntityType.SNOWMAN, 21);
		registerLivingType(NetworkEntityType.LAMA, 29);
		registerLivingType(NetworkEntityType.PARROT, 30);
		registerLivingType(NetworkEntityType.ARMOR_STAND_MOB, 61);
		registerLivingType(NetworkEntityType.VINDICATOR, 57);
		registerLivingType(NetworkEntityType.EVOKER, 104);
		registerLivingType(NetworkEntityType.VEX, 105);
		registerLivingType(NetworkEntityType.DOLPHIN, 31);
		registerLivingType(NetworkEntityType.PUFFERFISH, 108);
		registerLivingType(NetworkEntityType.SALMON, 109);
		registerLivingType(NetworkEntityType.TROPICAL_FISH, 111);
		registerLivingType(NetworkEntityType.COD, 112);
		registerLivingType(NetworkEntityType.DROWNED, 110);
		registerLivingType(NetworkEntityType.TURTLE, 74);
		registerLivingType(NetworkEntityType.PHANTOM, 58);

		entityType.put(NetworkEntityType.ARMOR_STAND_OBJECT, 61);
		entityType.put(NetworkEntityType.TNT, 65);
		entityType.put(NetworkEntityType.FALLING_OBJECT, 66);
		//TODO: Fix pistons, moving blocks? -> 67
		entityType.put(NetworkEntityType.EXP_BOTTLE, 68);
		entityType.put(NetworkEntityType.EXP_ORB, 69);
		entityType.put(NetworkEntityType.ENDEREYE, 70);
		entityType.put(NetworkEntityType.ENDER_CRYSTAL, 71);
		entityType.put(NetworkEntityType.FIREWORK, 72);
		entityType.put(NetworkEntityType.THROWN_TRIDENT, 73);
		entityType.put(NetworkEntityType.SHULKER_BULLET, 76);
		entityType.put(NetworkEntityType.FISHING_FLOAT, 77);
		entityType.put(NetworkEntityType.DRAGON_FIREBALL, 79);
		entityType.put(NetworkEntityType.ARROW, 80);
		entityType.put(NetworkEntityType.SNOWBALL, 81);
		entityType.put(NetworkEntityType.EGG, 82);
		entityType.put(NetworkEntityType.MINECART, 84);
		entityType.put(NetworkEntityType.FIREBALL, 85);
		entityType.put(NetworkEntityType.POTION, 86);
		entityType.put(NetworkEntityType.ENDERPEARL, 87);
		entityType.put(NetworkEntityType.LEASH_KNOT, 88);
		entityType.put(NetworkEntityType.WITHER_SKULL, 89);
		entityType.put(NetworkEntityType.BOAT, 90);
		//TODO: WitherSkull dangerous? -> 91
		entityType.put(NetworkEntityType.FIRECHARGE, 94);
		entityType.put(NetworkEntityType.AREA_EFFECT_CLOUD, 95);
		entityType.put(NetworkEntityType.MINECART_HOPPER, 96);
		entityType.put(NetworkEntityType.MINECART_TNT, 97);
		entityType.put(NetworkEntityType.MINECART_CHEST, 98);
		entityType.put(NetworkEntityType.MINECART_COMMAND, 100);
		//TODO: Lingering Potion? -> 101
		entityType.put(NetworkEntityType.LAMA_SPIT, 102);
		entityType.put(NetworkEntityType.EVOCATOR_FANGS, 103);
	}

	public static int getEntityTypeId(NetworkEntityType type) {
		try {
			return entityType.get(type);
		} catch(NullPointerException e) {
			System.err.println("Missing entity mapping type for " + type);
			return entityType.get(NetworkEntityType.ARMOR_STAND_MOB);
		}
	}

	public static NetworkEntityType getLivingTypeFromPeNetworkId(int networkId) {
		return livingTypeFromNetwork.get(networkId);
	}

	private static final Int2IntOpenHashMap pcEnchantToPe = new Int2IntOpenHashMap();
	private static final Int2IntOpenHashMap peEnchantToPc = new Int2IntOpenHashMap();
	private static void registerEnchantRemap(Enchantment enchantment, int peId) {
		pcEnchantToPe.put(LegacyEnchantmentId.getId(enchantment), peId);
		peEnchantToPc.put(peId, LegacyEnchantmentId.getId(enchantment));
	}
	static {
		registerEnchantRemap(Enchantment.OXYGEN, 6);
		registerEnchantRemap(Enchantment.WATER_WORKER, 8);
		registerEnchantRemap(Enchantment.THORNS, 5);
		registerEnchantRemap(Enchantment.DEPTH_STRIDER, 7);
		registerEnchantRemap(Enchantment.FROST_WALKER, 25);
		registerEnchantRemap(Enchantment.DAMAGE_ALL, 9);
		registerEnchantRemap(Enchantment.DAMAGE_UNDEAD, 10);
		registerEnchantRemap(Enchantment.DAMAGE_ARTHROPODS, 11);
		registerEnchantRemap(Enchantment.KNOCKBACK, 12);
		registerEnchantRemap(Enchantment.FIRE_ASPECT, 13);
		registerEnchantRemap(Enchantment.LOOT_BONUS_MOBS, 14);
		registerEnchantRemap(Enchantment.DIG_SPEED, 15);
		registerEnchantRemap(Enchantment.SILK_TOUCH, 16);
		registerEnchantRemap(Enchantment.DURABILITY, 17);
		registerEnchantRemap(Enchantment.LOOT_BONUS_BLOCKS, 18);
		registerEnchantRemap(Enchantment.ARROW_DAMAGE, 19);
		registerEnchantRemap(Enchantment.ARROW_KNOCKBACK, 20);
		registerEnchantRemap(Enchantment.ARROW_FIRE, 21);
		registerEnchantRemap(Enchantment.ARROW_INFINITE, 22);
		registerEnchantRemap(Enchantment.LUCK, 23);
		registerEnchantRemap(Enchantment.LURE, 24);
		registerEnchantRemap(Enchantment.MENDING, 26);
	}

	public static int pcToPeEnchant(int pcId) {
		return pcEnchantToPe.get(pcId);
	}
	public static int peToPcEnchant(int peId) {
		return peEnchantToPc.get(peId);
	}

	public static final IdRemappingRegistry<HashMapBasedIdRemappingTable> PARTICLE = new IdRemappingRegistry<HashMapBasedIdRemappingTable>() {
		{
			//TODO: Check values. (Speculative = names don't match) Only a few values have been tested by hand.
			registerRemapEntry(Particle.EXPLOSION_NORMAL.ordinal(), PELevelEvent.PARTICLE_EXPLODE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.EXPLOSION_LARGE.ordinal(), PELevelEvent.PARTICLE_HUGE_EXPLOSION, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.EXPLOSION_HUGE.ordinal(), PELevelEvent.PARTICLE_HUGE_EXPLOSION_SEED, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.WATER_BUBBLE.ordinal(), PELevelEvent.PARTICLE_BUBBLE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.WATER_SPLASH.ordinal(), PELevelEvent.PARTICLE_SPLASH, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.WATER_WAKE.ordinal(), PELevelEvent.PARTICLE_WATER_WAKE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.CRIT.ordinal(), PELevelEvent.PARTICLE_CRITICAL, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.CRIT_MAGIC.ordinal(), PELevelEvent.PARTICLE_CRITICAL, ProtocolVersion.MINECRAFT_PE); //Magiccrit..?
			registerRemapEntry(Particle.SMOKE_NORMAL.ordinal(), PELevelEvent.PARTICLE_SMOKE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.SMOKE_LARGE.ordinal(), PELevelEvent.PARTICLE_LARGE_SMOKE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.SPELL.ordinal(), PELevelEvent.PARTICLE_MOB_SPELL, ProtocolVersion.MINECRAFT_PE); //Speculative
			registerRemapEntry(Particle.SPELL_INSTANT.ordinal(), PELevelEvent.PARTICLE_MOB_SPELL_INSTANTANIOUS, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.SPELL_MOB.ordinal(), PELevelEvent.PARTICLE_MOB_SPELL, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.SPELL_MOB_AMBIENT.ordinal(), PELevelEvent.PARTICLE_MOB_SPELL_INSTANTANIOUS, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.SPELL_WITCH.ordinal(), PELevelEvent.PARTICLE_WITCH_SPELL, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.DRIP_WATER.ordinal(), PELevelEvent.PARTICLE_DRIP_WATER, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.DRIP_LAVA.ordinal(), PELevelEvent.PARTICLE_DRIP_LAVA, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.VILLAGER_ANGRY.ordinal(), PELevelEvent.PARTICLE_VILLAGER_ANGRY, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.VILLAGER_HAPPY.ordinal(), PELevelEvent.PARTICLE_VILLAGER_HAPPY, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.TOWN_AURA.ordinal(), PELevelEvent.PARTICLE_TOWN_AURA, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.NOTE.ordinal(), PELevelEvent.PARTICLE_NOTE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.PORTAL.ordinal(), PELevelEvent.PARTICLE_PORTAL, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.ENCHANTMENT_TABLE.ordinal(), PELevelEvent.PARTICLE_ENCHANTMENT_TABLE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.FLAME.ordinal(), PELevelEvent.PARTICLE_FLAME, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.LAVA.ordinal(), PELevelEvent.PARTICLE_LAVA, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.REDSTONE.ordinal(), PELevelEvent.PARTICLE_RISING_RED_DUST, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.SNOWBALL.ordinal(), PELevelEvent.PARTICLE_SNOWBALL_POOF, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.SLIME.ordinal(), PELevelEvent.PARTICLE_SLIME, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.HEART.ordinal(), PELevelEvent.PARTICLE_HEART, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.BARRIER.ordinal(), PELevelEvent.PARTICLE_BLOCK_FORCE_FIELD, ProtocolVersion.MINECRAFT_PE); //Speculative
			registerRemapEntry(Particle.WATER_DROP.ordinal(), PELevelEvent.CAULDRON_TAKE_WATER, ProtocolVersion.MINECRAFT_PE); //Speculative
			registerRemapEntry(Particle.DRAGON_BREATH.ordinal(), PELevelEvent.PARTICLE_DRAGONS_BREATH, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.END_ROD.ordinal(), PELevelEvent.PARTICLE_END_ROT, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Particle.FALLING_DUST.ordinal(), PELevelEvent.PARTICLE_FALLING_DUST, ProtocolVersion.MINECRAFT_PE);
		}
		@Override
		protected HashMapBasedIdRemappingTable createTable() {
			return new HashMapBasedIdRemappingTable();
		}
	};

	public static final IdRemappingRegistry<ArrayBasedIdRemappingTable> BIOME = new IdRemappingRegistry<ArrayBasedIdRemappingTable>() {
		{
			registerRemapEntry(Biome.SMALL_END_ISLANDS, Biome.THE_END, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Biome.END_MIDLANDS, Biome.THE_END, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Biome.END_HIGHLANDS, Biome.THE_END, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Biome.END_BARRENS, Biome.THE_END, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Biome.THE_VOID, Biome.THE_END, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Biome.WARM_OCEAN, 40, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Biome.LUKEWARM_OCEAN, 42, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Biome.COLD_OCEAN, 44, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Biome.DEEP_WARM_OCEAN, 41, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Biome.DEEP_LUKEWARM_OCEAN, 43, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Biome.DEEP_COLD_OCEAN, 45, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Biome.FROZEN_OCEAN, 46, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(Biome.DEEP_FROZEN_OCEAN, 47, ProtocolVersion.MINECRAFT_PE);
		}
		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(167); //Largest biome id.
		}

		private void registerRemapEntry(Biome type, int to, ProtocolVersion version) {
			registerRemapEntry(type.ordinal(), to, version);
		}

		private void registerRemapEntry(Biome type, Biome to, ProtocolVersion version) {
			registerRemapEntry(type.ordinal(), to.ordinal(), version);
		}
	};

	public static final IdRemappingRegistry<ArrayBasedIdRemappingTable> WINDOWTYPE = new IdRemappingRegistry<ArrayBasedIdRemappingTable>() {
		{
			registerRemapEntry(WindowType.PLAYER, -1, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.CHEST, 0, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.CRAFTING_TABLE, 1, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.FURNACE, 2, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.ENCHANT, 3, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.BREWING, 4, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.ANVIL, 5, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.DISPENSER, 6, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.DROPPER, 7, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.HOPPER, 8, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.HORSE, 12, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.BEACON, 13, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.VILLAGER, 15, ProtocolVersion.MINECRAFT_PE);
		}
		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(14);
		}

		private void registerRemapEntry(WindowType type, int to, ProtocolVersion version) {
			registerRemapEntry(type.toLegacyId(), to, version);
		}
	};

	private static HashMap<NetworkEntityType, HashMap<Integer, Integer>> entityStatusRemaps = new HashMap<>();

	private static void registerEntityStatusRemap(int pcStatus, int peStatus, NetworkEntityType entityType) {
		HashMap<Integer, Integer> mapping = entityStatusRemaps.get(entityType);
		if (mapping == null) {
			mapping = new HashMap<>();
			entityStatusRemaps.put(entityType, mapping);
		}
		mapping.put(pcStatus, peStatus);
	}

	private static void registerEntityStatusRemap(int pcStatus, int peStatus) {
		registerEntityStatusRemap(pcStatus, peStatus, null);
	}

	private static void initEntityStatusRemaps() {
		registerEntityStatusRemap(2, 2); // HURT_ANIMATION
		registerEntityStatusRemap(3, -1, NetworkEntityType.SNOWBALL); // SNOWBALL_POOF
		registerEntityStatusRemap(3, -1, NetworkEntityType.EGG); // EGG_ICONCRACK
		registerEntityStatusRemap(3, 3); // DEATH_ANIMATION
		registerEntityStatusRemap(6, 6); // TAME_FAIL
		registerEntityStatusRemap(7, 7); // TAME_SUCCESS
		registerEntityStatusRemap(8, 8); // SHAKE_WET
		registerEntityStatusRemap(9, 9); // USE_ITEM
		registerEntityStatusRemap(10, 10, NetworkEntityType.SHEEP); // EAT_GRASS_ANIMATION
		registerEntityStatusRemap(10, 10, NetworkEntityType.MINECART_TNT); // MINECART_TNT_PRIME_FUSE
		registerEntityStatusRemap(11, 19); // IRON_GOLEM_OFFER_FLOWER
		registerEntityStatusRemap(15, 24); // WITCH_SPELL_PARTICLES
		registerEntityStatusRemap(16, 16); // ZOMBIE_VILLAGER_CURE
		registerEntityStatusRemap(17, 25); // FIREWORK_PARTICLES
		registerEntityStatusRemap(18, 21); // LOVE_PARTICLES
		registerEntityStatusRemap(20, 27); // SILVERFISH_SPAWN_ANIMATION
		registerEntityStatusRemap(31, 13); // FISH_HOOK_HOOK
		registerEntityStatusRemap(34, 20); // IRON_GOLEM_WITHDRAW_FLOWER
		registerEntityStatusRemap(35, 65); // CONSUME_TOTEM

		/*
		TODO: List of known PC entity status codes that are currently missing remaps. This
		might result in	missing/broken functionality on PE.
		TIPPED_ARROW = 0;
		ROTATED_JUMP_rabbit = 1;
		RESET_SPAWNER_minecartspawner = 1;
		SNOWBALL_POOF_snowball = 3;
		EGG_ICONCRACK_egg = 3;
		ATTACK = 4;
		VILLAGER_MATE_HEARTS = 12;
		VILLAGER_ANGRY = 13;
		VILLAGER_HAPPY = 14;
		RESET_ROTATION = 19;
		ENABLE_REDUCED_DEBUG = 22;
		DISABLE_REDUCED_DEBUG = 23;
		SET_OP_LEVEL_0 = 24;
		SET_OP_LEVEL_1 = 25;
		SET_OP_LEVEL_2 = 26;
		SET_OP_LEVEL_3 = 27
		SET_OP_LEVEL_4 = 28;
		SHIELD_BLOCK = 29;
		SHIELD_BREAK = 30;
		ARMOR_STAND_HIT = 32;
		ENTITY_HURT_THORNS = 33;
		ENTITY_HURT_DROWN = 36;
		ENTITY_HURT_BURN = 37;

		TODO: List of known PE entity status codes that are never sent. Presumably,
		some PE functionality is missing because of this.
		FISH_HOOK_BUBBLE = 11;
		FISH_HOOK_POSITION = 12;
		FISH_HOOK_TEASE = 14;
		SQUID_INK_CLOUD = 15;
		RESPAWN = 18;
		WITCH_DRINK_POTION = 29;
		WITCH_THROW_POTION = 30;
		PLAYER_ADD_XP_LEVELS = 34;
		ELDER_GUARDIAN_CURSE = 35;
		AGENT_ARM_SWING = 36;
		ENDER_DRAGON_DEATH = 37;
		DUST_PARTICLES = 38;
		ARROW_SHAKE = 39;
		EATING_ITEM = 57;
		BABY_ANIMAL_FEED = 60;
		DEATH_SMOKE_CLOUD = 61;
		COMPLETE_TRADE = 62;
		PLAYER_CHECK_TREASURE_HUNTER_ACHIEVEMENT = 66;
		ENTITY_SPAWN = 67;
		DRAGON_PUKE = 68;
		ITEM_ENTITY_MERGE = 69;
		*/
	}

	static {
		initEntityStatusRemaps();
	}

	/**
	 * Return the PE entity status code for the given PC entity status code and
	 * entity type. If no mapping is possible, -1 is returned.
	 */
	public static int getEntityStatusRemap(int pcStatus, NetworkEntityType entityType) {
		int peStatus = -1;

		HashMap<Integer, Integer> entity_mapping = entityStatusRemaps.get(entityType);
		if (entity_mapping != null && entity_mapping.containsKey(pcStatus)) {
			// If we have a specific key, use the value, even if it's -1 (that means
			// that we should ignore the default mapping)
			peStatus = entity_mapping.get(pcStatus);
		} else {
			HashMap<Integer, Integer> default_mapping = entityStatusRemaps.get(null);
			if (entity_mapping != null && default_mapping.containsKey(pcStatus)) {
				peStatus = default_mapping.get(pcStatus);
			}
		}

		return peStatus;
	}

	private final static Map<NetworkEntityType, PEEntityData> entityData = new EnumMap<NetworkEntityType, PEEntityData>(NetworkEntityType.class);
	static {
		getFileObject("entitydata.json").entrySet().forEach(entry -> {
			entityData.put(NetworkEntityType.valueOf(entry.getKey()), Utils.GSON.fromJson(entry.getValue(), PEEntityData.class).init());
		});
	}

	public static PEEntityData getEntityData(NetworkEntityType type) {
		return entityData.get(type);
	}

	public static class PEEntityData {
		@SerializedName("BoundingBox")
		private BoundingBox boundingBox;

		@SerializedName("Offset")
		private Offset offset;

		@SerializedName("RiderInfo")
		private RiderInfo riderInfo;

		@SerializedName("InventoryFilter")
		private PocketInventoryFilter inventoryFilter;

		public BoundingBox getBoundingBox() {
			return boundingBox;
		}

		public Offset getOffset() {
			return offset;
		}

		public RiderInfo getRiderInfo() {
			return riderInfo;
		}

		public PocketInventoryFilter getInventoryFilter() {
			return inventoryFilter;
		}

		public PEEntityData init() {
			if(inventoryFilter != null) {
				inventoryFilter.init();
			}
			return this;
		}

		public static class BoundingBox {
			private float width;
			private float height;

			public float getWidth() {
				return width;
			}

			public float getHeight() {
				return height;
			}
		}

		public static class Offset {
			private float x;
			private float y;
			private float z;
			private byte yaw;
			private byte pitch;

			public float getX() {
				return x;
			}

			public float getY() {
				return y;
			}

			public float getZ() {
				return z;
			}

			public byte getYaw() {
				return yaw;
			}

			public byte getPitch() {
				return pitch;
			}
		}

		public static class RiderInfo {
			private double x;
			private double y;
			private double z;
			private final Float rotationlock = null; //Optional

			public Vector getPosition() {
				return new Vector(x, y + 1.2, z);
			}

			public Float getRotationLock() {
				return rotationlock;
			}

		}

		public static class PocketInventoryFilter {
			private String Filter;
			private transient NBTCompound filterNBT;

			protected void init() {
				Filter.hashCode();
				//TODO GET THIS???
				//filterNBT = new createNBTCompoundFromJson(Filter.replaceAll("\'", "\""));
			}

			public NBTCompound getFilter() {
				return filterNBT;
			}
		}

	}

}
