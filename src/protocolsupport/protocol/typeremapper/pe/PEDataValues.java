package protocolsupport.protocol.typeremapper.pe;

import java.util.EnumMap;

import org.bukkit.enchantments.Enchantment;

import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.HashMapBasedIdRemappingTable;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.protocol.utils.types.WindowType;

public class PEDataValues {

	private static final EnumMap<NetworkEntityType, Integer> livingEntityType = new EnumMap<>(NetworkEntityType.class);
	private static final TIntObjectHashMap<NetworkEntityType> livingTypeFromNetwork = new TIntObjectHashMap<NetworkEntityType>();
	private static void registerLivingType(NetworkEntityType type, int networkId) {
		livingEntityType.put(type, networkId);
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
	}

	public static int getLivingEntityTypeId(NetworkEntityType type) {
		return livingEntityType.get(type);
	}

	public static NetworkEntityType getLivingTypeFromPeNetworkId(int networkId) {
		return livingTypeFromNetwork.get(networkId);
	}

	private static final EnumMap<NetworkEntityType, Integer> objectEntityType = new EnumMap<>(NetworkEntityType.class);
	static {
		objectEntityType.put(NetworkEntityType.ARMOR_STAND_OBJECT, 61);
		objectEntityType.put(NetworkEntityType.TNT, 65);
		objectEntityType.put(NetworkEntityType.FALLING_OBJECT, 66);
		//TODO: Fix pistons, moving blocks? -> 67
		objectEntityType.put(NetworkEntityType.EXP_BOTTLE, 68);
		objectEntityType.put(NetworkEntityType.ENDEREYE, 70);
		objectEntityType.put(NetworkEntityType.ENDER_CRYSTAL, 71);
		objectEntityType.put(NetworkEntityType.FIREWORK, 72);
		objectEntityType.put(NetworkEntityType.SHULKER_BULLET, 76);
		objectEntityType.put(NetworkEntityType.FISHING_FLOAT, 77);
		objectEntityType.put(NetworkEntityType.DRAGON_FIREBALL, 79);
		objectEntityType.put(NetworkEntityType.ARROW, 80);
		objectEntityType.put(NetworkEntityType.SNOWBALL, 81);
		objectEntityType.put(NetworkEntityType.EGG, 82);
		objectEntityType.put(NetworkEntityType.MINECART, 84);
		objectEntityType.put(NetworkEntityType.FIREBALL, 85);
		objectEntityType.put(NetworkEntityType.POTION, 86);
		objectEntityType.put(NetworkEntityType.ENDERPEARL, 87);
		objectEntityType.put(NetworkEntityType.LEASH_KNOT, 88);
		objectEntityType.put(NetworkEntityType.WITHER_SKULL, 89);
		objectEntityType.put(NetworkEntityType.BOAT, 90);
		//TODO: WitherSkull dangerous? -> 91
		objectEntityType.put(NetworkEntityType.FIRECHARGE, 94);
		objectEntityType.put(NetworkEntityType.AREA_EFFECT_CLOUD, 95);
		objectEntityType.put(NetworkEntityType.MINECART_HOPPER, 96);
		objectEntityType.put(NetworkEntityType.MINECART_TNT, 97);
		objectEntityType.put(NetworkEntityType.MINECART_CHEST, 98);
		objectEntityType.put(NetworkEntityType.MINECART_COMMAND, 100);
		objectEntityType.put(NetworkEntityType.MINECART_FURNACE, 84); //Hack, we remap a furnace using entitymetadata.
		objectEntityType.put(NetworkEntityType.MINECART_MOB_SPAWNER, 84); //Hack, we remap a mobspawner using entitymetadata.
		objectEntityType.put(NetworkEntityType.AREA_EFFECT_CLOUD, 101);
		objectEntityType.put(NetworkEntityType.LAMA_SPIT, 102);
		objectEntityType.put(NetworkEntityType.EVOCATOR_FANGS, 103);
	}

	public static int getObjectEntityTypeId(NetworkEntityType type) {
		return objectEntityType.get(type);
	}

	public static final ArrayBasedIdRemappingTable BLOCK_ID = new ArrayBasedIdRemappingTable(MinecraftData.BLOCK_ID_MAX * MinecraftData.BLOCK_DATA_MAX);
	private static void registerBlockRemap(int from, int to) {
		for (int i = 0; i < MinecraftData.BLOCK_DATA_MAX; i++) {
			BLOCK_ID.setRemap(MinecraftData.getBlockStateFromIdAndData(from, i), MinecraftData.getBlockStateFromIdAndData(to, i));
		}
	}
	private static void registerBlockRemap(int from, int dataFrom, int to, int dataTo) {
		for (int i = 0; i < MinecraftData.BLOCK_DATA_MAX; i++) {
			BLOCK_ID.setRemap(MinecraftData.getBlockStateFromIdAndData(from, dataFrom), MinecraftData.getBlockStateFromIdAndData(to, dataTo));
		}
	}
	private static void registerBlockRemap(int from, int to, int dataTo) {
		for (int i = 0; i < MinecraftData.BLOCK_DATA_MAX; i++) {
			BLOCK_ID.setRemap(MinecraftData.getBlockStateFromIdAndData(from, i), MinecraftData.getBlockStateFromIdAndData(to, dataTo));
		}
	}

