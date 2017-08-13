package protocolsupport.protocol.typeremapper.pe;

import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.protocol.utils.types.NetworkEntityType;

import java.util.EnumMap;

public class PEDataValues {

	private static final EnumMap<NetworkEntityType, Integer> livingEntityType = new EnumMap<>(NetworkEntityType.class);
	static {
		livingEntityType.put(NetworkEntityType.WITHER_SKELETON, 48);
		livingEntityType.put(NetworkEntityType.WOLF, 14);
		livingEntityType.put(NetworkEntityType.RABBIT, 18);
		livingEntityType.put(NetworkEntityType.CHICKEN, 10);
		livingEntityType.put(NetworkEntityType.COW, 11);
		livingEntityType.put(NetworkEntityType.SHEEP, 13);
		livingEntityType.put(NetworkEntityType.PIG, 12);
		livingEntityType.put(NetworkEntityType.MUSHROOM_COW, 16);
		livingEntityType.put(NetworkEntityType.SHULKER, 54);
		livingEntityType.put(NetworkEntityType.GUARDIAN, 49);
		livingEntityType.put(NetworkEntityType.ENDERMITE, 55);
		livingEntityType.put(NetworkEntityType.WITCH, 45);
		livingEntityType.put(NetworkEntityType.BAT, 19);
		livingEntityType.put(NetworkEntityType.WITHER, 52);
		livingEntityType.put(NetworkEntityType.ENDER_DRAGON, 53);
		livingEntityType.put(NetworkEntityType.MAGMA_CUBE, 42);
		livingEntityType.put(NetworkEntityType.BLAZE, 43);
		livingEntityType.put(NetworkEntityType.SILVERFISH, 39);
		livingEntityType.put(NetworkEntityType.CAVE_SPIDER, 40);
		livingEntityType.put(NetworkEntityType.ENDERMAN, 38);
		livingEntityType.put(NetworkEntityType.ZOMBIE_PIGMAN, 36);
		livingEntityType.put(NetworkEntityType.GHAST, 41);
		livingEntityType.put(NetworkEntityType.SLIME, 37);
		livingEntityType.put(NetworkEntityType.ZOMBIE, 32);
		livingEntityType.put(NetworkEntityType.SPIDER, 35);
		livingEntityType.put(NetworkEntityType.SKELETON, 34);
		livingEntityType.put(NetworkEntityType.CREEPER, 33);
		livingEntityType.put(NetworkEntityType.VILLAGER, 15);
		livingEntityType.put(NetworkEntityType.MULE, 25);
		livingEntityType.put(NetworkEntityType.DONKEY, 24);
		livingEntityType.put(NetworkEntityType.ZOMBIE_HORSE, 27);
		livingEntityType.put(NetworkEntityType.SKELETON_HORSE, 26);
		livingEntityType.put(NetworkEntityType.ZOMBIE_VILLAGER, 44);
		livingEntityType.put(NetworkEntityType.HUSK, 47);
		livingEntityType.put(NetworkEntityType.SQUID, 17);
		livingEntityType.put(NetworkEntityType.STRAY, 46);
		livingEntityType.put(NetworkEntityType.POLAR_BEAR, 28);
		livingEntityType.put(NetworkEntityType.ELDER_GUARDIAN, 50);
		livingEntityType.put(NetworkEntityType.COMMON_HORSE, 23);
		livingEntityType.put(NetworkEntityType.IRON_GOLEM, 20);
		livingEntityType.put(NetworkEntityType.OCELOT, 22);
		livingEntityType.put(NetworkEntityType.SNOWMAN, 21);
		livingEntityType.put(NetworkEntityType.LAMA, 27);
	}

	public static int getLivingEntityTypeId(NetworkEntityType type) {
		return livingEntityType.get(type);
	}

