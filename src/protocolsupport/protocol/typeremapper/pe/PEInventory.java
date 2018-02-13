package protocolsupport.protocol.typeremapper.pe;

import java.util.EnumMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import protocolsupport.ProtocolSupport;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.utils.Any;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventoryOpen;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventorySetItems;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

/**
 * Hacky hack. This class is probably the biggest hack in ProtocolSupprt. Buyers be aware, this can break easily!
 * It is also necessary, without inventories (faked or otherwise) minecraft is unplayable!
 */
public class PEInventory {
	
	//To store data about fake inventory blocks for async usage.
	//Data stored inside will be used to reconstruct the block after the inventory is closed.
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
		
		//Call in sync! Stores information to set and reset fake container blocks and schedules an inventory update.
		public static void saveFakeInventoryInformation(Connection connection, Inventory inventory) {
			Location mainLoc = connection.getPlayer().getLocation();
			//If the player is not on the ground or almost at bedrock, 
			//set the fake blocks above so the player doesn't fall on to it or so they aren't out of the world.
			if ((!connection.getPlayer().isOnGround()) || (mainLoc.getBlockY() < 4)) {
				mainLoc.add(0, 6, 0);
			}
			if (inventory.getType() == InventoryType.BEACON && inventory.getLocation() != null) {
				//Since beacon power is checked clientside, we can't even fake the block in position we please.
				mainLoc = inventory.getLocation();
				mainLoc.add(1, 2, 0);
			}
			connection.addMetadata("peInvBlocks", new InvBlock[] {
					new InvBlock(mainLoc.subtract(1, 2, 0).getBlock()), 
					new InvBlock(mainLoc.	  add(1, 0, 0).getBlock())
				});
			Location headChestLock = mainLoc;
			//Double chests need some ticks to configure after the inventory blocks are placed. We need to resend the inventory open.
			if (inventory.getSize() > 27) {
				Bukkit.getScheduler().runTaskLater(ProtocolSupport.getInstance(), new Runnable() {
					@Override
					public void run() {
						if (connection.hasMetadata("smuggledWindowId")) {
							InventoryOpen.sendInventory(connection, 
								(int) connection.getMetadata("smuggledWindowId"),
								WindowType.CHEST, 
								Position.fromBukkit(headChestLock),
								-1
							);
							connection.getPlayer().updateInventory();
							connection.removeMetadata("smuggledWindowId");
						}
					}
				}, 2);
			}
		}
		
		//Creates a fake container at the location of the fake blocks.
		public static RecyclableArrayList<ClientBoundPacketData> prepareFakeInventory(ProtocolVersion version, String locale, InvBlock[] blocks, WindowType type, BaseComponent title, int slots) {
			RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
			Any<Integer, TileEntityType> typeData = InvBlock.getContainerData(type);
			if(typeData != null) {
				Position mainpos = blocks[0].getPosition();
				packets.add(BlockChangeSingle.create(version, mainpos, typeData.getObj1()));
				NBTTagCompoundWrapper tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
				tag.setString("CustomName", title.toLegacyText(locale));
				if(typeData.getObj2() != TileEntityType.UNKNOWN) {
					tag.setString("id", typeData.getObj2().getRegistryId());
				}
				if(type == WindowType.CHEST && slots > 27) {
					Position auxPos = blocks[1].getPosition();
					packets.add(BlockChangeSingle.create(version, auxPos, typeData.getObj1()));
					tag.setInt("pairx", auxPos.getX());
					tag.setInt("pairz", auxPos.getZ());
					tag.setByte("pairlead", 1);
					packets.add(BlockTileUpdate.create(version, mainpos, tag));
					NBTTagCompoundWrapper auxTag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();;
					auxTag.setString("CustomName", title.toLegacyText(locale));
					auxTag.setString("id", typeData.getObj2().getRegistryId());
					auxTag.setInt("pairx", mainpos.getX());
					auxTag.setInt("pairz", mainpos.getZ());
					auxTag.setByte("pairlead", 0);
					packets.add(BlockTileUpdate.create(version, auxPos, auxTag));
				} else {
					packets.add(BlockTileUpdate.create(version, mainpos, tag));
				}
				
			}
			return packets;
		}
		
		//Reset all fake container blocks belonging to the connection.
		public static void destroyFakeContainers(Connection connection) {
			if (connection.hasMetadata("peInvBlocks")) {
				InvBlock[] blocks = ((InvBlock[]) connection.getMetadata("peInvBlocks"));
				for (int i = 0; i < blocks.length; i++) {
					connection.sendPacket(ServerPlatform.get().getPacketFactory().createBlockUpdatePacket(
							blocks[i].getPosition(), blocks[i].getTypeData()));
				}
				connection.removeMetadata("peInvBlocks");
			}
		}
		
		//Creates packets for resetting fake container blocks belonging to the connection.
		public static RecyclableArrayList<ClientBoundPacketData> destroyFakeInventory(Connection connection) {
			RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
			if (connection.hasMetadata("peInvBlocks")) {
				InvBlock[] blocks = ((InvBlock[]) connection.getMetadata("peInvBlocks"));
				for (int i = 0; i < blocks.length; i++) {
					packets.add(BlockChangeSingle.create(connection.getVersion(), blocks[i].getPosition(), blocks[i].getTypeData()));
				}
				connection.removeMetadata("peInvBlocks");
			}
			return packets;
		}
		
	}
	
	//Uses bukkit to schedule an inventory update in PE
	public static void scheduleInventoryUpdate(Connection connection, ItemStack cursorItem) {
		if (
				(!connection.hasMetadata("lastScheduledInventoryUpdate")) ||
				(System.currentTimeMillis() - (long) connection.getMetadata("lastScheduledInventoryUpdate") >= 250)
		   ) {
			connection.addMetadata("lastScheduledInventoryUpdate", System.currentTimeMillis());
			Bukkit.getScheduler().runTaskLater(ProtocolSupport.getInstance(), new Runnable() {
				@Override
				public void run() {
					connection.getPlayer().updateInventory();
					if (cursorItem != null) {
						connection.getPlayer().setItemOnCursor(cursorItem);
					}
				}
			}, 10);
		}
	}
	
	//To store data to fake an entire beacon.
	public static class BeaconTemple {
		
		private int primary = 0;
		private int secondary = 0;
		
		public void setPrimaryEffect(int effect) {
			this.primary = effect;
		}
		
		public void setSecondaryEffect(int effect) {
			this.secondary = effect;
		}
		
		public RecyclableArrayList<ClientBoundPacketData> updateNBT(ProtocolVersion version, Connection connection) {
			RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
			if (connection.hasMetadata("peInvBlocks")) {
				InvBlock[] blocks = (InvBlock[]) connection.getMetadata("peInvBlocks");
				NBTTagCompoundWrapper tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
				tag.setString("id", "beacon");
				tag.setInt("primary", primary);
				tag.setInt("secondary", secondary);
				packets.add(BlockTileUpdate.create(version, blocks[0].getPosition(), tag));
			}
			return packets;
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
