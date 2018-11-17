package protocolsupport.protocol.typeremapper.pe;

import java.io.BufferedReader;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

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

	private static final EnumMap<NetworkEntityType, String> entityType = new EnumMap<>(NetworkEntityType.class);
	static {
		//entityType.put(NetworkEntityType.NPC, "minecraft:npc");
		entityType.put(NetworkEntityType.PLAYER, "minecraft:player");
		entityType.put(NetworkEntityType.WITHER_SKELETON, "minecraft:wither_skeleton");
		entityType.put(NetworkEntityType.HUSK, "minecraft:husk");
		entityType.put(NetworkEntityType.STRAY, "minecraft:stray");
		entityType.put(NetworkEntityType.WITCH, "minecraft:witch");
		entityType.put(NetworkEntityType.ZOMBIE_VILLAGER, "minecraft:zombie_villager");
		entityType.put(NetworkEntityType.BLAZE, "minecraft:blaze");
		entityType.put(NetworkEntityType.MAGMA_CUBE, "minecraft:magma_cube");
		entityType.put(NetworkEntityType.GHAST, "minecraft:ghast");
		entityType.put(NetworkEntityType.CAVE_SPIDER, "minecraft:cave_spider");
		entityType.put(NetworkEntityType.SILVERFISH, "minecraft:silverfish");
		entityType.put(NetworkEntityType.ENDERMAN, "minecraft:enderman");
		entityType.put(NetworkEntityType.SLIME, "minecraft:slime");
		entityType.put(NetworkEntityType.ZOMBIE_PIGMAN, "minecraft:zombie_pigman");
		entityType.put(NetworkEntityType.SPIDER, "minecraft:spider");
		entityType.put(NetworkEntityType.SKELETON, "minecraft:skeleton");
		entityType.put(NetworkEntityType.CREEPER, "minecraft:creeper");
		entityType.put(NetworkEntityType.ZOMBIE, "minecraft:zombie");
		entityType.put(NetworkEntityType.SKELETON_HORSE, "minecraft:skeleton_horse");
		entityType.put(NetworkEntityType.MULE, "minecraft:mule");
		entityType.put(NetworkEntityType.DONKEY, "minecraft:donkey");
		entityType.put(NetworkEntityType.DOLPHIN, "minecraft:dolphin");
		entityType.put(NetworkEntityType.TROPICAL_FISH, "minecraft:tropicalfish");
		entityType.put(NetworkEntityType.WOLF, "minecraft:wolf");
		entityType.put(NetworkEntityType.SQUID, "minecraft:squid");
		entityType.put(NetworkEntityType.DROWNED, "minecraft:drowned");
		entityType.put(NetworkEntityType.SHEEP, "minecraft:sheep");
		entityType.put(NetworkEntityType.MUSHROOM_COW, "minecraft:mooshroom");
		//entityType.put(NetworkEntityType.PANDA, "minecraft:panda");
		entityType.put(NetworkEntityType.SALMON, "minecraft:salmon");
		entityType.put(NetworkEntityType.PIG, "minecraft:pig");
		entityType.put(NetworkEntityType.VILLAGER, "minecraft:villager");
		entityType.put(NetworkEntityType.COD, "minecraft:cod");
		entityType.put(NetworkEntityType.PUFFERFISH, "minecraft:pufferfish");
		entityType.put(NetworkEntityType.COW, "minecraft:cow");
		entityType.put(NetworkEntityType.CHICKEN, "minecraft:chicken");
		//entityType.put(NetworkEntityType.BALLOON, "minecraft:balloon");
		entityType.put(NetworkEntityType.LAMA, "minecraft:llama");
		entityType.put(NetworkEntityType.IRON_GOLEM, "minecraft:iron_golem");
		entityType.put(NetworkEntityType.RABBIT, "minecraft:rabbit");
		//entityType.put(NetworkEntityType.SNOW_GOLEM, "minecraft:snow_golem");
		entityType.put(NetworkEntityType.BAT, "minecraft:bat");
		entityType.put(NetworkEntityType.OCELOT, "minecraft:ocelot");
		entityType.put(NetworkEntityType.COMMON_HORSE, "minecraft:horse");
		entityType.put(NetworkEntityType.OCELOT, "minecraft:cat");
		entityType.put(NetworkEntityType.POLAR_BEAR, "minecraft:polar_bear");
		entityType.put(NetworkEntityType.ZOMBIE_HORSE, "minecraft:zombie_horse");
		entityType.put(NetworkEntityType.TURTLE, "minecraft:turtle");
		entityType.put(NetworkEntityType.PARROT, "minecraft:parrot");
		entityType.put(NetworkEntityType.GUARDIAN, "minecraft:guardian");
		entityType.put(NetworkEntityType.ELDER_GUARDIAN, "minecraft:elder_guardian");
		entityType.put(NetworkEntityType.VINDICATOR, "minecraft:vindicator");
		entityType.put(NetworkEntityType.WITHER, "minecraft:wither");
		entityType.put(NetworkEntityType.ENDER_DRAGON, "minecraft:ender_dragon");
		entityType.put(NetworkEntityType.SHULKER, "minecraft:shulker");
		entityType.put(NetworkEntityType.ENDERMITE, "minecraft:endermite");
		entityType.put(NetworkEntityType.MINECART, "minecraft:minecart");
		entityType.put(NetworkEntityType.MINECART_HOPPER, "minecraft:hopper_minecart");
		entityType.put(NetworkEntityType.MINECART_TNT, "minecraft:tnt_minecart");
		entityType.put(NetworkEntityType.MINECART_CHEST, "minecraft:chest_minecart");
		entityType.put(NetworkEntityType.MINECART_COMMAND, "minecraft:command_block_minecart");
		entityType.put(NetworkEntityType.ARMOR_STAND_OBJECT, "minecraft:armor_stand");
		//entityType.put(NetworkEntityType.ARMOR_STAND_MOB, "minecraft:armor_stand");
		entityType.put(NetworkEntityType.ITEM, "minecraft:item");
		entityType.put(NetworkEntityType.TNT, "minecraft:tnt");
		entityType.put(NetworkEntityType.FALLING_OBJECT, "minecraft:falling_block");
		entityType.put(NetworkEntityType.EXP_BOTTLE, "minecraft:xp_bottle");
		entityType.put(NetworkEntityType.EXP_ORB, "minecraft:xp_orb");
		entityType.put(NetworkEntityType.ENDEREYE, "minecraft:eye_of_ender_signal");
		entityType.put(NetworkEntityType.ENDER_CRYSTAL, "minecraft:ender_crystal");
		entityType.put(NetworkEntityType.SHULKER_BULLET, "minecraft:shulker_bullet");
		entityType.put(NetworkEntityType.FISHING_FLOAT, "minecraft:fishing_hook");
		entityType.put(NetworkEntityType.DRAGON_FIREBALL, "minecraft:dragon_fireball");
		entityType.put(NetworkEntityType.ARROW, "minecraft:arrow");
		entityType.put(NetworkEntityType.SNOWBALL, "minecraft:snowball");
		entityType.put(NetworkEntityType.EGG, "minecraft:egg");
		entityType.put(NetworkEntityType.PAINTING, "minecraft:painting");
		entityType.put(NetworkEntityType.THROWN_TRIDENT, "minecraft:thrown_trident");
		entityType.put(NetworkEntityType.FIREBALL, "minecraft:fireball");
		entityType.put(NetworkEntityType.POTION, "minecraft:splash_potion");
		entityType.put(NetworkEntityType.ENDERPEARL, "minecraft:ender_pearl");
		entityType.put(NetworkEntityType.LEASH_KNOT, "minecraft:leash_knot");
		entityType.put(NetworkEntityType.WITHER_SKULL, "minecraft:wither_skull");
		//entityType.put(NetworkEntityType.WITHER_SKULL_DANGEROUS, "minecraft:wither_skull_dangerous");
		entityType.put(NetworkEntityType.BOAT, "minecraft:boat");
		//entityType.put(NetworkEntityType.LIGHTNING_BOLT, "minecraft:lightning_bolt");
		entityType.put(NetworkEntityType.FIREBALL, "minecraft:small_fireball");
		entityType.put(NetworkEntityType.LAMA_SPIT, "minecraft:llama_spit");
		entityType.put(NetworkEntityType.AREA_EFFECT_CLOUD, "minecraft:area_effect_cloud");
		//entityType.put(NetworkEntityType.LINGERING_POTION, "minecraft:lingering_potion");
		entityType.put(NetworkEntityType.FIREWORK, "minecraft:fireworks_rocket");
		entityType.put(NetworkEntityType.EVOCATOR_FANGS, "minecraft:evocation_fang");
		//entityType.put(NetworkEntityType.EVOCATION_ILLAGER, "minecraft:evocation_illager");
		entityType.put(NetworkEntityType.VEX, "minecraft:vex");
		//entityType.put(NetworkEntityType.AGENT, "minecraft:agent");
		//entityType.put(NetworkEntityType.ICE_BOMB, "minecraft:ice_bomb");
		entityType.put(NetworkEntityType.PHANTOM, "minecraft:phantom");
		//entityType.put(NetworkEntityType.TRIPOD_CAMERA, "minecraft:tripod_camera"
	}

	public static String getEntityKey(NetworkEntityType type) {
		String key = entityType.get(type);
		if (key == null) {
			System.err.println("Missing PE entity key for " + type);
			key = "minecraft:armor_stand";
		}
		return key;
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
			//TODO: Use particle enum
			registerRemapEntry(0, PELevelEvent.PARTICLE_EXPLODE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(1, PELevelEvent.PARTICLE_HUGE_EXPLOSION, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(2, PELevelEvent.PARTICLE_HUGE_EXPLOSION_SEED, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(4, PELevelEvent.PARTICLE_BUBBLE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(5, PELevelEvent.PARTICLE_SPLASH, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(6, PELevelEvent.PARTICLE_WATER_WAKE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(9, PELevelEvent.PARTICLE_CRITICAL, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(10, PELevelEvent.PARTICLE_CRITICAL, ProtocolVersion.MINECRAFT_PE); //Magiccrit..?
			registerRemapEntry(11, PELevelEvent.PARTICLE_SMOKE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(12, PELevelEvent.PARTICLE_LARGE_SMOKE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(13, PELevelEvent.PARTICLE_MOB_SPELL, ProtocolVersion.MINECRAFT_PE); //Speculative
			registerRemapEntry(14, PELevelEvent.PARTICLE_MOB_SPELL_INSTANTANIOUS, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(15, PELevelEvent.PARTICLE_MOB_SPELL, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(16, PELevelEvent.PARTICLE_MOB_SPELL_INSTANTANIOUS, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(17, PELevelEvent.PARTICLE_WITCH_SPELL, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(18, PELevelEvent.PARTICLE_DRIP_WATER, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(19, PELevelEvent.PARTICLE_DRIP_LAVA, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(20, PELevelEvent.PARTICLE_VILLAGER_ANGRY, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(21, PELevelEvent.PARTICLE_VILLAGER_HAPPY, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(22, PELevelEvent.PARTICLE_TOWN_AURA, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(23, PELevelEvent.PARTICLE_NOTE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(24, PELevelEvent.PARTICLE_PORTAL, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(25, PELevelEvent.PARTICLE_ENCHANTMENT_TABLE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(26, PELevelEvent.PARTICLE_FLAME, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(27, PELevelEvent.PARTICLE_LAVA, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(30, PELevelEvent.PARTICLE_RISING_RED_DUST, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(31, PELevelEvent.PARTICLE_SNOWBALL_POOF, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(33, PELevelEvent.PARTICLE_SLIME, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(34, PELevelEvent.PARTICLE_HEART, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(35, PELevelEvent.PARTICLE_BLOCK_FORCE_FIELD, ProtocolVersion.MINECRAFT_PE); //Speculative
			registerRemapEntry(40, PELevelEvent.CAULDRON_TAKE_WATER, ProtocolVersion.MINECRAFT_PE); //Speculative
			registerRemapEntry(42, PELevelEvent.PARTICLE_DRAGONS_BREATH, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(43, PELevelEvent.PARTICLE_END_ROT, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(46, PELevelEvent.PARTICLE_FALLING_DUST, ProtocolVersion.MINECRAFT_PE);
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
