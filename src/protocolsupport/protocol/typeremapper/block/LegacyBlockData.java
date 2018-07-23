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
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.Stairs.Shape;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.Switch.Face;
import org.bukkit.block.data.type.TrapDoor;

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

		protected Stairs toPre13StairsState(Stairs from, Stairs to) {
			to.setShape(Shape.STRAIGHT);
			to.setWaterlogged(false);
			to.setFacing(from.getFacing());
			//to.setHalf(from.getHalf()); TODO: wait for spigot fix.
			return to;
		}

		protected TrapDoor toPre13TrapDoor(TrapDoor from, TrapDoor to) {
			to.setWaterlogged(false);
			to.setPowered(false);
			to.setFacing(from.getFacing());
			to.setOpen(from.isOpen());
			//to.setHalf(from.getHalf()); TODO: wait for spigot fix.
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
					Material.COBBLESTONE_SLAB, Material.SANDSTONE_SLAB, Material.RED_SANDSTONE_SLAB
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

			this.registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE,
					Material.JUNGLE_PRESSURE_PLATE, Material.OAK_PRESSURE_PLATE, Material.SPRUCE_PRESSURE_PLATE
				),
				Material.OAK_PRESSURE_PLATE.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_13
			);

			this.<Stairs>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_STAIRS, Material.DARK_OAK_STAIRS, Material.BIRCH_STAIRS,
					Material.JUNGLE_STAIRS, Material.OAK_STAIRS, Material.SPRUCE_STAIRS,
					Material.COBBLESTONE_STAIRS, Material.STONE_BRICK_STAIRS,
					Material.SANDSTONE_STAIRS, Material.RED_SANDSTONE_STAIRS,
					Material.QUARTZ_STAIRS, Material.NETHER_BRICK_STAIRS, Material.PURPUR_STAIRS
				),
				o -> toPre13StairsState(o, (Stairs) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Stairs>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.PRISMARINE_BRICK_STAIRS, Material.PRISMARINE_STAIRS, Material.DARK_PRISMARINE_STAIRS
				),
				o -> toPre13StairsState(o, (Stairs) Material.STONE_BRICK_STAIRS.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);

			this.<TrapDoor>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_TRAPDOOR, Material.DARK_OAK_TRAPDOOR, Material.BIRCH_TRAPDOOR,
					Material.JUNGLE_TRAPDOOR, Material.OAK_TRAPDOOR, Material.SPRUCE_TRAPDOOR
				),
				o -> toPre13TrapDoor(o, (TrapDoor) Material.OAK_TRAPDOOR.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<TrapDoor>registerRemapEntryForAllStates(
				Material.IRON_TRAPDOOR,
				o -> toPre13TrapDoor(o, (TrapDoor) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);

			this.registerRemapEntryForAllStates(
					Arrays.asList(
							Material.CAVE_AIR, Material.VOID_AIR
					),
					Material.AIR.createBlockData(),
					ProtocolVersionsHelper.BEFORE_1_13
			);

			this.registerRemapEntryForAllStates(Material.ACACIA_LEAVES, Material.BIRCH_LEAVES.createBlockData(), ProtocolVersionsHelper.BEFORE_1_7);
			this.registerRemapEntryForAllStates(Material.DARK_OAK_LEAVES, Material.OAK_LEAVES.createBlockData(), ProtocolVersionsHelper.BEFORE_1_7);

			//TODO: complete remap list
		}

		protected void registerRemapEntryForAllStates(List<Material> materials, BlockData to, ProtocolVersion... versions) {
			for (Material material : materials) {
				registerRemapEntryForAllStates(material, to, versions);
			}
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
