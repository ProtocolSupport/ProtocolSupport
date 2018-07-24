package protocolsupport.protocol.typeremapper.pe;

import java.io.BufferedReader;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.util.Vector;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.HashMapBasedIdRemappingTable;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntityType;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class PEDataValues {

	public static BufferedReader getResource(String name) {
		return Utils.getResource("pe/" + name);
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
		registerLivingType(NetworkEntityType.GIANT, 32); //Massive zombies. No remap though because we want the metadata.
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

		entityType.put(NetworkEntityType.ARMOR_STAND_OBJECT, 61);
		entityType.put(NetworkEntityType.TNT, 65);
		entityType.put(NetworkEntityType.FALLING_OBJECT, 66);
		//TODO: Fix pistons, moving blocks? -> 67
		entityType.put(NetworkEntityType.EXP_BOTTLE, 68);
		entityType.put(NetworkEntityType.EXP_ORB, 69);
		entityType.put(NetworkEntityType.ENDEREYE, 70);
		entityType.put(NetworkEntityType.ENDER_CRYSTAL, 71);
		entityType.put(NetworkEntityType.FIREWORK, 72);
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
		entityType.put(NetworkEntityType.MINECART_FURNACE, 84); //Hack, we remap a furnace using entitymetadata.
		entityType.put(NetworkEntityType.MINECART_MOB_SPAWNER, 84); //Hack, we remap a mobspawner using entitymetadata.
		//TODO: Lingering Potion? -> 101
		entityType.put(NetworkEntityType.LAMA_SPIT, 102);
		entityType.put(NetworkEntityType.EVOCATOR_FANGS, 103);
	}

	public static int getEntityTypeId(NetworkEntityType type) {
		return entityType.get(type);
	}

	public static NetworkEntityType getLivingTypeFromPeNetworkId(int networkId) {
		return livingTypeFromNetwork.get(networkId);
	}

	public static final ArrayBasedIdRemappingTable BLOCK_ID = new ArrayBasedIdRemappingTable(MinecraftData.BLOCK_ID_MAX * MinecraftData.BLOCK_DATA_MAX);
	static {
		getFileObject("blockremaps.json").get("Remaps").getAsJsonArray().forEach(entry -> {
			BLOCK_ID.setRemap(entry.getAsJsonObject().get("from").getAsInt(), entry.getAsJsonObject().get("to").getAsInt());
		});
	}

	private static final Int2IntOpenHashMap pcEnchantToPe = new Int2IntOpenHashMap();
	private static final Int2IntOpenHashMap peEnchantToPc = new Int2IntOpenHashMap();
	@SuppressWarnings("deprecation")
	private static void registerEnchantRemap(Enchantment enchantment, int peId) {
		pcEnchantToPe.put(enchantment.getId(), peId);
		peEnchantToPc.put(peId, enchantment.getId());
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

	public static final RemappingTable.ComplexIdRemappingTable ITEM_ID = new RemappingTable.ComplexIdRemappingTable();
	public static final RemappingTable.ComplexIdRemappingTable PE_ITEM_ID = new RemappingTable.ComplexIdRemappingTable();
	private static void registerItemRemap(int from, int to) {
		ITEM_ID.setSingleRemap(from, to, -1);
		PE_ITEM_ID.setSingleRemap(to, from, -1);
	}
	private static void registerItemRemap(int from, int to, int dataTo) {
		ITEM_ID.setSingleRemap(from, to, dataTo);
		PE_ITEM_ID.setSingleRemap(to, from, dataTo);
	}
	private static void registerItemRemap(int from, int dataFrom, int to, int dataTo) {
		ITEM_ID.setComplexRemap(from, dataFrom, to, dataTo);
		PE_ITEM_ID.setComplexRemap(to, dataTo, from, dataFrom);
	}

	static {
		// ===[ BLOCKS ]===
		// Concrete Powder
		registerItemRemap(252, 237);
		// Chain Command Block
		registerItemRemap(211, 189);
		// Repeating Command Block
		registerItemRemap(210, 188);
		// Grass Path
		registerItemRemap(208, 198);
		// Double Wooden Slab
		registerItemRemap(125, 157);
		registerItemRemap(126, 158);
		registerItemRemap(95, 241); // STAINED_GLASS
		registerItemRemap(157, 126); // ACTIVATOR_RAIL
		registerItemRemap(158, 125); // DROPPER
		registerItemRemap(198, 208); // END_ROD
		registerItemRemap(199, 240); // CHORUS_PLANT
		registerItemRemap(207, 244); // BEETROOT_BLOCK
		registerItemRemap(208, 198); // GRASS_PATH
		registerItemRemap(212, 207); // FROSTED_ICE
		registerItemRemap(218, 251); // OBSERVER
		registerItemRemap(235, 220); // WHITE_GLAZED_TERRACOTTA
		registerItemRemap(236, 221); // ORANGE_GLAZED_TERRACOTTA
		registerItemRemap(237, 222); // MAGENTA_GLAZED_TERRACOTTA
		registerItemRemap(238, 223); // LIGHT_BLUE_GLAZED_TERRACOTTA
		registerItemRemap(239, 224); // YELLOW_GLAZED_TERRACOTTA
		registerItemRemap(240, 225); // LIME_GLAZED_TERRACOTTA
		registerItemRemap(241, 226); // PINK_GLAZED_TERRACOTTA
		registerItemRemap(242, 227); // GRAY_GLAZED_TERRACOTTA
		registerItemRemap(243, 228); // SILVER_GLAZED_TERRACOTTA
		registerItemRemap(244, 229); // CYAN_GLAZED_TERRACOTTA
		registerItemRemap(245, 219); // PURPLE_GLAZED_TERRACOTTA
		registerItemRemap(246, 231); // BLUE_GLAZED_TERRACOTTA
		registerItemRemap(247, 232); // BROWN_GLAZED_TERRACOTTA
		registerItemRemap(248, 233); // GREEN_GLAZED_TERRACOTTA
		registerItemRemap(249, 234); // RED_GLAZED_TERRACOTTA
		registerItemRemap(250, 235); // BLACK_GLAZED_TERRACOTTA
		registerItemRemap(251, 236); // CONCRETE
		registerItemRemap(255, 252); // STRUCTURE_BLOCK
		registerItemRemap(166, 95);  // BARRIER
		registerItemRemap(154, 410);  // HOPPER
		registerItemRemap(36, 250);  // Block Being Moved By Piston
		registerItemRemap(205, 203);  // Purpur slab
		registerItemRemap(204, 201);  // Purpur double slab TODO: replace to real double slab
		registerItemRemap(202, 201, 2);  // Purpur pillar
		// Nether slab -> Quartz slab
		registerItemRemap(44, 7, 44, 6);
		registerItemRemap(44, 14, 44, 15);
		registerItemRemap(43, 7, 43, 6);
		// And vice-versa
		registerItemRemap(44, 6, 44, 7);
		registerItemRemap(44, 15, 44, 14);
		registerItemRemap(43, 6, 43, 7);
		// Prismarine data ID mismatch
		registerItemRemap(168, 1, 168, 2);
		registerItemRemap(168, 2, 168, 1);
		// Podzol
		registerItemRemap(3, 2, 243, 0);
		// Colored Fences
		registerItemRemap(188, 0, 85, 1);
		registerItemRemap(189, 0, 85, 2);
		registerItemRemap(190, 0, 85, 3);
		registerItemRemap(192, 0, 85, 4);
		registerItemRemap(191, 0, 85, 5);
		// Shulker Boxes
		registerItemRemap(219, 218, 0); // WHITE_SHULKER_BOX
		registerItemRemap(220, 218, 1); // ORANGE_SHULKER_BOX
		registerItemRemap(221, 218, 2); // MAGENTA_SHULKER_BOX
		registerItemRemap(222, 218, 3); // LIGHT_BLUE_SHULKER_BOX
		registerItemRemap(223, 218, 4); // YELLOW_SHULKER_BOX
		registerItemRemap(224, 218, 5); // LIME_SHULKER_BOX
		registerItemRemap(225, 218, 6); // PINK_SHULKER_BOX
		registerItemRemap(226, 218, 7); // GRAY_SHULKER_BOX
		registerItemRemap(227, 218, 8); // SILVER_SHULKER_BOX
		registerItemRemap(228, 218, 9); // CYAN_SHULKER_BOX
		registerItemRemap(229, 218, 10); // PURPLE_SHULKER_BOX
		registerItemRemap(230, 218, 11); // BLUE_SHULKER_BOX
		registerItemRemap(231, 218, 12); // BROWN_SHULKER_BOX
		registerItemRemap(232, 218, 13); // GREEN_SHULKER_BOX
		registerItemRemap(233, 218, 14); // RED_SHULKER_BOX
		registerItemRemap(234, 218, 15); // BLACK_SHULKER_BOX

		// ===[ ITEMS ]===
		registerItemRemap(410, 422); // PRISMARINE_CRYSTALS
		registerItemRemap(416, 425); // ARMOR_STAND
		registerItemRemap(425, 446); // BANNER
		registerItemRemap(434, 457); // BEETROOT
		registerItemRemap(435, 458); // BEETROOT_SEEDS
		registerItemRemap(436, 459); // BEETROOT_SOUP
		registerItemRemap(443, 444); // ELYTRA
		registerItemRemap(449, 450); // TOTEM
		registerItemRemap(450, 445); // SHULKER_SHELL
		registerItemRemap(322, 1, 466, 0); // Enchanted Golden Apple
		registerItemRemap(333, 0, 333, 0); // Oak Boat
		registerItemRemap(444, 0, 333, 1); // Spruce Boat
		registerItemRemap(445, 0, 333, 2); // Birch Boat
		registerItemRemap(446, 0, 333, 3); // Jungle Boat
		registerItemRemap(447, 0, 333, 4); // Acacia Boat
		registerItemRemap(448, 0, 333, 5); // Dark Oak Boat
		registerItemRemap(422, 443); // Minecart with a Command Block
		registerItemRemap(325, 0, 325, 0); // Bucket
		registerItemRemap(335, 0, 325, 1); // Milk Bucket
		registerItemRemap(326, 0, 325, 8); // Water Bucket
		registerItemRemap(327, 0, 325, 10); // Lava Bucket
		// Records
		registerItemRemap(2256, 500);
		registerItemRemap(2257, 501);
		registerItemRemap(2258, 502);
		registerItemRemap(2259, 503);
		registerItemRemap(2260, 504);
		registerItemRemap(2261, 505);
		registerItemRemap(2262, 506);
		registerItemRemap(2263, 507);
		registerItemRemap(2264, 508);
		registerItemRemap(2265, 509);
		registerItemRemap(2266, 510);
		registerItemRemap(2267, 511);

		// Not implemented (yet) in PE
		registerItemRemap(453, 340); // KNOWLEDGE BOOK -> BOOK
		registerItemRemap(442, 268); // SHIELD -> WOODEN SWORD
		registerItemRemap(439, 262); // SPECTRAL ARROW -> ARROW
		registerItemRemap(343, 408); // POWERED MINECART -> MINECART WITH A HOPPER
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
			private transient NBTTagCompoundWrapper filterNBT;

			protected void init() {
				filterNBT = ServerPlatform.get().getWrapperFactory().createNBTCompoundFromJson(Filter.replaceAll("\'", "\""));
			}

			public NBTTagCompoundWrapper getFilter() {
				return filterNBT;
			}
		}

	}

}