	private static final EnumMap<NetworkEntityType, Integer> objectEntityType = new EnumMap<>(NetworkEntityType.class);
	static {
		objectEntityType.put(NetworkEntityType.TNT, 65);
		objectEntityType.put(NetworkEntityType.FALLING_OBJECT, 66);
		//TODO: Fix pistons, moving blocks? -> 67
		objectEntityType.put(NetworkEntityType.EXP_BOTTLE, 68);
		objectEntityType.put(NetworkEntityType.ENDEREYE, 70);
		objectEntityType.put(NetworkEntityType.ENDER_CRYSTAL, 71);
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

	public static final ArrayBasedIdRemappingTable ITEM_ID = new ArrayBasedIdRemappingTable(MinecraftData.ITEM_ID_MAX);
	private static void registerItemRemap(int from, int to) {
		ITEM_ID.setRemap(from, to);
	}

	static {
		// ===[ BLOCKS ]===
		// Nether slab -> Quartz slab
		registerBlockRemap(44, 7, 44, 6);
		registerBlockRemap(44, 14, 44, 15);
		registerBlockRemap(43, 7, 44, 6);
		// And vice-versa
		registerBlockRemap(44, 6, 44, 7);
		registerBlockRemap(44, 15, 44, 14);
		registerBlockRemap(43, 6, 44, 7);
		// Prismarine data ID mismatch
		registerBlockRemap(168, 1, 168, 2);
		registerBlockRemap(168, 2, 168, 1);
		// Podzol
		registerBlockRemap(3, 2, 243, 0);
		// Colored Fences
		registerBlockRemap(188, 85, 1);
		registerBlockRemap(189, 85, 2);
		registerBlockRemap(190, 85, 3);
		registerBlockRemap(191, 85, 4);
		registerBlockRemap(192, 85, 5);
		// Concrete Powder
		registerBlockRemap(252, 237);
		// Shulker Box
		registerBlockRemap(229, 218);
		// Chain Command Block
		registerBlockRemap(211, 189);
		// Repeating Command Block
		registerBlockRemap(210, 188);
		// Grass Path
		registerBlockRemap(208, 198);
		// Double Wooden Slab
		registerBlockRemap(126, 157);

		registerBlockRemap(95, 241); // STAINED_GLASS
		registerBlockRemap(157, 126); // ACTIVATOR_RAIL
		registerBlockRemap(158, 125); // DROPPER
		registerBlockRemap(198, 208); // END_ROD
		registerBlockRemap(199, 240); // CHORUS_PLANT
		registerBlockRemap(207, 244); // BEETROOT_BLOCK
		registerBlockRemap(208, 198); // GRASS_PATH
		registerBlockRemap(212, 207); // FROSTED_ICE
		registerBlockRemap(218, 251); // OBSERVER
		registerBlockRemap(235, 220); // WHITE_GLAZED_TERRACOTTA
		registerBlockRemap(236, 221); // ORANGE_GLAZED_TERRACOTTA
		registerBlockRemap(237, 222); // MAGENTA_GLAZED_TERRACOTTA
		registerBlockRemap(238, 223); // LIGHT_BLUE_GLAZED_TERRACOTTA
		registerBlockRemap(239, 224); // YELLOW_GLAZED_TERRACOTTA
		registerBlockRemap(240, 225); // LIME_GLAZED_TERRACOTTA
		registerBlockRemap(241, 226); // PINK_GLAZED_TERRACOTTA
		registerBlockRemap(242, 227); // GRAY_GLAZED_TERRACOTTA
		registerBlockRemap(243, 228); // SILVER_GLAZED_TERRACOTTA
		registerBlockRemap(244, 229); // CYAN_GLAZED_TERRACOTTA
		registerBlockRemap(245, 219); // PURPLE_GLAZED_TERRACOTTA
		registerBlockRemap(246, 231); // BLUE_GLAZED_TERRACOTTA
		registerBlockRemap(247, 232); // BROWN_GLAZED_TERRACOTTA
		registerBlockRemap(248, 233); // GREEN_GLAZED_TERRACOTTA
		registerBlockRemap(249, 234); // RED_GLAZED_TERRACOTTA
		registerBlockRemap(250, 235); // BLACK_GLAZED_TERRACOTTA
		registerBlockRemap(251, 236); // CONCRETE
		registerBlockRemap(255, 252); // STRUCTURE_BLOCK

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
	}

}
