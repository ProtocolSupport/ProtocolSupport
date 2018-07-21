package protocolsupport.protocol.typeremapper.block;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Door.Hinge;
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

		public void applyDefaultRemaps() {
			remappings.clear();

			registerRemapEntry(Material.ACACIA_LEAVES, Material.ACACIA_LEAVES.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Material.DARK_OAK_LEAVES, Material.DARK_OAK_LEAVES.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Material.BIRCH_LEAVES, Material.BIRCH_LEAVES.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Material.JUNGLE_LEAVES, Material.JUNGLE_LEAVES.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Material.SPRUCE_LEAVES, Material.SPRUCE_LEAVES.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Material.OAK_LEAVES, Material.OAK_LEAVES.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Material.TALL_GRASS, Material.TALL_GRASS.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			for (Material wButton : Arrays.asList(
				Material.ACACIA_BUTTON, Material.BIRCH_BUTTON, Material.DARK_OAK_BUTTON,
				Material.JUNGLE_BUTTON, Material.OAK_BUTTON, Material.SPRUCE_BUTTON
			)) {
				for (BlockData blockdata : ServerPlatform.get().getMiscUtils().getBlockStates(wButton)) {
					Switch originalButton = (Switch) blockdata;
					Switch oakButton = (Switch) Material.OAK_BUTTON.createBlockData();
					oakButton.setFace(originalButton.getFace());
					if (originalButton.getFace() == Face.CEILING || originalButton.getFace() == Face.FLOOR) {
						oakButton.setFacing(BlockFace.NORTH);
					} else {
						oakButton.setFacing(originalButton.getFacing());
					}
					oakButton.setPowered(originalButton.isPowered());
					registerRemapEntry(originalButton, oakButton, ProtocolVersionsHelper.BEFORE_1_13);
				}
			}
			for (Material door : Arrays.asList(
				Material.ACACIA_DOOR, Material.BIRCH_DOOR, Material.DARK_OAK_DOOR,
				Material.JUNGLE_DOOR, Material.OAK_DOOR, Material.SPRUCE_DOOR,
				Material.IRON_DOOR
			)) {
				for (BlockData blockdata : ServerPlatform.get().getMiscUtils().getBlockStates(door)) {
					Door originalDoor = (Door) blockdata;
					Door cloneDoor = (Door) door.createBlockData();
					if (originalDoor.getHalf() == Half.TOP) {
						cloneDoor.setHalf(Half.TOP);
						cloneDoor.setHinge(originalDoor.getHinge());
						cloneDoor.setPowered(originalDoor.isPowered());
						cloneDoor.setFacing(BlockFace.EAST);
						cloneDoor.setOpen(false);
					} else if (originalDoor.getHalf() == Half.BOTTOM) {
						cloneDoor.setHalf(Half.BOTTOM);
						cloneDoor.setHinge(Hinge.RIGHT);
						cloneDoor.setPowered(false);
						cloneDoor.setFacing(originalDoor.getFacing());
						cloneDoor.setOpen(originalDoor.isOpen());
					}
					registerRemapEntry(originalDoor, cloneDoor, ProtocolVersionsHelper.BEFORE_1_13);
				}
			}

			registerRemapEntry(Material.ACACIA_LEAVES, Material.BIRCH_LEAVES.createBlockData(), ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(Material.DARK_OAK_LEAVES, Material.OAK_LEAVES.createBlockData(), ProtocolVersionsHelper.BEFORE_1_7);
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

		protected void registerRemapEntry(Material from, BlockData to, ProtocolVersion... versions) {
			ServerPlatform.get().getMiscUtils().getBlockStates(from)
			.forEach(blockdata -> registerRemapEntry(blockdata, to, versions));
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
