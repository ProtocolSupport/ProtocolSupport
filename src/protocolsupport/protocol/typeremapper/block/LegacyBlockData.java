package protocolsupport.protocol.typeremapper.block;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.Chest.Type;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Door.Hinge;
import org.bukkit.block.data.type.EnderChest;
import org.bukkit.block.data.type.Fire;
import org.bukkit.block.data.type.Gate;
import org.bukkit.block.data.type.PistonHead;
import org.bukkit.block.data.type.RedstoneWire;
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

		protected Chest toPre13ChestState(Chest from, Chest to) {
			to.setWaterlogged(false);
			to.setType(Type.SINGLE);
			to.setFacing(from.getFacing());
			return to;
		}

		protected EnderChest toPre13EnderChestState(EnderChest from, EnderChest to) {
			to.setWaterlogged(false);
			to.setFacing(from.getFacing());
			return to;
		}

		protected PistonHead toPre13PistonHeadState(PistonHead from, PistonHead to) {
			to.setShort(false);
			to.setFacing(from.getFacing());
			to.setType(from.getType());
			return to;
		}

		protected Fire toPre13FireState(Fire from, Fire to) {
			to.setAge(from.getAge());
			from.getAllowedFaces().forEach(face -> to.setFace(face, false));
			return to;
		}

		protected RedstoneWire toPre13RedstoneWireState(RedstoneWire from, RedstoneWire to) {
			to.setPower(from.getPower());
			from.getAllowedFaces().forEach(face -> to.setFace(face, RedstoneWire.Connection.NONE));
			return to;
		}

		protected Bed cloneBed(Bed from, Bed to) {
			to.setFacing(from.getFacing());
			to.setPart(from.getPart());
			//TODO: clone isOccupied
			return to;
		}

		protected Rotatable cloneRotatable(Rotatable from, Rotatable to) {
			to.setRotation(from.getRotation());
			return to;
		}

		protected Directional cloneDirectional(Directional from, Directional to) {
			to.setFacing(from.getFacing());
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
					Material.COBBLESTONE_WALL, Material.MOSSY_COBBLESTONE_WALL,
					Material.TALL_GRASS, Material.BROWN_MUSHROOM_BLOCK, Material.GLASS_PANE,
					Material.BLACK_STAINED_GLASS_PANE, Material.BLUE_STAINED_GLASS_PANE, Material.BROWN_STAINED_GLASS_PANE, Material.CYAN_STAINED_GLASS_PANE,
					Material.GRAY_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE, Material.LIGHT_BLUE_STAINED_GLASS_PANE, Material.LIGHT_GRAY_STAINED_GLASS_PANE,
					Material.LIME_STAINED_GLASS_PANE, Material.MAGENTA_STAINED_GLASS_PANE, Material.ORANGE_STAINED_GLASS_PANE, Material.PINK_STAINED_GLASS_PANE,
					Material.PURPLE_STAINED_GLASS_PANE, Material.RED_STAINED_GLASS_PANE, Material.WHITE_STAINED_GLASS_PANE, Material.YELLOW_STAINED_GLASS_PANE,
					Material.CHORUS_PLANT, Material.MUSHROOM_STEW,
					Material.CREEPER_HEAD, Material.CREEPER_WALL_HEAD,
					Material.ZOMBIE_HEAD, Material.ZOMBIE_WALL_HEAD,
					Material.SKELETON_SKULL, Material.SKELETON_WALL_SKULL,
					Material.WITHER_SKELETON_SKULL, Material.WITHER_SKELETON_WALL_SKULL,
					Material.PLAYER_HEAD, Material.PLAYER_WALL_HEAD,
					Material.GRASS_BLOCK, Material.MYCELIUM, Material.PODZOL
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

			this.<Bed>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.BLACK_BED, Material.BLUE_BED, Material.BROWN_BED, Material.CYAN_BED,
					Material.GRAY_BED, Material.GREEN_BED, Material.LIGHT_BLUE_BED, Material.LIGHT_GRAY_BED,
					Material.LIME_BED, Material.MAGENTA_BED, Material.ORANGE_BED, Material.PINK_BED,
					Material.PURPLE_BED, Material.RED_BED, Material.WHITE_BED, Material.YELLOW_BED
				),
				o -> cloneBed(o, (Bed) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);

			this.<Rotatable>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.BLACK_BANNER, Material.BLUE_BANNER, Material.BROWN_BANNER, Material.CYAN_BANNER,
					Material.GRAY_BANNER, Material.GREEN_BANNER, Material.LIGHT_BLUE_BANNER, Material.LIGHT_GRAY_BANNER,
					Material.LIME_BANNER, Material.MAGENTA_BANNER, Material.ORANGE_BANNER, Material.PINK_BANNER,
					Material.PURPLE_BANNER, Material.RED_BANNER, Material.WHITE_BANNER, Material.YELLOW_BANNER
				),
				o -> cloneRotatable(o, (Rotatable) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);

			this.<Directional>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.BLACK_WALL_BANNER, Material.BLUE_WALL_BANNER, Material.BROWN_WALL_BANNER, Material.CYAN_WALL_BANNER,
					Material.GRAY_WALL_BANNER, Material.GREEN_WALL_BANNER, Material.LIGHT_BLUE_WALL_BANNER, Material.LIGHT_GRAY_WALL_BANNER,
					Material.LIME_WALL_BANNER, Material.MAGENTA_WALL_BANNER, Material.ORANGE_WALL_BANNER, Material.PINK_WALL_BANNER,
					Material.PURPLE_WALL_BANNER, Material.RED_WALL_BANNER, Material.WHITE_WALL_BANNER, Material.YELLOW_WALL_BANNER
				),
				o -> cloneDirectional(o, (Directional) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);

			this.<Chest>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.CHEST, Material.TRAPPED_CHEST
				),
				o -> toPre13ChestState(o, (Chest) o.clone()),
				ProtocolVersionsHelper.BEFORE_1_13
			);

			this.<EnderChest>registerRemapEntryForAllStates(
				Material.ENDER_CHEST,
				o -> toPre13EnderChestState(o, (EnderChest) o.clone()),
				ProtocolVersionsHelper.BEFORE_1_13
			);

			this.<PistonHead>registerRemapEntryForAllStates(
				Material.PISTON_HEAD,
				o -> toPre13PistonHeadState(o, (PistonHead) o.clone()),
				ProtocolVersionsHelper.BEFORE_1_13
			);

			this.<Fire>registerRemapEntryForAllStates(
				Material.FIRE,
				o -> toPre13FireState(o, (Fire) o.clone()),
				ProtocolVersionsHelper.BEFORE_1_13
			);

			this.<RedstoneWire>registerRemapEntryForAllStates(
				Material.REDSTONE_WIRE,
				o -> toPre13RedstoneWireState(o, (RedstoneWire) o.clone()),
				ProtocolVersionsHelper.BEFORE_1_13
			);

			this.registerRemapEntryForAllStates(
				Arrays.asList(
					Material.CAVE_AIR, Material.VOID_AIR
				),
				Material.AIR.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			//TODO: remap new 1.13 blocks

			this.registerRemapEntryForAllStates(Material.ACACIA_LEAVES, Material.BIRCH_LEAVES.createBlockData(), ProtocolVersionsHelper.BEFORE_1_7);
			this.registerRemapEntryForAllStates(Material.DARK_OAK_LEAVES, Material.OAK_LEAVES.createBlockData(), ProtocolVersionsHelper.BEFORE_1_7);

			//TODO: port old remap list
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
