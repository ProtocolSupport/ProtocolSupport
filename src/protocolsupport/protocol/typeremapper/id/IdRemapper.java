package protocolsupport.protocol.typeremapper.id;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.remapper.BlockRemapperControl;
import protocolsupport.protocol.typeremapper.id.RemappingRegistry.GenericRemappingRegistry;
import protocolsupport.protocol.typeremapper.id.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.id.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.typeremapper.id.RemappingTable.GenericRemappingTable;
import protocolsupport.protocol.typeremapper.id.RemappingTable.HashMapBasedIdRemappingTable;
import protocolsupport.utils.ApacheCommonsUtils.ImmutablePair;
import protocolsupport.utils.ProtocolVersionsHelper;

public class IdRemapper {

	public static final IdRemappingRegistry<ArrayBasedIdRemappingTable> BLOCK = new IdRemappingRegistry<ArrayBasedIdRemappingTable>() {
		{
			registerRemapEntry(Material.IRON_NUGGET, Material.GOLD_NUGGET, ProtocolVersionsHelper.BEFORE_1_11_1);
			registerRemapEntry(Material.OBSERVER, Material.FURNACE, 2, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.WHITE_SHULKER_BOX, Material.FURNACE, 2, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.ORANGE_SHULKER_BOX, Material.FURNACE, 2, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.MAGENTA_SHULKER_BOX, Material.FURNACE, 2, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.LIGHT_BLUE_SHULKER_BOX, Material.FURNACE, 2, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.YELLOW_SHULKER_BOX, Material.FURNACE, 2, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.LIME_SHULKER_BOX, Material.FURNACE, 2, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.PINK_SHULKER_BOX, Material.FURNACE, 2, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.GRAY_SHULKER_BOX, Material.FURNACE, 2, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.SILVER_SHULKER_BOX, Material.FURNACE, 2, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.CYAN_SHULKER_BOX, Material.FURNACE, 2, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.PURPLE_SHULKER_BOX, Material.FURNACE, 2, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.BLUE_SHULKER_BOX, Material.FURNACE, 2, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.BROWN_SHULKER_BOX, Material.FURNACE, 2, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.GREEN_SHULKER_BOX, Material.FURNACE, 2, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.RED_SHULKER_BOX, Material.FURNACE, 2, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.BLACK_SHULKER_BOX, Material.FURNACE, 2, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.STRUCTURE_VOID, Material.GLASS, ProtocolVersionsHelper.BEFORE_1_10);
			registerRemapEntry(Material.NETHER_WART_BLOCK, Material.WOOL, 14, ProtocolVersionsHelper.BEFORE_1_10);
			registerRemapEntry(Material.RED_NETHER_BRICK, Material.NETHER_BRICK, ProtocolVersionsHelper.BEFORE_1_10);
			registerRemapEntry(Material.MAGMA, Material.NETHERRACK, ProtocolVersionsHelper.BEFORE_1_10);
			registerRemapEntry(Material.BONE_BLOCK, Material.BRICK, ProtocolVersionsHelper.BEFORE_1_10);
			registerRemapEntry(Material.COMMAND_CHAIN, Material.COMMAND, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.COMMAND_REPEATING, Material.COMMAND, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.CHORUS_FLOWER, Material.WOOD, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.CHORUS_PLANT, Material.WOOD, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.END_GATEWAY, Material.ENDER_PORTAL, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.END_ROD, Material.GLOWSTONE, 0, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.PURPUR_PILLAR, Material.STONE, 0, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.END_BRICKS, Material.ENDER_STONE, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.FROSTED_ICE, Material.ICE, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.GRASS_PATH, Material.SOIL, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.PURPUR_BLOCK, Material.STONE, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.PURPUR_STAIRS, Material.COBBLESTONE_STAIRS, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.PURPUR_SLAB, Material.STEP, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.PURPUR_DOUBLE_SLAB, Material.DOUBLE_STEP, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.STRUCTURE_BLOCK, Material.BEDROCK, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BEETROOT_BLOCK, Material.CROPS, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.SLIME_BLOCK, Material.EMERALD_BLOCK, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.BARRIER, Material.GLASS, ProtocolVersionsHelper.BEFORE_1_8);
			//TODO: remap with something that has more strength
			registerRemapEntry(Material.IRON_TRAPDOOR, Material.TRAP_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.PRISMARINE, Material.MOSSY_COBBLESTONE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.SEA_LANTERN, Material.GLOWSTONE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.STANDING_BANNER, Material.SIGN_POST, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.WALL_BANNER, Material.WALL_SIGN, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.RED_SANDSTONE, Material.SANDSTONE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.RED_SANDSTONE_STAIRS, Material.SANDSTONE_STAIRS, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.DOUBLE_STONE_SLAB2, Material.DOUBLE_STEP, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.STONE_SLAB2, Material.STEP, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.SPRUCE_FENCE_GATE, Material.FENCE_GATE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.BIRCH_FENCE_GATE, Material.FENCE_GATE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.JUNGLE_FENCE_GATE, Material.FENCE_GATE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.DARK_OAK_FENCE_GATE, Material.FENCE_GATE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.ACACIA_FENCE_GATE, Material.FENCE_GATE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.SPRUCE_FENCE, Material.FENCE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.BIRCH_FENCE, Material.FENCE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.JUNGLE_FENCE, Material.FENCE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.DARK_OAK_FENCE, Material.FENCE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.ACACIA_FENCE, Material.FENCE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.SPRUCE_DOOR, Material.WOODEN_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.BIRCH_DOOR, Material.WOODEN_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.JUNGLE_DOOR, Material.WOODEN_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.ACACIA_DOOR, Material.WOODEN_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.DARK_OAK_DOOR, Material.WOODEN_DOOR, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.DAYLIGHT_DETECTOR_INVERTED, Material.DAYLIGHT_DETECTOR, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.STAINED_GLASS, Material.GLASS, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(Material.STAINED_GLASS_PANE, Material.THIN_GLASS, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(Material.LEAVES_2, Material.LEAVES, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(Material.LOG_2, Material.LOG, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(Material.ACACIA_STAIRS, Material.WOOD_STAIRS, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(Material.DARK_OAK_STAIRS, Material.WOOD_STAIRS, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(Material.DOUBLE_PLANT, Material.YELLOW_FLOWER, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(Material.PACKED_ICE, Material.WOOL, 3, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(Material.STAINED_CLAY, Material.STONE, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.HAY_BLOCK, Material.STONE, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.CARPET, Material.SNOW, 0, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.HARD_CLAY, Material.STONE, 0, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.COAL_BLOCK, Material.OBSIDIAN, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.DROPPER, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.HOPPER, Material.FURNACE, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.QUARTZ, Material.STONE, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.QUARTZ_STAIRS, Material.SMOOTH_STAIRS, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.DAYLIGHT_DETECTOR_INVERTED, Material.STEP, 0, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.DAYLIGHT_DETECTOR, Material.STEP, 0, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.TRAPPED_CHEST, Material.CHEST, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.REDSTONE_BLOCK, Material.DIAMOND_BLOCK, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.ACTIVATOR_RAIL, Material.DETECTOR_RAIL, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.QUARTZ_ORE, Material.COAL_ORE, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.GOLD_PLATE, Material.STONE_PLATE, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.IRON_PLATE, Material.STONE_PLATE, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.REDSTONE_COMPARATOR_OFF, Material.DIODE_BLOCK_OFF, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.REDSTONE_COMPARATOR_ON, Material.DIODE_BLOCK_ON, ProtocolVersionsHelper.BEFORE_1_5);
		}
		@SuppressWarnings("deprecation")
		protected void registerRemapEntry(Material from, Material to, ProtocolVersion... versions) {
			for (int i = 0; i < 16; i++) {
				registerRemapEntry((from.getId() << 4) | i, (to.getId() << 4) | i, versions);
			}
		}
		@SuppressWarnings("deprecation")
		protected void registerRemapEntry(Material matFrom, Material matTo, int dataTo, ProtocolVersion... versions) {
			for (int i = 0; i < 16; i++) {
				registerRemapEntry((matFrom.getId() << 4) | i, (matTo.getId() << 4) | (dataTo & 0xF), versions);
			}
		}
		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(4096 * 16);
		}
	};

	@SuppressWarnings("deprecation")
	public static final IdRemappingRegistry<ArrayBasedIdRemappingTable> ITEM = new IdRemappingRegistry<ArrayBasedIdRemappingTable>() {
		{
			for (ProtocolVersion version : ProtocolVersion.values()) {
				if (version.isSupported()) {
					BlockRemapperControl ctrl = ProtocolSupportAPI.getBlockRemapper(version);
					for (int i = 0; i < 4096; i++) {
						registerRemapEntry(i, ctrl.getRemap(i), version);
					}
				}
			}
			registerRemapEntry(Material.SHULKER_SHELL, Material.COBBLESTONE, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.TOTEM, Material.COBBLESTONE, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.BEETROOT, Material.BROWN_MUSHROOM, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BEETROOT_SOUP, Material.MUSHROOM_SOUP, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BEETROOT_SEEDS, Material.SEEDS, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.CHORUS_FRUIT, Material.POTATO_ITEM, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.CHORUS_FRUIT_POPPED, Material.BAKED_POTATO, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.DRAGONS_BREATH, Material.POTION, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.SPLASH_POTION, Material.POTION, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.LINGERING_POTION, Material.POTION, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.ELYTRA, Material.LEATHER_CHESTPLATE, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.END_CRYSTAL, Material.STONE, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.SHIELD, Material.WOOD_SWORD, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.SPECTRAL_ARROW, Material.ARROW, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.TIPPED_ARROW, Material.ARROW, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BOAT_ACACIA, Material.BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BOAT_BIRCH, Material.BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BOAT_DARK_OAK, Material.BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BOAT_JUNGLE, Material.BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BOAT_SPRUCE, Material.BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			// all doors -> door
			registerRemapEntry(427, 324, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(428, 324, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(429, 324, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(430, 324, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(431, 324, ProtocolVersionsHelper.BEFORE_1_8);
			// rabbit raw meat -> chicken raw meat
			registerRemapEntry(411, 365, ProtocolVersionsHelper.BEFORE_1_8);
			// rabbit cooked meat -> chicken cooked meat
			registerRemapEntry(412, 366, ProtocolVersionsHelper.BEFORE_1_8);
			// rabbit stew -> mushroom stew
			registerRemapEntry(413, 282, ProtocolVersionsHelper.BEFORE_1_8);
			// raw mutton -> chicken raw meat
			registerRemapEntry(423, 365, ProtocolVersionsHelper.BEFORE_1_8);
			// cooked mutton -> chicken cooked meat
			registerRemapEntry(424, 366, ProtocolVersionsHelper.BEFORE_1_8);
			// banner -> sign
			registerRemapEntry(425, 323, ProtocolVersionsHelper.BEFORE_1_8);
			// everything else -> stone
			registerRemapEntry(409, 1, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(410, 1, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(414, 1, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(415, 1, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(416, 1, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(417, 1, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(418, 1, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(419, 1, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(420, 1, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(421, 1, ProtocolVersionsHelper.BEFORE_1_6);
			// minecarts -> default minecart
			registerRemapEntry(407, 328, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(408, 328, ProtocolVersionsHelper.BEFORE_1_5);
			// comparator -> repeater
			registerRemapEntry(404, 356, ProtocolVersionsHelper.BEFORE_1_5);
			// nether brick -> brick
			registerRemapEntry(405, 336, ProtocolVersionsHelper.BEFORE_1_5);
			// quartz -> feather
			registerRemapEntry(406, 288, ProtocolVersionsHelper.BEFORE_1_5);
		}
		private void registerRemapEntry(Material from, Material to, ProtocolVersion... versions) {
			registerRemapEntry(from.getId(), to.getId(), versions);
		}
		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(4096);
		}
	};

	public static final IdRemappingRegistry<ArrayBasedIdRemappingTable> ENTITY_LIVING = new IdRemappingRegistry<ArrayBasedIdRemappingTable>() {
		final class Mapping {
			private final EntityType from;
			private final ArrayList<ImmutablePair<EntityType, ProtocolVersion[]>> remaps = new ArrayList<>();
			public Mapping(EntityType from) {
				this.from = from;
			}
			public Mapping addMapping(EntityType to, ProtocolVersion... versions) {
				remaps.add(ImmutablePair.of(to, versions));
				return this;
			}
			public void register() {
				for (ImmutablePair<EntityType, ProtocolVersion[]> pair : remaps) {
					registerRemapEntry(from, pair.getLeft(), pair.getRight());
				}
			}
		}
		{
			new Mapping(EntityType.VINDICATOR)
			.addMapping(EntityType.WITCH, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(EntityType.EVOKER)
			.addMapping(EntityType.WITCH, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(EntityType.VEX)
			.addMapping(EntityType.BLAZE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(EntityType.ZOMBIE_VILLAGER)
			.addMapping(EntityType.ZOMBIE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(EntityType.HUSK)
			.addMapping(EntityType.ZOMBIE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(EntityType.SKELETON_HORSE)
			.addMapping(EntityType.HORSE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(EntityType.COW, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(EntityType.ZOMBIE_HORSE)
			.addMapping(EntityType.HORSE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(EntityType.COW, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(EntityType.DONKEY)
			.addMapping(EntityType.HORSE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(EntityType.COW, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(EntityType.MULE)
			.addMapping(EntityType.HORSE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(EntityType.COW, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(EntityType.LLAMA)
			.addMapping(EntityType.HORSE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(EntityType.COW, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(EntityType.WITHER_SKELETON)
			.addMapping(EntityType.SKELETON, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(EntityType.STRAY)
			.addMapping(EntityType.SKELETON, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(EntityType.POLAR_BEAR)
			.addMapping(EntityType.SPIDER, ProtocolVersionsHelper.BEFORE_1_10)
			.register();
			new Mapping(EntityType.SHULKER)
			.addMapping(EntityType.BLAZE, ProtocolVersionsHelper.BEFORE_1_9)
			.register();
			new Mapping(EntityType.ENDERMITE)
			.addMapping(EntityType.SILVERFISH, ProtocolVersionsHelper.BEFORE_1_8)
			.register();
			new Mapping(EntityType.RABBIT)
			.addMapping(EntityType.CHICKEN, ProtocolVersionsHelper.BEFORE_1_8)
			.register();
			new Mapping(EntityType.ELDER_GUARDIAN)
			.addMapping(EntityType.GUARDIAN, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_10))
			.addMapping(EntityType.SQUID, ProtocolVersionsHelper.BEFORE_1_8)
			.register();
			new Mapping(EntityType.GUARDIAN)
			.addMapping(EntityType.SQUID, ProtocolVersionsHelper.BEFORE_1_8)
			.register();
			new Mapping(EntityType.HORSE)
			.addMapping(EntityType.COW, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
		}
		@SuppressWarnings("deprecation")
		private void registerRemapEntry(EntityType from, EntityType to, ProtocolVersion... versions) {
			registerRemapEntry(from.getTypeId(), to.getTypeId(), versions);
		}
		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(256);
		}
	};

	public static final IdRemappingRegistry<ArrayBasedIdRemappingTable> ENTITY_OBJECT = new IdRemappingRegistry<ArrayBasedIdRemappingTable>() {
		{
			//evocation fangs -> firecharge
			registerRemapEntry(79, 64, ProtocolVersionsHelper.BEFORE_1_11);
			//lama spit -> snowball
			registerRemapEntry(68, 61, ProtocolVersionsHelper.BEFORE_1_11);
			//shulker bullet -> firecharge
			registerRemapEntry(67, 64, ProtocolVersionsHelper.BEFORE_1_9);
			//dragon fireball -> firecharge
			registerRemapEntry(93, 64, ProtocolVersionsHelper.BEFORE_1_9);
			//spectral arrow -> arrow
			registerRemapEntry(91, 60, ProtocolVersionsHelper.BEFORE_1_9);
			//tipped arrow -> arrow
			registerRemapEntry(92, 60, ProtocolVersionsHelper.BEFORE_1_9);
		}
		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(256);
		}
	};

	public static final IdRemappingRegistry<ArrayBasedIdRemappingTable> MAPCOLOR = new IdRemappingRegistry<ArrayBasedIdRemappingTable>() {
		{
			//see http://minecraft.gamepedia.com/Map_item_format (i don't event know a names for half of those colors)
			registerRemapEntry(14, 8, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(15, 10, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(16, 5, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(17, 5, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(18, 2, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(19, 1, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(20, 4, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(21, 11, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(22, 11, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(23, 5, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(24, 5, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(25, 5, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(26, 10, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(27, 7, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(28, 4, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(29, 11, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(30, 2, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(31, 5, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(32, 5, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(33, 7, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(34, 10, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(35, 4, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(36, 10, ProtocolVersionsHelper.BEFORE_1_7);
		}
		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(64) {
				@Override
				public int getRemap(int id) {
					int realColor = (id & 0xFF) >> 2;
					return ((table[realColor] << 2) + (id & 0b11));
				}
			};
		}
	};

	public static final IdRemappingRegistry<HashMapBasedIdRemappingTable> EFFECT = new IdRemappingRegistry<HashMapBasedIdRemappingTable>() {
		@Override
		protected HashMapBasedIdRemappingTable createTable() {
			return new HashMapBasedIdRemappingTable();
		}
	};

	public static final GenericRemappingRegistry<String, GenericRemappingTable<String>> INVENTORY = new GenericRemappingRegistry<String, GenericRemappingTable<String>>() {
		{
			registerRemapEntry("minecraft:shulker_box", "minecraft:chest", ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry("minecraft:dropper", "minecraft:dispenser", ProtocolVersionsHelper.BEFORE_1_5);
		}
		@Override
		protected GenericRemappingTable<String> createTable() {
			return new GenericRemappingTable<>();
		}
	};

	public static int fixDimensionId(int dimensionId) {
		if ((dimensionId > 1) || (dimensionId < -1)) {
			return 0;
		}
		return dimensionId;
	}


	public static void init() {
	}

}
