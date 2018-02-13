package protocolsupport.protocol.typeremapper.id;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.Material;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.pe.PELevelEvent;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.EnumRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.EnumRemappingTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.HashMapBasedIdRemappingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.protocol.utils.types.WindowType;

public class IdRemapper {

	public static final BlockIdDataRemappingRegistry BLOCK = new BlockIdDataRemappingRegistry();

	public static class BlockIdDataRemappingRegistry extends IdRemappingRegistry<ArrayBasedIdRemappingTable> {
		{
			applyDefaultRemaps();
		}
		public void applyDefaultRemaps() {
			remappings.clear();
			registerRemapEntry(Material.CONCRETE, Material.BRICK, 0, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.CONCRETE_POWDER, Material.WOOL, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.WHITE_GLAZED_TERRACOTTA, Material.BRICK, 0, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.ORANGE_GLAZED_TERRACOTTA, Material.BRICK, 0, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.MAGENTA_GLAZED_TERRACOTTA, Material.BRICK, 0, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, Material.BRICK, 0, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.YELLOW_GLAZED_TERRACOTTA, Material.BRICK, 0, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.LIME_GLAZED_TERRACOTTA, Material.BRICK, 0, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.PINK_GLAZED_TERRACOTTA, Material.BRICK, 0, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.GRAY_GLAZED_TERRACOTTA, Material.BRICK, 0, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.SILVER_GLAZED_TERRACOTTA, Material.BRICK, 0, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.CYAN_GLAZED_TERRACOTTA, Material.BRICK, 0, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.PURPLE_GLAZED_TERRACOTTA, Material.BRICK, 0, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.BLUE_GLAZED_TERRACOTTA, Material.BRICK, 0, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.BROWN_GLAZED_TERRACOTTA, Material.BRICK, 0, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.GREEN_GLAZED_TERRACOTTA, Material.BRICK, 0, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.RED_GLAZED_TERRACOTTA, Material.BRICK, 0, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.BLACK_GLAZED_TERRACOTTA, Material.BRICK, 0, ProtocolVersionsHelper.BEFORE_1_12);
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
			registerRemapEntry(Material.BONE_BLOCK, Material.BRICK, 0, ProtocolVersionsHelper.BEFORE_1_10);
			for (int i = 0; i < MinecraftData.BLOCK_DATA_MAX; i++) {
				int newdata = (i & 0x8) == 0x8 ? 1 : 0;
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
			for (int i = 0; i < MinecraftData.BLOCK_DATA_MAX; i++) {
				registerRemapEntry(from, i, to, i, versions);
			}
		}
		protected void registerRemapEntry(Material matFrom, Material matTo, int dataTo, ProtocolVersion... versions) {
			for (int i = 0; i < MinecraftData.BLOCK_DATA_MAX; i++) {
				registerRemapEntry(matFrom, i, matTo, dataTo, versions);
			}
		}
		@SuppressWarnings("deprecation")
		protected void registerRemapEntry(Material matFrom, int dataFrom, Material matTo, int dataTo, ProtocolVersion... versions) {
			registerRemapEntry(MinecraftData.getBlockStateFromIdAndData(matFrom.getId(), dataFrom), MinecraftData.getBlockStateFromIdAndData(matTo.getId(), dataTo), versions);
		}
		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(MinecraftData.BLOCK_ID_MAX * MinecraftData.BLOCK_DATA_MAX);
		}
	}