	private static final TIntIntHashMap pcEnchantToPe = new TIntIntHashMap();
	private static final TIntIntHashMap peEnchantToPc = new TIntIntHashMap();
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

	private static void registerBlockAndItemRemap(int from, int to) {
		registerBlockRemap(from, to);
		registerItemRemap(from, to);
	}
	private static void registerBlockAndItemRemap(int from, int dataFrom, int to, int dataTo) {
		registerBlockRemap(from, dataFrom, to, dataTo);
		registerItemRemap(from, dataFrom, to, dataTo);
	}
	private static void registerBlockAndItemRemap(int from, int to, int dataTo) {
		registerBlockRemap(from, to, dataTo);
		registerItemRemap(from, to, dataTo);
	}

	static {
		// ===[ BLOCKS ]===
		// Concrete Powder
		registerBlockAndItemRemap(252, 237);
		// Chain Command Block
		registerBlockAndItemRemap(211, 189);
		// Repeating Command Block
		registerBlockAndItemRemap(210, 188);
		// Grass Path
		registerBlockAndItemRemap(208, 198);
		// Double Wooden Slab
		registerBlockAndItemRemap(125, 157);
		registerBlockAndItemRemap(126, 158);
		registerBlockAndItemRemap(95, 241); // STAINED_GLASS
		registerBlockAndItemRemap(157, 126); // ACTIVATOR_RAIL
		registerBlockAndItemRemap(158, 125); // DROPPER
		registerBlockAndItemRemap(198, 208); // END_ROD
		registerBlockAndItemRemap(199, 240); // CHORUS_PLANT
		registerBlockAndItemRemap(207, 244); // BEETROOT_BLOCK
		registerBlockAndItemRemap(208, 198); // GRASS_PATH
		registerBlockAndItemRemap(212, 207); // FROSTED_ICE
		registerBlockAndItemRemap(218, 251); // OBSERVER
		registerBlockAndItemRemap(235, 220); // WHITE_GLAZED_TERRACOTTA
		registerBlockAndItemRemap(236, 221); // ORANGE_GLAZED_TERRACOTTA
		registerBlockAndItemRemap(237, 222); // MAGENTA_GLAZED_TERRACOTTA
		registerBlockAndItemRemap(238, 223); // LIGHT_BLUE_GLAZED_TERRACOTTA
		registerBlockAndItemRemap(239, 224); // YELLOW_GLAZED_TERRACOTTA
		registerBlockAndItemRemap(240, 225); // LIME_GLAZED_TERRACOTTA
		registerBlockAndItemRemap(241, 226); // PINK_GLAZED_TERRACOTTA
		registerBlockAndItemRemap(242, 227); // GRAY_GLAZED_TERRACOTTA
		registerBlockAndItemRemap(243, 228); // SILVER_GLAZED_TERRACOTTA
		registerBlockAndItemRemap(244, 229); // CYAN_GLAZED_TERRACOTTA
		registerBlockAndItemRemap(245, 219); // PURPLE_GLAZED_TERRACOTTA
		registerBlockAndItemRemap(246, 231); // BLUE_GLAZED_TERRACOTTA
		registerBlockAndItemRemap(247, 232); // BROWN_GLAZED_TERRACOTTA
		registerBlockAndItemRemap(248, 233); // GREEN_GLAZED_TERRACOTTA
		registerBlockAndItemRemap(249, 234); // RED_GLAZED_TERRACOTTA
		registerBlockAndItemRemap(250, 235); // BLACK_GLAZED_TERRACOTTA
		registerBlockAndItemRemap(251, 236); // CONCRETE
		registerBlockAndItemRemap(255, 252); // STRUCTURE_BLOCK
		registerBlockAndItemRemap(166, 95);  // BARRIER
		registerBlockAndItemRemap(154, 410);  // HOPPER
		registerBlockAndItemRemap(36, 250);  // Block Being Moved By Piston
		registerBlockAndItemRemap(205, 203);  // Purpur slab
		registerBlockAndItemRemap(204, 201);  // Purpur double slab TODO: replace to real double slab
		registerBlockAndItemRemap(202, 201, 2);  // Purpur pillar
		// Nether slab -> Quartz slab
		registerBlockAndItemRemap(44, 7, 44, 6);
		registerBlockAndItemRemap(44, 14, 44, 15);
		registerBlockAndItemRemap(43, 7, 43, 6);
		// And vice-versa
		registerBlockAndItemRemap(44, 6, 44, 7);
		registerBlockAndItemRemap(44, 15, 44, 14);
		registerBlockAndItemRemap(43, 6, 43, 7);
		// Prismarine data ID mismatch
		registerBlockAndItemRemap(168, 1, 168, 2);
		registerBlockAndItemRemap(168, 2, 168, 1);
		// Podzol
		registerBlockAndItemRemap(3, 2, 243, 0);
		// Colored Fences
		registerBlockRemap(188, 85, 1);
		registerBlockRemap(189, 85, 2);
		registerBlockRemap(190, 85, 3);
		registerBlockRemap(192, 85, 4);
		registerBlockRemap(191, 85, 5);
		registerItemRemap(188, 0, 85, 1);
		registerItemRemap(189, 0, 85, 2);
		registerItemRemap(190, 0, 85, 3);
		registerItemRemap(192, 0, 85, 4);
		registerItemRemap(191, 0, 85, 5);
		// Shulker Boxes
		registerBlockAndItemRemap(219, 218, 0); // WHITE_SHULKER_BOX
		registerBlockAndItemRemap(220, 218, 1); // ORANGE_SHULKER_BOX
		registerBlockAndItemRemap(221, 218, 2); // MAGENTA_SHULKER_BOX
		registerBlockAndItemRemap(222, 218, 3); // LIGHT_BLUE_SHULKER_BOX
		registerBlockAndItemRemap(223, 218, 4); // YELLOW_SHULKER_BOX
		registerBlockAndItemRemap(224, 218, 5); // LIME_SHULKER_BOX
		registerBlockAndItemRemap(225, 218, 6); // PINK_SHULKER_BOX
		registerBlockAndItemRemap(226, 218, 7); // GRAY_SHULKER_BOX
		registerBlockAndItemRemap(227, 218, 8); // SILVER_SHULKER_BOX
		registerBlockAndItemRemap(228, 218, 9); // CYAN_SHULKER_BOX
		registerBlockAndItemRemap(229, 218, 10); // PURPLE_SHULKER_BOX
		registerBlockAndItemRemap(230, 218, 11); // BLUE_SHULKER_BOX
		registerBlockAndItemRemap(231, 218, 12); // BROWN_SHULKER_BOX
		registerBlockAndItemRemap(232, 218, 13); // GREEN_SHULKER_BOX
		registerBlockAndItemRemap(233, 218, 14); // RED_SHULKER_BOX
		registerBlockAndItemRemap(234, 218, 15); // BLACK_SHULKER_BOX
		// Trap Doors...
		// Wooden
		registerBlockRemap(96, 0, 96, 3);
		registerBlockRemap(96, 1, 96, 2);
		registerBlockRemap(96, 2, 96, 1);
		registerBlockRemap(96, 3, 96, 0);
		registerBlockRemap(96, 4, 96, 11);
		registerBlockRemap(96, 5, 96, 10);
		registerBlockRemap(96, 6, 96, 9);
		registerBlockRemap(96, 7, 96, 8);
		registerBlockRemap(96, 8, 96, 7);
		registerBlockRemap(96, 9, 96, 6);
		registerBlockRemap(96, 10, 96, 5);
		registerBlockRemap(96, 11, 96, 4);
		registerBlockRemap(96, 12, 96, 15);
		registerBlockRemap(96, 13, 96, 14);
		registerBlockRemap(96, 14, 96, 13);
		registerBlockRemap(96, 15, 96, 12);
		// Iron
		registerBlockRemap(167, 0, 167, 3);
		registerBlockRemap(167, 1, 167, 2);
		registerBlockRemap(167, 2, 167, 1);
		registerBlockRemap(167, 3, 167, 0);
		registerBlockRemap(167, 4, 167, 11);
		registerBlockRemap(167, 5, 167, 10);
		registerBlockRemap(167, 6, 167, 9);
		registerBlockRemap(167, 7, 167, 8);
		registerBlockRemap(167, 8, 167, 7);
		registerBlockRemap(167, 9, 167, 6);
		registerBlockRemap(167, 10, 167, 5);
		registerBlockRemap(167, 11, 167, 4);
		registerBlockRemap(167, 12, 167, 15);
		registerBlockRemap(167, 13, 167, 14);
		registerBlockRemap(167, 14, 167, 13);
		registerBlockRemap(167, 15, 167, 12);

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
		registerItemRemap(444, 333, 1); // Spruce Boat
		registerItemRemap(445, 333, 2); // Birch Boat
		registerItemRemap(446, 333, 3); // Jungle Boat
		registerItemRemap(447, 333, 4); // Acacia Boat
		registerItemRemap(448, 333, 5); // Dark Oak Boat
		registerItemRemap(422, 443); // Minecart with a Command Block
		registerItemRemap(335, 325, 1); // Milk Bucket
		registerItemRemap(326, 325, 8); // Water Bucket
		registerItemRemap(327, 325, 10); // Lava Bucket
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

}
