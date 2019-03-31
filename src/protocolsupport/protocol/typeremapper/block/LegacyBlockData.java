package protocolsupport.protocol.typeremapper.block;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.CommandBlock;
import org.bukkit.block.data.type.Comparator;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Door.Hinge;
import org.bukkit.block.data.type.EnderChest;
import org.bukkit.block.data.type.Fire;
import org.bukkit.block.data.type.Gate;
import org.bukkit.block.data.type.Observer;
import org.bukkit.block.data.type.PistonHead;
import org.bukkit.block.data.type.RedstoneWire;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.Stairs.Shape;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.Switch.Face;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.block.data.type.Tripwire;

import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyBlockData {

	public static final BlockIdRemappingRegistry REGISTRY = new BlockIdRemappingRegistry();

	public static class BlockIdRemappingRegistry extends IdRemappingRegistry<ArrayBasedIdRemappingTable> {

		public BlockIdRemappingRegistry() {
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
			if ((from.getFace() == Face.CEILING) || (from.getFace() == Face.FLOOR)) {
				to.setFacing(BlockFace.NORTH);
			} else {
				to.setFacing(from.getFacing());
			}
			to.setPowered(from.isPowered());
			return to;
		}

		protected Switch toPre13LeverState(Switch from, Switch to) {
			to.setFace(from.getFace());
			to.setFacing(from.getFacing());
			if ((from.getFace() == Face.CEILING) || (from.getFace() == Face.FLOOR)) {
				// PE levers can only be oriented towards south or east (when off)
				if (from.getFacing() == BlockFace.NORTH) {
					to.setFacing(BlockFace.SOUTH);
				} else if (from.getFacing() == BlockFace.WEST) {
					to.setFacing(BlockFace.EAST);
				}
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
			to.setHalf(from.getHalf());
			return to;
		}

		protected TrapDoor toPre13TrapDoor(TrapDoor from, TrapDoor to) {
			to.setWaterlogged(false);
			to.setPowered(false);
			to.setFacing(from.getFacing());
			to.setOpen(from.isOpen());
			to.setHalf(from.getHalf());
			return to;
		}

		protected Rotatable cloneRotatable(Rotatable from, Rotatable to) {
			to.setRotation(from.getRotation());
			return to;
		}

		protected Directional cloneDirectional(Directional from, Directional to) {
			BlockFace face = from.getFacing();
			if (to.getFaces().contains(face)) {
				to.setFacing(face);
			} else {
				to.setFacing(to.getFaces().iterator().next());
			}
			return to;
		}

		protected Orientable cloneOrientable(Orientable from, Orientable to) {
			to.setAxis(from.getAxis());
			return to;
		}

		protected MultipleFacing clearMutipleFacing(MultipleFacing mfacing) {
			mfacing.getAllowedFaces().forEach(face -> mfacing.setFace(face, false));
			return mfacing;
		}

		public void applyDefaultRemaps() {
			clear();

			this.registerRemapEntryForAllStates(
				Arrays.asList(
					Material.BRAIN_CORAL, Material.BUBBLE_CORAL, Material.FIRE_CORAL, Material.HORN_CORAL, Material.TUBE_CORAL,
					Material.CONDUIT, Material.TNT
				),
				o -> o.getMaterial().createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_13_1
			);

			this.registerRemapEntryForAllStates(
				Arrays.asList(Material.TNT),
				o -> o.getMaterial().createBlockData(),
				ProtocolVersionsHelper.ALL_PE
			);

			this.registerRemapEntryForAllStates(
				Arrays.asList(
					Material.WHITE_SHULKER_BOX, Material.ORANGE_SHULKER_BOX, Material.MAGENTA_SHULKER_BOX,
					Material.LIGHT_BLUE_SHULKER_BOX, Material.YELLOW_SHULKER_BOX, Material.LIME_SHULKER_BOX,
					Material.PINK_SHULKER_BOX, Material.GRAY_SHULKER_BOX, Material.LIGHT_GRAY_SHULKER_BOX,
					Material.CYAN_SHULKER_BOX, Material.CYAN_SHULKER_BOX, Material.PURPLE_SHULKER_BOX,
					Material.BLUE_SHULKER_BOX, Material.BROWN_SHULKER_BOX, Material.GREEN_SHULKER_BOX,
					Material.RED_SHULKER_BOX, Material.BLACK_SHULKER_BOX, Material.SHULKER_BOX
				),
				o -> o.getMaterial().createBlockData(),
				ProtocolVersionsHelper.ALL_PE
			);
			this.registerRemapEntryForAllStates(Material.KELP_PLANT, Material.KELP.createBlockData((blockdata)-> {
				((Ageable) blockdata).setAge(25); //Max age.
			}), ProtocolVersionsHelper.ALL_PE);
			//Remap to closest match. The normal dead variants get broken instantly in PE.
			this.registerRemapEntryForAllStates(Material.DEAD_TUBE_CORAL, Material.DEAD_TUBE_CORAL_FAN.createBlockData(), ProtocolVersionsHelper.ALL_PE);
			this.registerRemapEntryForAllStates(Material.DEAD_BRAIN_CORAL, Material.DEAD_BRAIN_CORAL_FAN.createBlockData(), ProtocolVersionsHelper.ALL_PE);
			this.registerRemapEntryForAllStates(Material.DEAD_BUBBLE_CORAL, Material.DEAD_BUBBLE_CORAL_FAN.createBlockData(), ProtocolVersionsHelper.ALL_PE);
			this.registerRemapEntryForAllStates(Material.DEAD_FIRE_CORAL, Material.DEAD_FIRE_CORAL_FAN.createBlockData(), ProtocolVersionsHelper.ALL_PE);
			this.registerRemapEntryForAllStates(Material.DEAD_HORN_CORAL, Material.DEAD_HORN_CORAL_FAN.createBlockData(), ProtocolVersionsHelper.ALL_PE);
			this.registerRemapEntryForAllStates(
				//Clear all waterlog, but not rotation of all the corals.
				Arrays.asList(
					Material.DEAD_TUBE_CORAL_WALL_FAN, Material.DEAD_BRAIN_CORAL_WALL_FAN, Material.DEAD_BUBBLE_CORAL_WALL_FAN, Material.DEAD_FIRE_CORAL_WALL_FAN, Material.DEAD_HORN_CORAL_WALL_FAN,
					Material.TUBE_CORAL_WALL_FAN, Material.BRAIN_CORAL_WALL_FAN, Material.BUBBLE_CORAL_WALL_FAN, Material.FIRE_CORAL_WALL_FAN, Material.HORN_CORAL_WALL_FAN
				),
				o -> {
					return cloneDirectional((Directional) o, (Directional) o.getMaterial().createBlockData());
				},
				ProtocolVersionsHelper.ALL_PE
			);
			// Remove everything except direction from piston stuff. The rest is handled in tilenbt.
			this.registerRemapEntryForAllStates(
				Arrays.asList(Material.PISTON, Material.STICKY_PISTON, Material.PISTON_HEAD), 
				o -> cloneDirectional((Directional) o, (Directional) o.getMaterial().createBlockData()), 
				ProtocolVersionsHelper.ALL_PE);
			
			this.registerRemapEntryForAllStates(
				//Clear waterlog from remaining non rotatable corals.
				Arrays.asList(
					Material.DEAD_TUBE_CORAL_FAN, Material.DEAD_BRAIN_CORAL_FAN, Material.DEAD_BUBBLE_CORAL_FAN, Material.DEAD_FIRE_CORAL_FAN, Material.DEAD_HORN_CORAL_FAN
				),
				o -> o.getMaterial().createBlockData(),
				ProtocolVersionsHelper.ALL_PE
			);

			this.registerRemapEntryForAllStates(Material.DEAD_BRAIN_CORAL, Material.BRAIN_CORAL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13_1);
			this.registerRemapEntryForAllStates(Material.DEAD_BUBBLE_CORAL, Material.BUBBLE_CORAL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13_1);
			this.registerRemapEntryForAllStates(Material.DEAD_FIRE_CORAL, Material.FIRE_CORAL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13_1);
			this.registerRemapEntryForAllStates(Material.DEAD_HORN_CORAL, Material.HORN_CORAL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13_1);
			this.registerRemapEntryForAllStates(Material.DEAD_TUBE_CORAL, Material.TUBE_CORAL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13_1);

			this.registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_LEAVES, Material.DARK_OAK_LEAVES, Material.BIRCH_LEAVES,
					Material.JUNGLE_LEAVES, Material.SPRUCE_LEAVES, Material.OAK_LEAVES,
					Material.ACACIA_FENCE, Material.DARK_OAK_FENCE, Material.BIRCH_FENCE,
					Material.JUNGLE_FENCE, Material.SPRUCE_FENCE, Material.OAK_FENCE,
					Material.NETHER_BRICK_FENCE, Material.IRON_BARS,
					Material.CHORUS_PLANT, Material.MUSHROOM_STEM, Material.BROWN_MUSHROOM_BLOCK, Material.RED_MUSHROOM_BLOCK, Material.GLASS_PANE,
					Material.BLACK_STAINED_GLASS_PANE, Material.BLUE_STAINED_GLASS_PANE, Material.BROWN_STAINED_GLASS_PANE, Material.CYAN_STAINED_GLASS_PANE,
					Material.GRAY_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE, Material.LIGHT_BLUE_STAINED_GLASS_PANE, Material.LIGHT_GRAY_STAINED_GLASS_PANE,
					Material.LIME_STAINED_GLASS_PANE, Material.MAGENTA_STAINED_GLASS_PANE, Material.ORANGE_STAINED_GLASS_PANE, Material.PINK_STAINED_GLASS_PANE,
					Material.PURPLE_STAINED_GLASS_PANE, Material.RED_STAINED_GLASS_PANE, Material.WHITE_STAINED_GLASS_PANE, Material.YELLOW_STAINED_GLASS_PANE,
					Material.GRASS_BLOCK, Material.MYCELIUM, Material.PODZOL,
					Material.NOTE_BLOCK
				),
				o -> o.getMaterial().createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);

			this.registerRemapEntryForAllStates(
				Arrays.asList(
					Material.SKELETON_SKULL,
					Material.WITHER_SKELETON_SKULL,
					Material.CREEPER_HEAD,
					Material.DRAGON_HEAD,
					Material.PLAYER_HEAD,
					Material.ZOMBIE_HEAD
				),
				o -> o.getMaterial().createBlockData(),
				ProtocolVersionsHelper.ALL_PE
			);

			this.<Directional>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.SKELETON_WALL_SKULL,
					Material.WITHER_SKELETON_WALL_SKULL,
					Material.CREEPER_WALL_HEAD,
					Material.DRAGON_WALL_HEAD,
					Material.PLAYER_WALL_HEAD,
					Material.ZOMBIE_WALL_HEAD
				),
				o -> cloneDirectional(o, (Directional) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.ALL_PE
			);

			this.<Directional>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.SKELETON_WALL_SKULL,
					Material.WITHER_SKELETON_WALL_SKULL,
					Material.CREEPER_WALL_HEAD,
					Material.DRAGON_WALL_HEAD,
					Material.PLAYER_WALL_HEAD,
					Material.ZOMBIE_WALL_HEAD
				),
				o -> cloneDirectional(o, (Directional) Material.SKELETON_WALL_SKULL.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<MultipleFacing>registerRemapEntryForAllStates(
				Arrays.asList(Material.COBBLESTONE_WALL, Material.MOSSY_COBBLESTONE_WALL),
				o -> clearMutipleFacing((MultipleFacing) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);
			this.<MultipleFacing>registerRemapEntryForAllStates(
				Material.VINE,
				o -> {
					MultipleFacing mfacing = (MultipleFacing) o.clone();
					mfacing.setFace(BlockFace.UP, true);
					return mfacing;
				},
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);
			this.<Gate>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_FENCE_GATE, Material.DARK_OAK_FENCE_GATE, Material.BIRCH_FENCE_GATE,
					Material.JUNGLE_FENCE_GATE, Material.OAK_FENCE_GATE, Material.SPRUCE_FENCE_GATE
				),
				o -> toPre13GateState(o, (Gate) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);
			this.<Switch>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.STONE_BUTTON,
					Material.OAK_BUTTON
				),
				o -> toPre13ButtonState(o, (Switch) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);
			this.<Switch>registerRemapEntryForAllStates(
				Arrays.asList(Material.LEVER),
				o -> toPre13ButtonState(o, (Switch) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Switch>registerRemapEntryForAllStates(
				Arrays.asList(Material.LEVER),
				o -> toPre13LeverState(o, (Switch) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.ALL_PE
			);
			this.<Switch>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_BUTTON, Material.DARK_OAK_BUTTON, Material.BIRCH_BUTTON,
					Material.JUNGLE_BUTTON, Material.SPRUCE_BUTTON
				),
				o -> toPre13ButtonState(o, (Switch) Material.OAK_BUTTON.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Switch>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_BUTTON, Material.DARK_OAK_BUTTON, Material.BIRCH_BUTTON,
					Material.JUNGLE_BUTTON, Material.SPRUCE_BUTTON
				),
				o -> toPre13ButtonState(o, (Switch) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.ALL_PE
			);
			this.<Door>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_DOOR, Material.DARK_OAK_DOOR, Material.BIRCH_DOOR,
					Material.JUNGLE_DOOR, Material.OAK_DOOR, Material.SPRUCE_DOOR,
					Material.IRON_DOOR
				),
				o -> toPre13DoorState(o, (Door) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);
			this.<Slab>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_SLAB, Material.DARK_OAK_SLAB, Material.BIRCH_SLAB,
					Material.JUNGLE_SLAB, Material.OAK_SLAB, Material.SPRUCE_SLAB,
					Material.COBBLESTONE_SLAB, Material.SANDSTONE_SLAB, Material.RED_SANDSTONE_SLAB,
					Material.STONE_SLAB, Material.BRICK_SLAB, Material.STONE_BRICK_SLAB,
					Material.NETHER_BRICK_SLAB, Material.QUARTZ_SLAB, Material.PURPUR_SLAB
				),
				o -> toPre13SlabState(o, (Slab) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);
			this.<Slab>registerRemapEntryForAllStates(
				Arrays.asList(Material.PETRIFIED_OAK_SLAB),
				o -> toPre13SlabState(o, (Slab) Material.COBBLESTONE_SLAB.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);
			this.<Slab>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.PRISMARINE_BRICK_SLAB,
					Material.PRISMARINE_SLAB,
					Material.DARK_PRISMARINE_SLAB
				),
				o -> toPre13SlabState(o, (Slab) Material.COBBLESTONE_SLAB.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Slab>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.PRISMARINE_BRICK_SLAB,
					Material.PRISMARINE_SLAB,
					Material.DARK_PRISMARINE_SLAB
				),
				o -> toPre13SlabState(o, (Slab) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.ALL_PE
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
					Material.QUARTZ_STAIRS, Material.NETHER_BRICK_STAIRS, Material.PURPUR_STAIRS,
					Material.BRICK_STAIRS
				),
				o -> toPre13StairsState(o, (Stairs) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);
			this.<Stairs>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.PRISMARINE_BRICK_STAIRS,
					Material.PRISMARINE_STAIRS,
					Material.DARK_PRISMARINE_STAIRS
				),
				o -> toPre13StairsState(o, (Stairs) Material.STONE_BRICK_STAIRS.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Stairs>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.PRISMARINE_BRICK_STAIRS,
					Material.PRISMARINE_STAIRS,
					Material.DARK_PRISMARINE_STAIRS
				),
				o -> toPre13StairsState(o, (Stairs) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.ALL_PE
			);
			this.<TrapDoor>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.IRON_TRAPDOOR,
					Material.OAK_TRAPDOOR
				),
				o -> toPre13TrapDoor(o, (TrapDoor) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);
			this.<TrapDoor>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_TRAPDOOR, Material.DARK_OAK_TRAPDOOR, Material.BIRCH_TRAPDOOR,
					Material.JUNGLE_TRAPDOOR,  Material.SPRUCE_TRAPDOOR
				),
				o -> toPre13TrapDoor(o, (TrapDoor) Material.OAK_TRAPDOOR.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<TrapDoor>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_TRAPDOOR, Material.DARK_OAK_TRAPDOOR, Material.BIRCH_TRAPDOOR,
					Material.JUNGLE_TRAPDOOR,  Material.SPRUCE_TRAPDOOR
				),
				o -> toPre13TrapDoor(o, (TrapDoor) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.ALL_PE
			);
			this.<Bed>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.BLACK_BED, Material.BLUE_BED, Material.BROWN_BED, Material.CYAN_BED,
					Material.GRAY_BED, Material.GREEN_BED, Material.LIGHT_BLUE_BED, Material.LIGHT_GRAY_BED,
					Material.LIME_BED, Material.MAGENTA_BED, Material.ORANGE_BED, Material.PINK_BED,
					Material.PURPLE_BED, Material.RED_BED, Material.WHITE_BED, Material.YELLOW_BED
				),
				o -> {
					Bed bed = (Bed) Material.RED_BED.createBlockData();
					bed.setFacing(o.getFacing());
					bed.setPart(o.getPart());
					return bed;
				},
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);

			this.<Rotatable>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.BLACK_BANNER, Material.BLUE_BANNER, Material.BROWN_BANNER, Material.CYAN_BANNER,
					Material.GRAY_BANNER, Material.GREEN_BANNER, Material.LIGHT_BLUE_BANNER, Material.LIGHT_GRAY_BANNER,
					Material.LIME_BANNER, Material.MAGENTA_BANNER, Material.ORANGE_BANNER, Material.PINK_BANNER,
					Material.PURPLE_BANNER, Material.RED_BANNER, Material.WHITE_BANNER, Material.YELLOW_BANNER
				),
				o -> cloneRotatable(o, (Rotatable) Material.WHITE_BANNER.createBlockData()),
				ProtocolVersionsHelper.ALL_PE
			);
			this.<Directional>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.BLACK_WALL_BANNER, Material.BLUE_WALL_BANNER, Material.BROWN_WALL_BANNER, Material.CYAN_WALL_BANNER,
					Material.GRAY_WALL_BANNER, Material.GREEN_WALL_BANNER, Material.LIGHT_BLUE_WALL_BANNER, Material.LIGHT_GRAY_WALL_BANNER,
					Material.LIME_WALL_BANNER, Material.MAGENTA_WALL_BANNER, Material.ORANGE_WALL_BANNER, Material.PINK_WALL_BANNER,
					Material.PURPLE_WALL_BANNER, Material.RED_WALL_BANNER, Material.WHITE_WALL_BANNER, Material.YELLOW_WALL_BANNER
				),
				o -> cloneDirectional(o, (Directional) Material.WHITE_WALL_BANNER.createBlockData()),
				ProtocolVersionsHelper.ALL_PE
			);

			this.<Directional>registerRemapEntryForAllStates(
				Arrays.asList(Material.LADDER, Material.ENDER_CHEST),
				o -> cloneDirectional(o, (Directional) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);
			this.<Chest>registerRemapEntryForAllStates(
				Arrays.asList(Material.CHEST, Material.TRAPPED_CHEST),
				o -> {
					EnderChest enderChest = (EnderChest) Material.ENDER_CHEST.createBlockData();
					enderChest.setWaterlogged(false);
					enderChest.setFacing(o.getFacing());
					return enderChest;
				},
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Chest>registerRemapEntryForAllStates(
				Arrays.asList(Material.CHEST, Material.TRAPPED_CHEST),
				o -> cloneDirectional(o, (Directional) o.getMaterial().createBlockData()),
				ProtocolVersionsHelper.ALL_PE
			);
			this.<PistonHead>registerRemapEntryForAllStates(
				Material.PISTON_HEAD,
				o -> {
					PistonHead pistonHead = (PistonHead) o.getMaterial().createBlockData();
					pistonHead.setFacing(o.getFacing());
					pistonHead.setType(o.getType());
					return pistonHead;
				},
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);
			this.<Fire>registerRemapEntryForAllStates(
				Material.FIRE,
				o -> {
					Fire fire = (Fire) o.getMaterial().createBlockData();
					fire.setAge(o.getAge());
					return fire;
				},
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);
			this.<Tripwire>registerRemapEntryForAllStates(
				Material.TRIPWIRE,
				o -> {
					Tripwire tripwire = (Tripwire) o.getMaterial().createBlockData();
					tripwire.setAttached(o.isAttached());
					tripwire.setDisarmed(o.isDisarmed());
					tripwire.setPowered(o.isPowered());
					return tripwire;
				},
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);
			this.<RedstoneWire>registerRemapEntryForAllStates(
				Material.REDSTONE_WIRE,
				o -> {
					RedstoneWire wire = (RedstoneWire) o.getMaterial().createBlockData();
					wire.setPower(o.getPower());
					return wire;
				},
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);
			this.<Repeater>registerRemapEntryForAllStates(
				Material.REPEATER,
				o -> {
					Repeater repeater = (Repeater) Material.REPEATER.createBlockData();
					repeater.setDelay(o.getDelay());
					repeater.setFacing(o.getFacing());
					repeater.setPowered(o.isPowered());
					return repeater;
				},
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);
			this.registerRemapEntryForAllStates(
				Arrays.asList(Material.CAVE_AIR, Material.VOID_AIR),
				Material.AIR.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);
			this.registerRemapEntryForAllStates(Material.ATTACHED_MELON_STEM, o -> {
				return Material.MELON_STEM.createBlockData(data -> {
					((Ageable) data).setAge(((Ageable) data).getMaximumAge());
				});
			}, ProtocolVersionsHelper.BEFORE_1_13);
			this.registerRemapEntryForAllStates(Material.ATTACHED_PUMPKIN_STEM, o -> {
				return Material.PUMPKIN_STEM.createBlockData(data -> {
					((Ageable) data).setAge(((Ageable) data).getMaximumAge());
				});
			}, ProtocolVersionsHelper.BEFORE_1_13);
			this.registerRemapEntryForAllStates(Material.BLUE_ICE, Material.LIGHT_BLUE_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			this.registerRemapEntryForAllStates(Material.BUBBLE_COLUMN, Material.WATER.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			this.registerRemapEntryForAllStates(Material.PUMPKIN, Material.CARVED_PUMPKIN.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			this.registerRemapEntryForAllStates(Material.CONDUIT, Material.STONE.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			this.registerRemapEntryForAllStates(
				Arrays.asList(
					Material.BRAIN_CORAL, Material.BRAIN_CORAL_FAN, Material.BRAIN_CORAL_WALL_FAN,
					Material.BUBBLE_CORAL, Material.BRAIN_CORAL_FAN, Material.BRAIN_CORAL_WALL_FAN,
					Material.FIRE_CORAL, Material.FIRE_CORAL_FAN, Material.FIRE_CORAL_WALL_FAN,
					Material.HORN_CORAL, Material.HORN_CORAL_FAN, Material.HORN_CORAL_WALL_FAN,
					Material.TUBE_CORAL, Material.TUBE_CORAL_FAN, Material.TUBE_CORAL_WALL_FAN,
					Material.DEAD_BRAIN_CORAL, Material.DEAD_BRAIN_CORAL_FAN, Material.DEAD_BRAIN_CORAL_WALL_FAN,
					Material.DEAD_BUBBLE_CORAL, Material.DEAD_BUBBLE_CORAL_FAN, Material.DEAD_BUBBLE_CORAL_WALL_FAN,
					Material.DEAD_FIRE_CORAL, Material.DEAD_FIRE_CORAL_FAN, Material.DEAD_FIRE_CORAL_WALL_FAN,
					Material.DEAD_HORN_CORAL, Material.DEAD_HORN_CORAL_FAN, Material.DEAD_HORN_CORAL_WALL_FAN,
					Material.DEAD_TUBE_CORAL, Material.DEAD_TUBE_CORAL_FAN, Material.DEAD_TUBE_CORAL_WALL_FAN
				),
				Material.DANDELION.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.registerRemapEntryForAllStates(Material.TUBE_CORAL_BLOCK, Material.BLUE_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			this.registerRemapEntryForAllStates(Material.BRAIN_CORAL_BLOCK, Material.PINK_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			this.registerRemapEntryForAllStates(Material.BUBBLE_CORAL_BLOCK, Material.PURPLE_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			this.registerRemapEntryForAllStates(Material.FIRE_CORAL_BLOCK, Material.RED_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			this.registerRemapEntryForAllStates(Material.HORN_CORAL_BLOCK, Material.YELLOW_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			this.registerRemapEntryForAllStates(
				Arrays.asList(
					Material.DEAD_BRAIN_CORAL_BLOCK, Material.DEAD_BUBBLE_CORAL_BLOCK, Material.DEAD_FIRE_CORAL_BLOCK,
					Material.DEAD_HORN_CORAL_BLOCK, Material.DEAD_TUBE_CORAL_BLOCK
				),
				Material.LIGHT_GRAY_WOOL.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.registerRemapEntryForAllStates(Material.DRIED_KELP_BLOCK, Material.GREEN_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			this.registerRemapEntryForAllStates(Material.SHULKER_BOX, Material.PINK_SHULKER_BOX.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			this.registerRemapEntryForAllStates(
				Arrays.asList(Material.SEA_PICKLE, Material.TURTLE_EGG),
				Material.CAKE.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.registerRemapEntryForAllStates(Material.SEAGRASS, Material.GRASS.createBlockData(), ProtocolVersionsHelper.BEFORE_1_13);
			this.registerRemapEntryForAllStates(
				Arrays.asList(Material.TALL_SEAGRASS, Material.KELP, Material.KELP_PLANT),
				Material.TALL_GRASS.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Orientable>registerRemapEntryForAllStates(
				Material.STRIPPED_ACACIA_LOG,
				o -> cloneOrientable(o, (Orientable) Material.ACACIA_LOG.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Orientable>registerRemapEntryForAllStates(
				Material.STRIPPED_ACACIA_WOOD,
				o -> cloneOrientable(o, (Orientable) Material.ACACIA_WOOD.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Orientable>registerRemapEntryForAllStates(
				Material.STRIPPED_DARK_OAK_LOG,
				o -> cloneOrientable(o, (Orientable) Material.DARK_OAK_LOG.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Orientable>registerRemapEntryForAllStates(
				Material.STRIPPED_DARK_OAK_WOOD,
				o -> cloneOrientable(o, (Orientable) Material.DARK_OAK_WOOD.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Orientable>registerRemapEntryForAllStates(
				Material.STRIPPED_BIRCH_LOG,
				o -> cloneOrientable(o, (Orientable) Material.BIRCH_LOG.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Orientable>registerRemapEntryForAllStates(
				Material.STRIPPED_BIRCH_WOOD,
				o -> cloneOrientable(o, (Orientable) Material.BIRCH_WOOD.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Orientable>registerRemapEntryForAllStates(
				Material.STRIPPED_JUNGLE_LOG,
				o -> cloneOrientable(o, (Orientable) Material.JUNGLE_LOG.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Orientable>registerRemapEntryForAllStates(
				Material.STRIPPED_JUNGLE_WOOD,
				o -> cloneOrientable(o, (Orientable) Material.JUNGLE_WOOD.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Orientable>registerRemapEntryForAllStates(
				Material.STRIPPED_SPRUCE_LOG,
				o -> cloneOrientable(o, (Orientable) Material.SPRUCE_LOG.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Orientable>registerRemapEntryForAllStates(
				Material.STRIPPED_SPRUCE_WOOD,
				o -> cloneOrientable(o, (Orientable) Material.SPRUCE_WOOD.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Orientable>registerRemapEntryForAllStates(
				Material.STRIPPED_OAK_LOG,
				o -> cloneOrientable(o, (Orientable) Material.OAK_LOG.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.<Orientable>registerRemapEntryForAllStates(
				Material.STRIPPED_OAK_WOOD,
				o -> cloneOrientable(o, (Orientable) Material.OAK_WOOD.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_13
			);
			this.registerRemapEntryForAllStates(
				Arrays.asList(
					Material.POTTED_ACACIA_SAPLING, Material.POTTED_ALLIUM, Material.POTTED_AZURE_BLUET, Material.POTTED_BIRCH_SAPLING,
					Material.POTTED_BLUE_ORCHID, Material.POTTED_BROWN_MUSHROOM, Material.POTTED_CACTUS, Material.POTTED_DANDELION,
					Material.POTTED_DARK_OAK_SAPLING, Material.POTTED_DEAD_BUSH, Material.POTTED_FERN, Material.POTTED_JUNGLE_SAPLING,
					Material.POTTED_OAK_SAPLING, Material.POTTED_ORANGE_TULIP, Material.POTTED_OXEYE_DAISY, Material.POTTED_PINK_TULIP,
					Material.POTTED_POPPY, Material.POTTED_RED_MUSHROOM, Material.POTTED_RED_TULIP, Material.POTTED_SPRUCE_SAPLING,
					Material.POTTED_WHITE_TULIP
				),
				Material.FLOWER_POT.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_13_AND_PE
			);


			this.registerRemapEntryForAllStates(
				Arrays.asList(
					Material.BLACK_CONCRETE, Material.BLUE_CONCRETE, Material.BROWN_CONCRETE, Material.CYAN_CONCRETE,
					Material.GRAY_CONCRETE, Material.GREEN_CONCRETE, Material.LIGHT_BLUE_CONCRETE, Material.LIGHT_GRAY_CONCRETE,
					Material.LIME_CONCRETE, Material.MAGENTA_CONCRETE, Material.ORANGE_CONCRETE, Material.PINK_CONCRETE,
					Material.PURPLE_CONCRETE, Material.RED_CONCRETE, Material.WHITE_CONCRETE, Material.YELLOW_CONCRETE,
					Material.BLACK_GLAZED_TERRACOTTA, Material.BLUE_GLAZED_TERRACOTTA, Material.BROWN_GLAZED_TERRACOTTA, Material.CYAN_GLAZED_TERRACOTTA,
					Material.GRAY_GLAZED_TERRACOTTA, Material.GREEN_GLAZED_TERRACOTTA, Material.LIGHT_BLUE_GLAZED_TERRACOTTA, Material.LIGHT_GRAY_GLAZED_TERRACOTTA,
					Material.LIME_GLAZED_TERRACOTTA, Material.MAGENTA_GLAZED_TERRACOTTA, Material.ORANGE_GLAZED_TERRACOTTA, Material.PINK_GLAZED_TERRACOTTA,
					Material.PURPLE_GLAZED_TERRACOTTA, Material.RED_GLAZED_TERRACOTTA, Material.WHITE_GLAZED_TERRACOTTA, Material.YELLOW_GLAZED_TERRACOTTA
				),
				Material.BRICKS.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_12
			);
			this.registerRemapEntryForAllStates(Material.BLACK_CONCRETE_POWDER, Material.BLACK_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_12);
			this.registerRemapEntryForAllStates(Material.BLUE_CONCRETE_POWDER, Material.BLUE_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_12);
			this.registerRemapEntryForAllStates(Material.BROWN_CONCRETE_POWDER, Material.BROWN_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_12);
			this.registerRemapEntryForAllStates(Material.CYAN_CONCRETE_POWDER, Material.CYAN_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_12);
			this.registerRemapEntryForAllStates(Material.GRAY_CONCRETE_POWDER, Material.GRAY_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_12);
			this.registerRemapEntryForAllStates(Material.GREEN_CONCRETE_POWDER, Material.GREEN_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_12);
			this.registerRemapEntryForAllStates(Material.LIGHT_BLUE_CONCRETE_POWDER, Material.LIGHT_BLUE_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_12);
			this.registerRemapEntryForAllStates(Material.LIGHT_GRAY_CONCRETE_POWDER, Material.LIGHT_GRAY_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_12);
			this.registerRemapEntryForAllStates(Material.LIME_CONCRETE_POWDER, Material.LIME_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_12);
			this.registerRemapEntryForAllStates(Material.MAGENTA_CONCRETE_POWDER, Material.MAGENTA_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_12);
			this.registerRemapEntryForAllStates(Material.ORANGE_CONCRETE_POWDER, Material.ORANGE_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_12);
			this.registerRemapEntryForAllStates(Material.PINK_CONCRETE_POWDER, Material.PINK_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_12);
			this.registerRemapEntryForAllStates(Material.PURPLE_CONCRETE_POWDER, Material.PURPLE_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_12);
			this.registerRemapEntryForAllStates(Material.RED_CONCRETE_POWDER, Material.RED_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_12);
			this.registerRemapEntryForAllStates(Material.WHITE_CONCRETE_POWDER, Material.WHITE_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_12);
			this.registerRemapEntryForAllStates(Material.YELLOW_CONCRETE_POWDER, Material.YELLOW_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_12);


			this.<Observer>registerRemapEntryForAllStates(
				Material.OBSERVER,
				o -> cloneDirectional(o, (Directional) Material.FURNACE.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_11
			);
			this.registerRemapEntryForAllStates(
				Arrays.asList(
					Material.SHULKER_BOX,
					Material.BLACK_SHULKER_BOX, Material.BLUE_SHULKER_BOX, Material.BROWN_SHULKER_BOX, Material.CYAN_SHULKER_BOX,
					Material.GRAY_SHULKER_BOX, Material.GREEN_SHULKER_BOX, Material.LIGHT_BLUE_SHULKER_BOX, Material.LIGHT_GRAY_SHULKER_BOX,
					Material.LIME_SHULKER_BOX, Material.MAGENTA_SHULKER_BOX, Material.ORANGE_SHULKER_BOX, Material.PINK_SHULKER_BOX,
					Material.PURPLE_SHULKER_BOX, Material.RED_SHULKER_BOX, Material.WHITE_SHULKER_BOX, Material.YELLOW_SHULKER_BOX
				),
				Material.FURNACE.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_11
			);

			this.registerRemapEntryForAllStates(Material.STRUCTURE_VOID, Material.GLASS.createBlockData(), ProtocolVersionsHelper.BEFORE_1_10);
			this.registerRemapEntryForAllStates(Material.NETHER_WART_BLOCK, Material.RED_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_10);
			this.registerRemapEntryForAllStates(Material.RED_NETHER_BRICKS, Material.NETHER_BRICKS.createBlockData(), ProtocolVersionsHelper.BEFORE_1_10);
			this.registerRemapEntryForAllStates(Material.MAGMA_BLOCK, Material.NETHERRACK.createBlockData(), ProtocolVersionsHelper.BEFORE_1_10);
			this.registerRemapEntryForAllStates(Material.BONE_BLOCK, o -> {
				Slab slab = (Slab) Material.STONE_SLAB.createBlockData();
				slab.setType(Slab.Type.DOUBLE);
				return slab;
			}, ProtocolVersionsHelper.BEFORE_1_10);


			this.registerRemapEntryForAllStates(Material.END_GATEWAY, Material.END_PORTAL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_9);
			this.registerRemapEntryForAllStates(Material.END_ROD, Material.GLOWSTONE.createBlockData(), ProtocolVersionsHelper.BEFORE_1_9);
			this.registerRemapEntryForAllStates(Material.END_STONE_BRICKS, Material.END_STONE.createBlockData(), ProtocolVersionsHelper.BEFORE_1_9);
			this.registerRemapEntryForAllStates(Material.FROSTED_ICE, Material.ICE.createBlockData(), ProtocolVersionsHelper.BEFORE_1_9);
			this.registerRemapEntryForAllStates(Material.GRASS_PATH, Material.FARMLAND.createBlockData(), ProtocolVersionsHelper.BEFORE_1_9);
			this.registerRemapEntryForAllStates(Material.STRUCTURE_BLOCK, Material.BEDROCK.createBlockData(), ProtocolVersionsHelper.BEFORE_1_9);
			this.registerRemapEntryForAllStates(Material.BEETROOTS, Material.POTATOES.createBlockData(), ProtocolVersionsHelper.BEFORE_1_9);
			this.<CommandBlock>registerRemapEntryForAllStates(
				Arrays.asList(Material.COMMAND_BLOCK, Material.CHAIN_COMMAND_BLOCK, Material.REPEATING_COMMAND_BLOCK),
				o -> {
					CommandBlock data = (CommandBlock) Material.COMMAND_BLOCK.createBlockData();
					data.setFacing(BlockFace.DOWN);
					return data;
				},
				ProtocolVersionsHelper.BEFORE_1_9
			);
			this.registerRemapEntryForAllStates(
				Arrays.asList(Material.CHORUS_FLOWER, Material.CHORUS_PLANT),
				Material.OAK_WOOD.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_9
			);
			this.registerRemapEntryForAllStates(
				Arrays.asList(Material.PURPUR_PILLAR, Material.PURPUR_BLOCK),
				Material.STONE.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_9
			);
			this.<Stairs>registerRemapEntryForAllStates(
				Material.PURPUR_STAIRS,
				o -> toPre13StairsState(o, (Stairs) Material.COBBLESTONE_STAIRS.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_9
			);
			this.<Slab>registerRemapEntryForAllStates(
				Material.PURPUR_SLAB,
				o -> toPre13SlabState(o, (Slab) Material.COBBLESTONE_SLAB.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_9
			);


			this.registerRemapEntryForAllStates(Material.SLIME_BLOCK, Material.EMERALD_BLOCK.createBlockData(), ProtocolVersionsHelper.BEFORE_1_8);
			this.registerRemapEntryForAllStates(Material.BARRIER, Material.GLASS.createBlockData(), ProtocolVersionsHelper.BEFORE_1_8);
			this.registerRemapEntryForAllStates(Material.PRISMARINE, Material.MOSSY_COBBLESTONE.createBlockData(), ProtocolVersionsHelper.BEFORE_1_8);
			this.registerRemapEntryForAllStates(Material.DARK_PRISMARINE, Material.MOSSY_COBBLESTONE.createBlockData(), ProtocolVersionsHelper.BEFORE_1_8);
			this.registerRemapEntryForAllStates(Material.PRISMARINE_BRICKS, Material.MOSSY_STONE_BRICKS.createBlockData(), ProtocolVersionsHelper.BEFORE_1_8);
			this.registerRemapEntryForAllStates(Material.SEA_LANTERN, Material.GLOWSTONE.createBlockData(), ProtocolVersionsHelper.BEFORE_1_8);
			this.registerRemapEntryForAllStates(Material.DAYLIGHT_DETECTOR, Material.DAYLIGHT_DETECTOR.createBlockData(), ProtocolVersionsHelper.BEFORE_1_8);
			this.<TrapDoor>registerRemapEntryForAllStates(//not the best remap, but we have no choice
				Material.IRON_TRAPDOOR,
				o -> toPre13TrapDoor(o, (TrapDoor) Material.OAK_TRAPDOOR.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_8
			);
			this.<Rotatable>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.BLACK_BANNER, Material.BLUE_BANNER, Material.BROWN_BANNER, Material.CYAN_BANNER,
					Material.GRAY_BANNER, Material.GREEN_BANNER, Material.LIGHT_BLUE_BANNER, Material.LIGHT_GRAY_BANNER,
					Material.LIME_BANNER, Material.MAGENTA_BANNER, Material.ORANGE_BANNER, Material.PINK_BANNER,
					Material.PURPLE_BANNER, Material.RED_BANNER, Material.WHITE_BANNER, Material.YELLOW_BANNER
				),
				o -> cloneRotatable(o, (Rotatable) Material.SIGN.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_8
			);
			this.<Directional>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.BLACK_WALL_BANNER, Material.BLUE_WALL_BANNER, Material.BROWN_WALL_BANNER, Material.CYAN_WALL_BANNER,
					Material.GRAY_WALL_BANNER, Material.GREEN_WALL_BANNER, Material.LIGHT_BLUE_WALL_BANNER, Material.LIGHT_GRAY_WALL_BANNER,
					Material.LIME_WALL_BANNER, Material.MAGENTA_WALL_BANNER, Material.ORANGE_WALL_BANNER, Material.PINK_WALL_BANNER,
					Material.PURPLE_WALL_BANNER, Material.RED_WALL_BANNER, Material.WHITE_WALL_BANNER, Material.YELLOW_WALL_BANNER
				),
				o -> cloneDirectional(o, (Directional) Material.WALL_SIGN.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_8
			);
			this.<Slab>registerRemapEntryForAllStates(
				Arrays.asList(Material.STONE_SLAB, Material.BRICK_SLAB),
				o -> toPre13SlabState(o, (Slab) Material.COBBLESTONE_SLAB.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_8
			);
			this.<Gate>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_FENCE_GATE, Material.DARK_OAK_FENCE_GATE, Material.BIRCH_FENCE_GATE,
					Material.JUNGLE_FENCE_GATE, Material.OAK_FENCE_GATE, Material.SPRUCE_FENCE_GATE
				),
				o -> toPre13GateState(o, (Gate) Material.OAK_FENCE_GATE.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_8
			);
			this.registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_FENCE, Material.DARK_OAK_FENCE, Material.BIRCH_FENCE,
					Material.JUNGLE_FENCE, Material.SPRUCE_FENCE, Material.OAK_FENCE
				),
				Material.OAK_FENCE.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_8
			);
			this.<Door>registerRemapEntryForAllStates(
				Arrays.asList(
					Material.ACACIA_DOOR, Material.DARK_OAK_DOOR, Material.BIRCH_DOOR,
					Material.JUNGLE_DOOR, Material.OAK_DOOR, Material.SPRUCE_DOOR
				),
				o -> toPre13DoorState(o, (Door) Material.OAK_DOOR.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_8
			);
			this.registerRemapEntryForAllStates(Material.RED_SAND, Material.SAND.createBlockData(), ProtocolVersionsHelper.BEFORE_1_8);
			this.registerRemapEntryForAllStates(Material.RED_SANDSTONE, Material.SANDSTONE.createBlockData(), ProtocolVersionsHelper.BEFORE_1_8);
			this.registerRemapEntryForAllStates(Material.CHISELED_RED_SANDSTONE, Material.CHISELED_SANDSTONE.createBlockData(), ProtocolVersionsHelper.BEFORE_1_8);
			this.registerRemapEntryForAllStates(Material.CUT_RED_SANDSTONE, Material.CUT_SANDSTONE.createBlockData(), ProtocolVersionsHelper.BEFORE_1_8);
			this.registerRemapEntryForAllStates(Material.SMOOTH_RED_SANDSTONE, Material.SMOOTH_SANDSTONE.createBlockData(), ProtocolVersionsHelper.BEFORE_1_8);
			this.<Slab>registerRemapEntryForAllStates(
				Material.RED_SANDSTONE_SLAB,
				o -> toPre13SlabState(o, (Slab) Material.SANDSTONE_SLAB.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_8
			);
			this.<Stairs>registerRemapEntryForAllStates(
				Material.RED_SANDSTONE_STAIRS,
				o -> toPre13StairsState(o, (Stairs) Material.SANDSTONE_STAIRS.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_8
			);


			this.registerRemapEntryForAllStates(
				Arrays.asList(Material.KELP, Material.KELP_PLANT, Material.TALL_SEAGRASS, Material.TALL_GRASS),
				Material.GRASS.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_7
			);
			this.registerRemapEntryForAllStates(Material.PACKED_ICE, Material.BLUE_WOOL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_7);
			this.registerRemapEntryForAllStates(
				Arrays.asList(Material.ACACIA_LOG, Material.DARK_OAK_LOG),
				Material.OAK_LOG.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_7
			);
			this.registerRemapEntryForAllStates(
				Arrays.asList(Material.ACACIA_STAIRS, Material.DARK_OAK_STAIRS),
				Material.OAK_STAIRS.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_7
			);
			this.registerRemapEntryForAllStates(
				Arrays.asList(Material.ACACIA_LEAVES, Material.DARK_OAK_LEAVES),
				Material.OAK_LEAVES.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_7
			);
			this.registerRemapEntryForAllStates(
				Arrays.asList(
					Material.BLACK_STAINED_GLASS, Material.BLUE_STAINED_GLASS, Material.BROWN_STAINED_GLASS, Material.CYAN_STAINED_GLASS,
					Material.GRAY_STAINED_GLASS, Material.GREEN_STAINED_GLASS, Material.LIGHT_BLUE_STAINED_GLASS, Material.LIGHT_GRAY_STAINED_GLASS,
					Material.LIME_STAINED_GLASS, Material.MAGENTA_STAINED_GLASS, Material.ORANGE_STAINED_GLASS, Material.PINK_STAINED_GLASS,
					Material.PURPLE_STAINED_GLASS, Material.RED_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.YELLOW_STAINED_GLASS
				),
				Material.GLASS.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_7
			);
			this.registerRemapEntryForAllStates(
				Arrays.asList(
					Material.BLACK_STAINED_GLASS_PANE, Material.BLUE_STAINED_GLASS_PANE, Material.BROWN_STAINED_GLASS_PANE, Material.CYAN_STAINED_GLASS_PANE,
					Material.GRAY_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE, Material.LIGHT_BLUE_STAINED_GLASS_PANE, Material.LIGHT_GRAY_STAINED_GLASS_PANE,
					Material.LIME_STAINED_GLASS_PANE, Material.MAGENTA_STAINED_GLASS_PANE, Material.ORANGE_STAINED_GLASS_PANE, Material.PINK_STAINED_GLASS_PANE,
					Material.PURPLE_STAINED_GLASS_PANE, Material.RED_STAINED_GLASS_PANE, Material.WHITE_STAINED_GLASS_PANE, Material.YELLOW_STAINED_GLASS_PANE
				),
				Material.GLASS_PANE.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_7
			);
			this.registerRemapEntryForAllStates(
				Arrays.asList(
					Material.BLUE_ORCHID, Material.ALLIUM, Material.AZURE_BLUET, Material.RED_TULIP,
					Material.ORANGE_TULIP, Material.WHITE_TULIP, Material.PINK_TULIP, Material.OXEYE_DAISY,
					Material.SUNFLOWER, Material.LILAC, Material.ROSE_BUSH, Material.PEONY
				),
				Material.DANDELION.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_7
			);


			this.registerRemapEntryForAllStates(Material.HAY_BLOCK, Material.STONE.createBlockData(), ProtocolVersionsHelper.BEFORE_1_6);
			this.registerRemapEntryForAllStates(Material.COAL_BLOCK, Material.OBSIDIAN.createBlockData(), ProtocolVersionsHelper.BEFORE_1_6);
			this.registerRemapEntryForAllStates(
				Arrays.asList(
					Material.BLACK_CARPET, Material.BLUE_CARPET, Material.BROWN_CARPET, Material.CYAN_CARPET,
					Material.GRAY_CARPET, Material.GREEN_CARPET, Material.LIGHT_BLUE_CARPET, Material.LIGHT_GRAY_CARPET,
					Material.LIME_CARPET, Material.MAGENTA_CARPET, Material.ORANGE_CARPET, Material.PINK_CARPET,
					Material.PURPLE_CARPET, Material.RED_CARPET, Material.WHITE_CARPET, Material.YELLOW_CARPET
				),
				Material.SNOW.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_6
			);
			this.registerRemapEntryForAllStates(
				Arrays.asList(
					Material.TERRACOTTA,
					Material.BLACK_TERRACOTTA, Material.BLUE_TERRACOTTA, Material.BROWN_TERRACOTTA, Material.CYAN_TERRACOTTA,
					Material.GRAY_TERRACOTTA, Material.GREEN_TERRACOTTA, Material.LIGHT_BLUE_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA,
					Material.LIME_TERRACOTTA, Material.MAGENTA_TERRACOTTA, Material.ORANGE_TERRACOTTA, Material.PINK_TERRACOTTA,
					Material.PURPLE_TERRACOTTA, Material.RED_TERRACOTTA, Material.WHITE_TERRACOTTA, Material.YELLOW_TERRACOTTA
				),
				Material.STONE.createBlockData(),
				ProtocolVersionsHelper.BEFORE_1_6
			);


			this.registerRemapEntryForAllStates(Material.QUARTZ_BLOCK, Material.STONE.createBlockData(), ProtocolVersionsHelper.BEFORE_1_5);
			this.registerRemapEntryForAllStates(Material.QUARTZ_PILLAR, Material.STONE.createBlockData(), ProtocolVersionsHelper.BEFORE_1_5);
			this.registerRemapEntryForAllStates(Material.NETHER_QUARTZ_ORE, Material.COAL_ORE.createBlockData(), ProtocolVersionsHelper.BEFORE_1_5);
			this.registerRemapEntryForAllStates(Material.DAYLIGHT_DETECTOR, Material.COBBLESTONE_SLAB.createBlockData(), ProtocolVersionsHelper.BEFORE_1_5);
			this.registerRemapEntryForAllStates(Material.REDSTONE_BLOCK, Material.EMERALD_BLOCK.createBlockData(), ProtocolVersionsHelper.BEFORE_1_5);
			this.registerRemapEntryForAllStates(Material.ACTIVATOR_RAIL, Material.DETECTOR_RAIL.createBlockData(), ProtocolVersionsHelper.BEFORE_1_5);
			this.<AnaloguePowerable>registerRemapEntryForAllStates(
				Arrays.asList(Material.LIGHT_WEIGHTED_PRESSURE_PLATE, Material.HEAVY_WEIGHTED_PRESSURE_PLATE),
				o -> {
					Powerable powerable = (Powerable) Material.STONE_PRESSURE_PLATE.createBlockData();
					powerable.setPowered(o.getPower() == o.getMaximumPower());
					return powerable;
				}, ProtocolVersionsHelper.BEFORE_1_5
			);
			this.<Comparator>registerRemapEntryForAllStates(
				Material.COMPARATOR,
				o -> {
					Repeater repeater = (Repeater) Material.REPEATER.createBlockData();
					repeater.setPowered(o.isPowered());
					return repeater;
				},
				ProtocolVersionsHelper.BEFORE_1_5
			);
			this.<Stairs>registerRemapEntryForAllStates(
				Material.QUARTZ_STAIRS,
				o -> toPre13StairsState(o, (Stairs) Material.COBBLESTONE_STAIRS.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_5
			);
			this.<Slab>registerRemapEntryForAllStates(
				Material.QUARTZ_SLAB,
				o -> toPre13SlabState(o, (Slab) Material.COBBLESTONE_SLAB.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_5
			);
			this.<Directional>registerRemapEntryForAllStates(
				Material.TRAPPED_CHEST,
				o -> cloneDirectional(o, (Directional) Material.ENDER_CHEST.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_5
			);
			this.<Directional>registerRemapEntryForAllStates(
				Arrays.asList(Material.DROPPER, Material.HOPPER),
				o -> cloneDirectional(o, (Directional) Material.FURNACE.createBlockData()),
				ProtocolVersionsHelper.BEFORE_1_5
			);
		}

		protected void registerRemapEntryForAllStates(List<Material> materials, BlockData to, ProtocolVersion... versions) {
			for (Material material : materials) {
				registerRemapEntryForAllStates(material, to, versions);
			}
		}

		protected void registerRemapEntryForAllStates(Material from, BlockData to, ProtocolVersion... versions) {
			MaterialAPI.getBlockDataList(from)
			.forEach(blockdata -> registerRemapEntry(blockdata, to, versions));
		}

		protected <T extends BlockData> void registerRemapEntryForAllStates(List<Material> materials, Function<T, BlockData> remapFunc, ProtocolVersion... versions) {
			for (Material material : materials) {
				registerRemapEntryForAllStates(material, remapFunc, versions);
			}
		}

		@SuppressWarnings("unchecked")
		protected <T extends BlockData> void registerRemapEntryForAllStates(Material from, Function<T, BlockData> remapFunc, ProtocolVersion... versions) {
			MaterialAPI.getBlockDataList(from)
			.forEach(blockdata -> registerRemapEntry(blockdata, remapFunc.apply((T) blockdata), versions));
		}

		protected void registerRemapEntry(BlockData from, BlockData to, ProtocolVersion... versions) {
			registerRemapEntry(MaterialAPI.getBlockDataNetworkId(from), MaterialAPI.getBlockDataNetworkId(to), versions);
		}

		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(MinecraftData.BLOCKDATA_COUNT);
		}
	}

}
