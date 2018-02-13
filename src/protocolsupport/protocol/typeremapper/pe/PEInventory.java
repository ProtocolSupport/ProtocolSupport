package protocolsupport.protocol.typeremapper.pe;

import java.util.EnumMap;

import org.bukkit.block.Block;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.utils.Any;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventorySetItems;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

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
			regInvBlockType(WindowType.ENCHANT, 	   154, TileEntityType.HOPPER); //Fake with hopper
			regInvBlockType(WindowType.BREWING, 	   117, TileEntityType.BREWING_STAND);
			regInvBlockType(WindowType.BEACON, 		   138, TileEntityType.BEACON);
			regInvBlockType(WindowType.ANVIL, 		   145, TileEntityType.UNKNOWN);
			regInvBlockType(WindowType.HOPPER, 		   154, TileEntityType.HOPPER);
			regInvBlockType(WindowType.DROPPER,		   158, TileEntityType.DROPPER);
			regInvBlockType(WindowType.SHULKER, 		54, TileEntityType.CHEST); //Fake with chest
		}
		public static Any<Integer, TileEntityType> getContainerData(WindowType type) {
			return invBlockType.get(type);
		}
	}
	
	//To store data to fake the enchantment process using hoppers.
	public static class EnchantHopper {
		
		private ItemStackWrapper inputOutputSlot = ItemStackWrapper.NULL;
		private ItemStackWrapper lapisSlot = ItemStackWrapper.NULL;
		private int[] optionXP   = 	new int[] { 0,  0,  0};
		private int[] optionEnch = 	new int[] {-1, -1, -1};
		private int[] optionLvl  = 	new int[] { 1,  1,  1};
		
		public void setInputOutputStack(ItemStackWrapper inputOutputStack) {
			this.inputOutputSlot = inputOutputStack;
		}
		
		public ItemStackWrapper getInput() {
			return inputOutputSlot;
		}
		
		public void setLapisStack(ItemStackWrapper lapisStack) {
			this.lapisSlot = lapisStack;
		}
		
		public void updateOptionXP(int num, int xp) {
			optionXP[num] = xp;
		}
		
		public void updateOptionEnch(int num, int enchant) {
			optionEnch[num] = enchant;
		}
		
		public void updateOptionLevel(int num, int lvl) {
			optionLvl[num] = lvl;
		}
		
		public ClientBoundPacketData updateInventory(NetworkDataCache cache, ProtocolVersion version) {
			ItemStackWrapper[] contents = new ItemStackWrapper[5];
			contents[0] = inputOutputSlot;
			contents[1] = lapisSlot;
			for (int i = 0; i < 3; i++) {
				//Create option item & nbt
				if (optionEnch[i] < 0) { contents[i+2] = ItemStackWrapper.NULL; break;}
				ItemStackWrapper option = inputOutputSlot.cloneItemStack();
				if (option.isNull()) { break; }
				System.out.println("Woo not -1!");
				NBTTagCompoundWrapper tag = (option.getTag() == null || option.getTag().isNull()) ?
				ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound() : option.getTag();
				//Display
				if (!tag.hasKeyOfType("display", NBTTagType.COMPOUND)) {
					tag.setCompound("display", ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound());
				}
				NBTTagCompoundWrapper display = tag.getCompound("display");
				display.setString("Name", "Click to enchant");
				NBTTagListWrapper lore = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
				lore.addString("Requires");
				lore.addString(optionXP[i] + (optionXP[i] == 1 ? " Enchantment Level" : " Enchantment Levels"));
				lore.addString((i + 1) + " Lapis Lazuli");
				display.setList("Lore", lore);
				tag.setCompound("display", display);
				//Enchantment
				NBTTagListWrapper ench = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
				if(ench.isEmpty()) { ench.addCompound(ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound()); }
				ench.getCompound(0).setShort("id",  optionEnch[i]);
				ench.getCompound(0).setShort("lvl", optionLvl [i]);
				tag.setList("ench", ench);
				//Wrap up
				option.setTag(tag);
				contents[i+2] = option;
			}
			return InventorySetItems.create(version, cache.getLocale(), cache.getOpenedWindowId(), contents);
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
