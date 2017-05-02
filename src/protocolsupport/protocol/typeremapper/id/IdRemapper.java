package protocolsupport.protocol.typeremapper.id;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.RemappingRegistry.GenericRemappingRegistry;
import protocolsupport.protocol.typeremapper.id.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.id.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.typeremapper.id.RemappingTable.GenericRemappingTable;
import protocolsupport.protocol.typeremapper.id.RemappingTable.HashMapBasedIdRemappingTable;
import protocolsupport.utils.ProtocolVersionsHelper;

public class IdRemapper {

	public static final IdRemappingRegistry<ArrayBasedIdRemappingTable> BLOCK = new IdRemappingRegistry<ArrayBasedIdRemappingTable>() {

		private static final int DATA_MAX = 16;

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
			for (int i = 0; i < DATA_MAX; i++) {
				int newdata = (i & 0x8) == 1 ? 1 : 0;
				registerRemapEntry(Material.COMMAND_CHAIN, i, Material.COMMAND, newdata, ProtocolVersionsHelper.BEFORE_1_9);
				registerRemapEntry(Material.COMMAND_REPEATING, i, Material.COMMAND, newdata, ProtocolVersionsHelper.BEFORE_1_9);
				registerRemapEntry(Material.COMMAND, i, Material.COMMAND, newdata, ProtocolVersionsHelper.BEFORE_1_9);
			}
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
			registerRemapEntry(Material.QUARTZ_BLOCK, Material.STONE, ProtocolVersionsHelper.BEFORE_1_5);
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
		protected void registerRemapEntry(Material from, Material to, ProtocolVersion... versions) {
			for (int i = 0; i < DATA_MAX; i++) {
				registerRemapEntry(from, i, to, i, versions);
			}
		}
		protected void registerRemapEntry(Material matFrom, Material matTo, int dataTo, ProtocolVersion... versions) {
			for (int i = 0; i < DATA_MAX; i++) {
				registerRemapEntry(matFrom, i, matTo, dataTo, versions);
			}
		}
		@SuppressWarnings("deprecation")
		protected void registerRemapEntry(Material matFrom, int dataFrom, Material matTo, int dataTo, ProtocolVersion... versions) {
			registerRemapEntry((matFrom.getId() << 4) | dataFrom, (matTo.getId() << 4) | (dataTo & 0xF), versions);
		}
		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(4096 * DATA_MAX);
		}
	};

	@SuppressWarnings("deprecation")
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