	public static final EnumRemappingRegistry<NetworkEntityType, EnumRemappingTable<NetworkEntityType>> ENTITY = new EnumRemappingRegistry<NetworkEntityType, EnumRemappingTable<NetworkEntityType>>() {
		final class Mapping {
			private final NetworkEntityType from;
			private final ArrayList<ImmutablePair<NetworkEntityType, ProtocolVersion[]>> remaps = new ArrayList<>();
			public Mapping(NetworkEntityType from) {
				this.from = from;
			}
			public Mapping addMapping(NetworkEntityType to, ProtocolVersion... versions) {
				remaps.add(ImmutablePair.of(to, versions));
				return this;
			}
			public void register() {
				for (ImmutablePair<NetworkEntityType, ProtocolVersion[]> pair : remaps) {
					registerRemapEntry(from, pair.getLeft(), pair.getRight());
				}
			}
		}
		{
			new Mapping(NetworkEntityType.PARROT)
			.addMapping(NetworkEntityType.OCELOT, ProtocolVersionsHelper.BEFORE_1_12)
			.register();
			new Mapping(NetworkEntityType.ILLUSIONER)
			.addMapping(NetworkEntityType.WITCH, ProtocolVersionsHelper.BEFORE_1_12)
			.register();
			new Mapping(NetworkEntityType.VINDICATOR)
			.addMapping(NetworkEntityType.WITCH, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.EVOKER)
			.addMapping(NetworkEntityType.WITCH, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.VEX)
			.addMapping(NetworkEntityType.BLAZE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.ZOMBIE_VILLAGER)
			.addMapping(NetworkEntityType.ZOMBIE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.HUSK)
			.addMapping(NetworkEntityType.ZOMBIE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.EVOCATOR_FANGS)
			.addMapping(NetworkEntityType.FIRECHARGE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.LAMA_SPIT)
			.addMapping(NetworkEntityType.SNOWBALL, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.SKELETON_HORSE)
			.addMapping(NetworkEntityType.COMMON_HORSE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(NetworkEntityType.ZOMBIE_HORSE)
			.addMapping(NetworkEntityType.COMMON_HORSE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(NetworkEntityType.DONKEY)
			.addMapping(NetworkEntityType.COMMON_HORSE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(NetworkEntityType.MULE)
			.addMapping(NetworkEntityType.COMMON_HORSE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(NetworkEntityType.LAMA)
			.addMapping(NetworkEntityType.COMMON_HORSE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(NetworkEntityType.WITHER_SKELETON)
			.addMapping(NetworkEntityType.SKELETON, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.STRAY)
			.addMapping(NetworkEntityType.SKELETON, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.POLAR_BEAR)
			.addMapping(NetworkEntityType.SPIDER, ProtocolVersionsHelper.BEFORE_1_10)
			.register();
			new Mapping(NetworkEntityType.SHULKER)
			.addMapping(NetworkEntityType.BLAZE, ProtocolVersionsHelper.BEFORE_1_9)
			.register();
			new Mapping(NetworkEntityType.SHULKER_BULLET)
			.addMapping(NetworkEntityType.FIRECHARGE, ProtocolVersionsHelper.BEFORE_1_9)
			.register();
			new Mapping(NetworkEntityType.DRAGON_FIREBALL)
			.addMapping(NetworkEntityType.FIRECHARGE, ProtocolVersionsHelper.BEFORE_1_9)
			.register();
			new Mapping(NetworkEntityType.SPECTRAL_ARROW)
			.addMapping(NetworkEntityType.ARROW, ProtocolVersionsHelper.concat(ProtocolVersionsHelper.BEFORE_1_9, ProtocolVersion.MINECRAFT_PE))
			.register();
			new Mapping(NetworkEntityType.TIPPED_ARROW)
			.addMapping(NetworkEntityType.ARROW, ProtocolVersionsHelper.concat(ProtocolVersionsHelper.BEFORE_1_9, ProtocolVersion.MINECRAFT_PE))
			.register();
			new Mapping(NetworkEntityType.ENDERMITE)
			.addMapping(NetworkEntityType.SILVERFISH, ProtocolVersionsHelper.BEFORE_1_8)
			.register();
			new Mapping(NetworkEntityType.RABBIT)
			.addMapping(NetworkEntityType.CHICKEN, ProtocolVersionsHelper.BEFORE_1_8)
			.register();
			new Mapping(NetworkEntityType.ELDER_GUARDIAN)
			.addMapping(NetworkEntityType.GUARDIAN, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_10))
			.addMapping(NetworkEntityType.SQUID, ProtocolVersionsHelper.BEFORE_1_8)
			.register();
			new Mapping(NetworkEntityType.GUARDIAN)
			.addMapping(NetworkEntityType.SQUID, ProtocolVersionsHelper.BEFORE_1_8)
			.register();
			new Mapping(NetworkEntityType.COMMON_HORSE)
			.addMapping(NetworkEntityType.COW, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
		}
		@Override
		protected EnumRemappingTable<NetworkEntityType> createTable() {
			return new EnumRemappingTable<>(NetworkEntityType.class);
		}
	};

	//TODO: this doesn't belong here, should be moved to PEDataValues. TODO: Why? You can add other versions too :)
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

	public static final IdRemappingRegistry<HashMapBasedIdRemappingTable> EFFECT = new IdRemappingRegistry<HashMapBasedIdRemappingTable>() {
		@Override
		protected HashMapBasedIdRemappingTable createTable() {
			return new HashMapBasedIdRemappingTable();
		}
	};

	public static final EnumRemappingRegistry<WindowType, EnumRemappingTable<WindowType>> INVENTORY = new EnumRemappingRegistry<WindowType, EnumRemappingTable<WindowType>>() {
		{
			registerRemapEntry(WindowType.SHULKER, WindowType.CHEST, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(WindowType.DROPPER, WindowType.DISPENSER, ProtocolVersionsHelper.BEFORE_1_5);
		}
		@Override
		protected EnumRemappingTable<WindowType> createTable() {
			return new EnumRemappingTable<>(WindowType.class);
		}
	};

}
