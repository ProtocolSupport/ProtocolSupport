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
		private ItemStackWrapper[] contents = new ItemStackWrapper[] {
				ItemStackWrapper.NULL, ItemStackWrapper.NULL, ItemStackWrapper.NULL, ItemStackWrapper.NULL, ItemStackWrapper.NULL
			};
		
		public void clear() {
			for (int i = 0; i < contents.length; i++) {
				contents[i] = ItemStackWrapper.NULL;
			}
		}
		
		public void setInputOutputStack(ItemStackWrapper inputOutputStack) {
			contents[0] = inputOutputStack;
		}
		
		public ItemStackWrapper getInput() {
			return contents[0];
		}
		
		public void setLapisStack(ItemStackWrapper lapisStack) {
			contents[1] = lapisStack;
		}
		
		public void setOption(int num, ItemStackWrapper option) {
			contents[num + 2] = option;
		}
		
		public void updateOptionLore(int num, int xp) {
			ItemStackWrapper option = createOption(num);
			if (option.isNull()) { return; }
			NBTTagCompoundWrapper tag = option.getTag();
			if (!tag.hasKeyOfType("display", NBTTagType.COMPOUND)) {
				tag.setCompound("display", ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound());
			}
			NBTTagCompoundWrapper display = tag.getCompound("display");
			NBTTagListWrapper lore = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
			lore.addString("Click to enchant item. Requires:"); //Empty line
			lore.addString(xp + (xp == 1 ? " Enchantment Level" : " Enchantment Levels"));
			lore.addString(num + " Lapis Lazuli");
			display.setList("Lore", lore);
		}
		
		public void updateOptionEnch(int num, int enchant) {
			ItemStackWrapper option = createOption(num);
			if (option.isNull()) { return; }
			NBTTagCompoundWrapper tag = option.getTag();
			if (!tag.hasKeyOfType("ench", NBTTagType.LIST)) {
				tag.setList("ench", ServerPlatform.get().getWrapperFactory().createEmptyNBTList());
			}
			NBTTagListWrapper ench = tag.getList("ench");
			if(ench.isEmpty()) { ench.addCompound(ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound()); }
			ench.getCompound(0).setShort("id", enchant);
			if(!ench.getCompound(0).hasKeyOfType("lvl", NBTTagType.SHORT)) {ench.getCompound(0).setShort("lvl", 1);}
		}
		
		public void updateOptionLevel(int num, int lvl) {
			ItemStackWrapper option = createOption(num);
			if (option.isNull()) { return; }
			NBTTagCompoundWrapper tag = option.getTag();
			if (!tag.hasKeyOfType("ench", NBTTagType.LIST)) {
				tag.setList("ench", ServerPlatform.get().getWrapperFactory().createEmptyNBTList());
			}
			NBTTagListWrapper ench = tag.getList("ench");
			if(ench.isEmpty()) { ench.addCompound(ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound()); }
			ench.getCompound(0).setShort("lvl", lvl);
			if(!ench.getCompound(0).hasKeyOfType("ench", NBTTagType.SHORT)) {ench.getCompound(0).setShort("ench", 1);}
		}
		
		private ItemStackWrapper createOption(int num) {
			num += 2;
			if (contents[num].isNull()) {
				contents[num] = contents[0].cloneItemStack();
				if(!contents[num].isNull() && contents[num].getTag() == null) {
					contents[num].setTag(ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound());
				}
			}
			return contents[num];
		}
		
		public ClientBoundPacketData updateInventory(NetworkDataCache cache, ProtocolVersion version) {
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
