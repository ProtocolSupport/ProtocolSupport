package protocolsupport.protocol.typeremapper.pe;

import java.util.EnumMap;

import org.bukkit.block.Block;

import protocolsupport.api.utils.Any;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.protocol.utils.types.WindowType;

public class PEInventory {
	
	//To store data about fake inventory blocks for async usage.
	public static class InvBlock {
		private final Position position;
		private final int typeData;
		
		//Constructor is called in sync!
		@SuppressWarnings("deprecation")
		public InvBlock(Block b) {
			position = Position.fromBukkit(b.getLocation());
			typeData = MinecraftData.getBlockStateFromIdAndData(b.getTypeId(), b.getData());
		}
		
		public Position getPosition() {
			return position;
		}
		
		public int getTypeData() {
			return typeData;
		}
		
		//Table with PE ids and access to tile id, to place the inventory blocks.
		private static void regInvBlockType(WindowType type, int containerId, TileEntityType tileType) {
			invBlockType.put(type, new Any<Integer, TileEntityType>(containerId << 4, tileType));
		}
		private static EnumMap<WindowType, Any<Integer, TileEntityType>> invBlockType = new EnumMap<>(WindowType.class);
		static {
			regInvBlockType(WindowType.CHEST, 			54, TileEntityType.CHEST);
			regInvBlockType(WindowType.CRAFTING_TABLE, 	58, TileEntityType.UNKNOWN);
			regInvBlockType(WindowType.FURNACE, 		61, TileEntityType.FURNACE);
			regInvBlockType(WindowType.DISPENSER, 		23, TileEntityType.DISPENSER);
			regInvBlockType(WindowType.ENCHANT, 	   116, TileEntityType.UNKNOWN);
			regInvBlockType(WindowType.BREWING, 	   117, TileEntityType.BREWING_STAND);
			regInvBlockType(WindowType.BEACON, 		   138, TileEntityType.BEACON);
			regInvBlockType(WindowType.ANVIL, 		   145, TileEntityType.UNKNOWN);
			regInvBlockType(WindowType.HOPPER, 		   154, TileEntityType.HOPPER);
			regInvBlockType(WindowType.DROPPER,		   158, TileEntityType.DROPPER);
			regInvBlockType(WindowType.SHULKER, 		54, TileEntityType.CHEST);
		}
		public static Any<Integer, TileEntityType> getContainerData(WindowType type) {
			return invBlockType.get(type);
		}
	}
	
	//Slot thingy numbers.
	public static class PESource {
		public static final int POCKET_FAUX_DROP = -999;
		public static final int POCKET_BEACON = -24;                   
		public static final int POCKET_TRADE_OUTPUT = -23;
		public static final int POCKET_TRADE_USE_INGREDIENT = -22;
		public static final int POCKET_TRADE_INPUT_2 = -21;
		public static final int POCKET_TRADE_INPUT_1 = -20;
		public static final int POCKET_ENCHANT_OUTPUT = -16;
		public static final int POCKET_ENCHANT_MATERIAL = -15;
		public static final int POCKET_ENCHANT_INPUT = -14;
		public static final int POCKET_ANVIL_OUTPUT = -13;
		public static final int POCKET_ANVIL_RESULT = -12;
		public static final int POCKET_ANVIL_MATERIAL = -11;
		public static final int POCKET_ANVIL_INPUT = -10;
		public static final int POCKET_CRAFTING_GRID_USE_INGREDIENT = -5;
		public static final int POCKET_CRAFTING_RESULT = -4;
		public static final int POCKET_CRAFTING_GRID_REMOVE = -3;
		public static final int POCKET_CRAFTING_GRID_ADD = -2;
		public static final int POCKET_NONE = -1;
		public static final int POCKET_INVENTORY = 0;
		public static final int POCKET_OFFHAND = 119;
		public static final int POCKET_ARMOR_EQUIPMENT = 120;
		public static final int POCKET_CREATIVE_INVENTORY = 121;
		public static final int POCKET_CLICKED_SLOT = 124;
	}
	
}
