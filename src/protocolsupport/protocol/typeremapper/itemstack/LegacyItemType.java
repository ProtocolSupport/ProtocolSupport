package protocolsupport.protocol.typeremapper.itemstack;

import org.bukkit.Material;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;

public class LegacyItemType {


	public static final ItemIdRemappingRegistry REGISTRY = new ItemIdRemappingRegistry();

	public static class ItemIdRemappingRegistry extends IdRemappingRegistry<ArrayBasedIdRemappingTable> {
		{
			applyDefaultRemaps();
		}
		public void applyDefaultRemaps() {
//TODO: update to new material enum
//			//modified copy of blocks remapper adapted to items
//			registerRemapEntry(Material.CONCRETE, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_12);
//			registerRemapEntry(Material.CONCRETE_POWDER, Material.WOOL, ProtocolVersionsHelper.BEFORE_1_12);
//			registerRemapEntry(Material.WHITE_GLAZED_TERRACOTTA, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_12);
//			registerRemapEntry(Material.ORANGE_GLAZED_TERRACOTTA, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_12);
//			registerRemapEntry(Material.MAGENTA_GLAZED_TERRACOTTA, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_12);
//			registerRemapEntry(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_12);
//			registerRemapEntry(Material.YELLOW_GLAZED_TERRACOTTA, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_12);
//			registerRemapEntry(Material.LIME_GLAZED_TERRACOTTA, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_12);
//			registerRemapEntry(Material.PINK_GLAZED_TERRACOTTA, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_12);
//			registerRemapEntry(Material.GRAY_GLAZED_TERRACOTTA, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_12);
//			registerRemapEntry(Material.SILVER_GLAZED_TERRACOTTA, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_12);
//			registerRemapEntry(Material.CYAN_GLAZED_TERRACOTTA, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_12);
//			registerRemapEntry(Material.PURPLE_GLAZED_TERRACOTTA, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_12);
//			registerRemapEntry(Material.BLUE_GLAZED_TERRACOTTA, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_12);
//			registerRemapEntry(Material.BROWN_GLAZED_TERRACOTTA, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_12);
//			registerRemapEntry(Material.GREEN_GLAZED_TERRACOTTA, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_12);
//			registerRemapEntry(Material.RED_GLAZED_TERRACOTTA, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_12);
//			registerRemapEntry(Material.BLACK_GLAZED_TERRACOTTA, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_12);
//			registerRemapEntry(Material.IRON_NUGGET, Material.GOLD_NUGGET, ProtocolVersionsHelper.BEFORE_1_11_1);
//			registerRemapEntry(Material.OBSERVER, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_11);
//			registerRemapEntry(Material.WHITE_SHULKER_BOX, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_11);
//			registerRemapEntry(Material.ORANGE_SHULKER_BOX, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_11);
//			registerRemapEntry(Material.MAGENTA_SHULKER_BOX, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_11);
//			registerRemapEntry(Material.LIGHT_BLUE_SHULKER_BOX, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_11);
//			registerRemapEntry(Material.YELLOW_SHULKER_BOX, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_11);
//			registerRemapEntry(Material.LIME_SHULKER_BOX, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_11);
//			registerRemapEntry(Material.PINK_SHULKER_BOX, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_11);
//			registerRemapEntry(Material.GRAY_SHULKER_BOX, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_11);
//			registerRemapEntry(Material.SILVER_SHULKER_BOX, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_11);
//			registerRemapEntry(Material.CYAN_SHULKER_BOX, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_11);
//			registerRemapEntry(Material.PURPLE_SHULKER_BOX, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_11);
//			registerRemapEntry(Material.BLUE_SHULKER_BOX, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_11);
//			registerRemapEntry(Material.BROWN_SHULKER_BOX, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_11);
//			registerRemapEntry(Material.GREEN_SHULKER_BOX, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_11);
//			registerRemapEntry(Material.RED_SHULKER_BOX, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_11);
//			registerRemapEntry(Material.BLACK_SHULKER_BOX, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_11);
//			registerRemapEntry(Material.STRUCTURE_VOID, Material.GLASS, ProtocolVersionsHelper.BEFORE_1_10);
//			registerRemapEntry(Material.NETHER_WART_BLOCK, Material.WOOL, ProtocolVersionsHelper.BEFORE_1_10);
//			registerRemapEntry(Material.RED_NETHER_BRICK, Material.NETHER_BRICK, ProtocolVersionsHelper.BEFORE_1_10);
//			registerRemapEntry(Material.MAGMA, Material.NETHERRACK, ProtocolVersionsHelper.BEFORE_1_10);
//			registerRemapEntry(Material.BONE_BLOCK, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_10);
//			registerRemapEntry(Material.COMMAND_CHAIN, Material.COMMAND, ProtocolVersionsHelper.BEFORE_1_9);
//			registerRemapEntry(Material.COMMAND_REPEATING, Material.COMMAND, ProtocolVersionsHelper.BEFORE_1_9);
//			registerRemapEntry(Material.COMMAND, Material.COMMAND, ProtocolVersionsHelper.BEFORE_1_9);
//			registerRemapEntry(Material.CHORUS_FLOWER, Material.WOOD, ProtocolVersionsHelper.BEFORE_1_9);
//			registerRemapEntry(Material.CHORUS_PLANT, Material.WOOD, ProtocolVersionsHelper.BEFORE_1_9);
//			registerRemapEntry(Material.END_GATEWAY, Material.ENDER_PORTAL, ProtocolVersionsHelper.BEFORE_1_9);
//			registerRemapEntry(Material.END_ROD, Material.GLOWSTONE, ProtocolVersionsHelper.BEFORE_1_9);
//			registerRemapEntry(Material.PURPUR_PILLAR, Material.STONE, ProtocolVersionsHelper.BEFORE_1_9);
//			registerRemapEntry(Material.END_BRICKS, Material.ENDER_STONE, ProtocolVersionsHelper.BEFORE_1_9);
//			registerRemapEntry(Material.FROSTED_ICE, Material.ICE, ProtocolVersionsHelper.BEFORE_1_9);
//			registerRemapEntry(Material.GRASS_PATH, Material.SOIL, ProtocolVersionsHelper.BEFORE_1_9);
//			registerRemapEntry(Material.PURPUR_BLOCK, Material.STONE, ProtocolVersionsHelper.BEFORE_1_9);
//			registerRemapEntry(Material.PURPUR_STAIRS, Material.COBBLESTONE_STAIRS, ProtocolVersionsHelper.BEFORE_1_9);
//			registerRemapEntry(Material.PURPUR_SLAB, Material.STEP, ProtocolVersionsHelper.BEFORE_1_9);
//			registerRemapEntry(Material.PURPUR_DOUBLE_SLAB, Material.DOUBLE_STEP, ProtocolVersionsHelper.BEFORE_1_9);
//			registerRemapEntry(Material.STRUCTURE_BLOCK, Material.BEDROCK, ProtocolVersionsHelper.BEFORE_1_9);
//			registerRemapEntry(Material.BEETROOT_BLOCK, Material.CROPS, ProtocolVersionsHelper.BEFORE_1_9);
//			registerRemapEntry(Material.SLIME_BLOCK, Material.EMERALD_BLOCK, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.BARRIER, Material.GLASS, ProtocolVersionsHelper.BEFORE_1_8);
//			//TODO: remap with something that has more strength
//			registerRemapEntry(Material.IRON_TRAPDOOR, Material.TRAP_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.PRISMARINE, Material.MOSSY_COBBLESTONE, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.SEA_LANTERN, Material.GLOWSTONE, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.STANDING_BANNER, Material.SIGN_POST, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.WALL_BANNER, Material.WALL_SIGN, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.RED_SANDSTONE, Material.SANDSTONE, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.RED_SANDSTONE_STAIRS, Material.SANDSTONE_STAIRS, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.DOUBLE_STONE_SLAB2, Material.DOUBLE_STEP, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.STONE_SLAB2, Material.STEP, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.SPRUCE_FENCE_GATE, Material.FENCE_GATE, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.BIRCH_FENCE_GATE, Material.FENCE_GATE, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.JUNGLE_FENCE_GATE, Material.FENCE_GATE, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.DARK_OAK_FENCE_GATE, Material.FENCE_GATE, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.ACACIA_FENCE_GATE, Material.FENCE_GATE, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.SPRUCE_FENCE, Material.FENCE, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.BIRCH_FENCE, Material.FENCE, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.JUNGLE_FENCE, Material.FENCE, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.DARK_OAK_FENCE, Material.FENCE, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.ACACIA_FENCE, Material.FENCE, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.SPRUCE_DOOR, Material.WOODEN_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.BIRCH_DOOR, Material.WOODEN_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.JUNGLE_DOOR, Material.WOODEN_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.ACACIA_DOOR, Material.WOODEN_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.DARK_OAK_DOOR, Material.WOODEN_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.DAYLIGHT_DETECTOR_INVERTED, Material.DAYLIGHT_DETECTOR, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.STAINED_GLASS, Material.GLASS, ProtocolVersionsHelper.BEFORE_1_7);
//			registerRemapEntry(Material.STAINED_GLASS_PANE, Material.THIN_GLASS, ProtocolVersionsHelper.BEFORE_1_7);
//			registerRemapEntry(Material.LEAVES_2, Material.LEAVES, ProtocolVersionsHelper.BEFORE_1_7);
//			registerRemapEntry(Material.LOG_2, Material.LOG, ProtocolVersionsHelper.BEFORE_1_7);
//			registerRemapEntry(Material.ACACIA_STAIRS, Material.WOOD_STAIRS, ProtocolVersionsHelper.BEFORE_1_7);
//			registerRemapEntry(Material.DARK_OAK_STAIRS, Material.WOOD_STAIRS, ProtocolVersionsHelper.BEFORE_1_7);
//			registerRemapEntry(Material.DOUBLE_PLANT, Material.YELLOW_FLOWER, ProtocolVersionsHelper.BEFORE_1_7);
//			registerRemapEntry(Material.PACKED_ICE, Material.WOOL, ProtocolVersionsHelper.BEFORE_1_7);
//			registerRemapEntry(Material.STAINED_CLAY, Material.STONE, ProtocolVersionsHelper.BEFORE_1_6);
//			registerRemapEntry(Material.HAY_BLOCK, Material.STONE, ProtocolVersionsHelper.BEFORE_1_6);
//			registerRemapEntry(Material.CARPET, Material.SNOW, ProtocolVersionsHelper.BEFORE_1_6);
//			registerRemapEntry(Material.HARD_CLAY, Material.STONE, ProtocolVersionsHelper.BEFORE_1_6);
//			registerRemapEntry(Material.COAL_BLOCK, Material.OBSIDIAN, ProtocolVersionsHelper.BEFORE_1_6);
//			registerRemapEntry(Material.DROPPER, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_5);
//			registerRemapEntry(Material.HOPPER, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_5);
//			registerRemapEntry(Material.QUARTZ_BLOCK, Material.STONE, ProtocolVersionsHelper.BEFORE_1_5);
//			registerRemapEntry(Material.QUARTZ_STAIRS, Material.SMOOTH_STAIRS, ProtocolVersionsHelper.BEFORE_1_5);
//			registerRemapEntry(Material.DAYLIGHT_DETECTOR_INVERTED, Material.STEP, ProtocolVersionsHelper.BEFORE_1_5);
//			registerRemapEntry(Material.DAYLIGHT_DETECTOR, Material.STEP, ProtocolVersionsHelper.BEFORE_1_5);
//			registerRemapEntry(Material.TRAPPED_CHEST, Material.CHEST, ProtocolVersionsHelper.BEFORE_1_5);
//			registerRemapEntry(Material.REDSTONE_BLOCK, Material.DIAMOND_BLOCK, ProtocolVersionsHelper.BEFORE_1_5);
//			registerRemapEntry(Material.ACTIVATOR_RAIL, Material.DETECTOR_RAIL, ProtocolVersionsHelper.BEFORE_1_5);
//			registerRemapEntry(Material.QUARTZ_ORE, Material.COAL_ORE, ProtocolVersionsHelper.BEFORE_1_5);
//			registerRemapEntry(Material.GOLD_PLATE, Material.STONE_PLATE, ProtocolVersionsHelper.BEFORE_1_5);
//			registerRemapEntry(Material.IRON_PLATE, Material.STONE_PLATE, ProtocolVersionsHelper.BEFORE_1_5);
//			registerRemapEntry(Material.REDSTONE_COMPARATOR_OFF, Material.DIODE_BLOCK_OFF, ProtocolVersionsHelper.BEFORE_1_5);
//			registerRemapEntry(Material.REDSTONE_COMPARATOR_ON, Material.DIODE_BLOCK_ON, ProtocolVersionsHelper.BEFORE_1_5);
//			//items remap
//			registerRemapEntry(Material.BED, Material.BED, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.KNOWLEDGE_BOOK, Material.BOOK, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.SHULKER_SHELL, Material.COBBLESTONE, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.TOTEM_OF_UNDYING, Material.COBBLESTONE, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.BEETROOT, Material.BROWN_MUSHROOM, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BEETROOT_SOUP, Material.MUSHROOM_STEW, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BEETROOT_SEEDS, Material.WHEAT_SEEDS, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.CHORUS_FRUIT, Material.POTATO, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.POPPED_CHORUS_FRUIT, Material.BAKED_POTATO, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.DRAGON_BREATH, Material.POTION, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.SPLASH_POTION, Material.POTION, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.LINGERING_POTION, Material.POTION, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.ELYTRA, Material.LEATHER_CHESTPLATE, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.END_CRYSTAL, Material.STONE, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.SHIELD, Material.WOODEN_SWORD, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.SPECTRAL_ARROW, Material.ARROW, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.TIPPED_ARROW, Material.ARROW, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.ACACIA_BOAT, Material.OAK_BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BIRCH_BOAT, Material.OAK_BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.DARK_OAK_BOAT, Material.OAK_BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.JUNGLE_BOAT, Material.OAK_BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.SPRUCE_BOAT, Material.OAK_BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.SPRUCE_DOOR, Material.OAK_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.BIRCH_DOOR, Material.OAK_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.JUNGLE_DOOR, Material.OAK_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.ACACIA_DOOR, Material.OAK_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.DARK_OAK_DOOR, Material.OAK_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.RABBIT, Material.CHICKEN, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.COOKED_RABBIT, Material.COOKED_CHICKEN, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.RABBIT_STEW, Material.MUSHROOM_STEW, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.MUTTON, Material.CHICKEN, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.COOKED_MUTTON, Material.COOKED_CHICKEN, ProtocolVersionsHelper.BEFORE_1_8);
//			registerRemapEntry(Material.BANNER, Material.SIGN, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.PRISMARINE_SHARD, Material.STONE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.PRISMARINE_CRYSTALS, Material.STONE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.RABBIT_FOOT, Material.STONE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.RABBIT_HIDE, Material.STONE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.ARMOR_STAND, Material.STONE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.IRON_HORSE_ARMOR, Material.LEATHER_CHESTPLATE, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.GOLDEN_HORSE_ARMOR, Material.LEATHER_CHESTPLATE, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.DIAMOND_HORSE_ARMOR, Material.LEATHER_CHESTPLATE, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.LEAD, Material.STONE, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.NAME_TAG, Material.STONE, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.TNT_MINECART, Material.MINECART, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.HOPPER_MINECART, Material.MINECART, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.COMPARATOR, Material.REPEATER, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.NETHER_BRICK, Material.CLAY_BALL, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.QUARTZ, Material.FEATHER, ProtocolVersionsHelper.BEFORE_1_5);
		}
		protected void registerRemapEntry(Material from, Material to, ProtocolVersion... versions) {
			registerRemapEntry(ItemMaterialLookup.getRuntimeId(from), ItemMaterialLookup.getRuntimeId(to), versions);
		}
		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(MinecraftData.ID_MAX);
		}
	}

}
