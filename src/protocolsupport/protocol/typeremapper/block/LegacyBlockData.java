package protocolsupport.protocol.typeremapper.block;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Door.Hinge;
import org.bukkit.block.data.type.Fence;
import org.bukkit.block.data.type.Gate;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.Switch.Face;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.zplatform.ServerPlatform;

public class LegacyBlockData {

	public static final BlockIdRemappingRegistry REGISTRY = new BlockIdRemappingRegistry();

	public static class BlockIdRemappingRegistry extends IdRemappingRegistry<ArrayBasedIdRemappingTable> {

		{
			applyDefaultRemaps();
		}

		protected Gate toPre13GateState(Gate from, Gate to) {
			to.setInWall(false);
			to.setFacing(from.getFacing());
			to.setOpen(from.isOpen());
			to.setPowered(from.isPowered());
			return to;
		}

		protected Fence toPre13FenceState(Fence from, Fence to) {
			to.setWaterlogged(false);
			from.getAllowedFaces().stream().forEach(face -> to.setFace(face, from.hasFace(face)));
			return to;
		}

		protected Switch toPre13ButtonState(Switch from, Switch to) {
			to.setFace(from.getFace());
			if (from.getFace() == Face.CEILING || from.getFace() == Face.FLOOR) {
				to.setFacing(BlockFace.NORTH);
			} else {
				to.setFacing(from.getFacing());
			}
			to.setPowered(from.isPowered());
			return to;
		}

		protected Door toPre13DoorState(Door from, Door to) {
			if (from.getHalf() == Half.TOP) {
				to.setHalf(Half.TOP);
				to.setHinge(from.getHinge());
				to.setPowered(from.isPowered());
				to.setFacing(BlockFace.EAST);
				to.setOpen(false);
			} else if (from.getHalf() == Half.BOTTOM) {
				to.setHalf(Half.BOTTOM);
				to.setHinge(Hinge.RIGHT);
				to.setPowered(false);
				to.setFacing(from.getFacing());
				to.setOpen(from.isOpen());
			}
			return to;
		}

		protected Slab toPre13SlabState(Slab from, Slab to) {
			to.setWaterlogged(false);
			to.setType(from.getType());
			return to;
		}

		public void applyDefaultRemaps() {
			remappings.clear();


			this.registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_LEAVES, Material.DARK_OAK_LEAVES, Material.BIRCH_LEAVES,
					Material.JUNGLE_LEAVES, Material.SPRUCE_LEAVES, Material.OAK_LEAVES,
					Material.ACACIA_FENCE, Material.DARK_OAK_FENCE, Material.BIRCH_FENCE,
					Material.JUNGLE_FENCE, Material.SPRUCE_FENCE, Material.OAK_FENCE,
					Material.TALL_GRASS
				),
				o -> o.getMaterial().createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_13
			);

			this.<Gate>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_FENCE_GATE, Material.DARK_OAK_FENCE_GATE, Material.BIRCH_FENCE_GATE,
					Material.JUNGLE_FENCE_GATE, Material.OAK_FENCE_GATE, Material.SPRUCE_FENCE_GATE
				),
				o -> toPre13GateState(o, (Gate) o.clone()),
				ProtocolVersionsHelper.BEFORE_1_13
			);

			this.<Switch>registerRemapEntryForAllStates(
				Material.STONE_BUTTON,
				o -> toPre13ButtonState(o, (Switch) o.clone()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Switch>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_BUTTON, Material.DARK_OAK_BUTTON, Material.BIRCH_BUTTON,
					Material.JUNGLE_BUTTON, Material.OAK_BUTTON, Material.SPRUCE_BUTTON
				),
				o -> toPre13ButtonState(o, (Switch) Material.OAK_BUTTON.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);

			this.<Door>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_DOOR, Material.DARK_OAK_DOOR, Material.BIRCH_DOOR,
					Material.JUNGLE_DOOR, Material.OAK_DOOR, Material.SPRUCE_DOOR,
					Material.IRON_DOOR
				),
				o -> toPre13DoorState(o, (Door) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);

			this.<Slab>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_SLAB, Material.DARK_OAK_SLAB, Material.BIRCH_SLAB,
					Material.JUNGLE_SLAB, Material.OAK_SLAB, Material.SPRUCE_SLAB,
					Material.COBBLESTONE_SLAB, Material.SANDSTONE_SLAB
				),
				o -> toPre13SlabState(o, (Slab) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Slab>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.PRISMARINE_BRICK_SLAB, Material.PRISMARINE_SLAB, Material.DARK_PRISMARINE_SLAB,
					Material.PETRIFIED_OAK_SLAB
				),
				o -> toPre13SlabState(o, (Slab) Material.STONE_SLAB.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);


			this.registerRemapEntryForAllStates(Material.ACACIA_LEAVES, Material.BIRCH_LEAVES.createBlockData(), ProtocolVersionsHelper.BEFORE_1_7);
			this.registerRemapEntryForAllStates(Material.DARK_OAK_LEAVES, Material.OAK_LEAVES.createBlockData(), ProtocolVersionsHelper.BEFORE_1_7);
//TODO: update to new material enum
//			remappings.clear();
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
		}

		protected void registerRemapEntryForAllStates(Material from, BlockData to, ProtocolVersion... versions) {
			ServerPlatform.get().getMiscUtils().getBlockStates(from)
			.forEach(blockdata -> registerRemapEntry(blockdata, to, versions));
		}

		protected <T extends BlockData> void registerRemapEntryForAllStates(List<Material> materials, Function<T, BlockData> remapFunc, ProtocolVersion... versions) {
			for (Material material : materials) {
				registerRemapEntryForAllStates(material, remapFunc, versions);
			}
		}

		@SuppressWarnings("unchecked")
		protected <T extends BlockData> void registerRemapEntryForAllStates(Material material, Function<T, BlockData> remapFunc, ProtocolVersion... versions) {
			for (BlockData blockdata : ServerPlatform.get().getMiscUtils().getBlockStates(material)) {
				registerRemapEntry(blockdata, remapFunc.apply((T) blockdata), versions);
			}
		}

		protected void registerRemapEntry(BlockData from, BlockData to, ProtocolVersion... versions) {
			registerRemapEntry(
				ServerPlatform.get().getMiscUtils().getNetworkBlockStateId(from),
				ServerPlatform.get().getMiscUtils().getNetworkBlockStateId(to),
				versions
			);
		}

		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(MinecraftData.ID_MAX);
		}
	}

}
