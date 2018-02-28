package protocolsupport.protocol.typeremapper.pe;

import java.util.EnumMap;

import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.utils.Any;
import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventorySetItems;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.storage.pe.PEInventoryCache;
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
	private static Any<Integer, TileEntityType> getContainerData(WindowType type) {
		return invBlockType.get(type);
	}
	
	public static Position prepareFakeInventory(BaseComponent title, Connection connection, NetworkDataCache cache, RecyclableArrayList<ClientBoundPacketData> packets) {
		ProtocolVersion version = connection.getVersion();
		PEInventoryCache invCache = cache.getPEDataCache().getInventoryCache();
		Any<Integer, TileEntityType> typeData = getContainerData(cache.getOpenedWindow());
		//Get position under client's feet.
		Position position = new Position(
						(int) cache.getClientX() - 2, 
						(int) cache.getClientY() - 2, 
						(int) cache.getClientZ()
					);
		if(typeData != null) {
			//If client is falling or extremely low, get above head.
			if (cache.getPEDataCache().getAttributesCache().isFlying() || cache.getClientY() < 4) {
				position.modifyY(6);
			}
			invCache.getFakeContainers().addFirst(position);
			//Create fake inventory block.
			packets.add(BlockChangeSingle.create(version, position, typeData.getObj1()));
			//Set tile data for fake block.
			NBTTagCompoundWrapper tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
			tag.setString("CustomName", title.toLegacyText(cache.getLocale()));
			if (typeData.getObj2() != TileEntityType.UNKNOWN) {
				tag.setString("id", typeData.getObj2().getRegistryId());
			}
			//Large inventories require doublechest that requires two blocks and nbt.
			if (doDoubleChest(cache)) {
				Position auxPos = position.clone();
				auxPos.modifyX(1); //Get adjacend block.
				invCache.getFakeContainers().addLast(auxPos);
				packets.add(BlockChangeSingle.create(version, auxPos, typeData.getObj1()));
				tag.setInt("pairx", auxPos.getX());
				tag.setInt("pairz", auxPos.getZ());
				tag.setByte("pairlead", 1);
				packets.add(BlockTileUpdate.create(version, position, tag));
				NBTTagCompoundWrapper auxTag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();;
				auxTag.setString("CustomName", title.toLegacyText(cache.getLocale()));
				auxTag.setString("id", typeData.getObj2().getRegistryId());
				auxTag.setInt("pairx", position.getX());
				auxTag.setInt("pairz", position.getZ());
				auxTag.setByte("pairlead", 0);
				packets.add(BlockTileUpdate.create(version, auxPos, auxTag));
				//Schedule the double chest open on the server. The client needs time to settle in.
				InternalPluginMessageRequest.receivePluginMessageRequest(connection, new InternalPluginMessageRequest.InventoryOpenRequest(
						cache.getOpenedWindowId(), cache.getOpenedWindow(), position, -1)
				);
				//Since we probably miss the first contents, request an update.
				InternalPluginMessageRequest.receivePluginMessageRequest(connection, new InternalPluginMessageRequest.InventoryUpdateRequest(4));	
			} else {
				packets.add(BlockTileUpdate.create(version, position, tag));
			}
		}
		return position;
	}
	
	//Check if player has / needs "fake" double chest.
	public static boolean doDoubleChest(NetworkDataCache cache) {
		return (cache.getOpenedWindow() == WindowType.CHEST && cache.getOpenedWindowSlots() > 27);
	}
	
	//Request reset for all fake container blocks.
	public static void destroyFakeContainers(Connection connection, NetworkDataCache cache) {
		cache.getPEDataCache().getInventoryCache().getFakeContainers().cycleDown(position -> {
			InternalPluginMessageRequest.receivePluginMessageRequest(connection, new InternalPluginMessageRequest.BlockUpdateRequest(position));
			return true;
		});
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
		
		public RecyclableArrayList<ClientBoundPacketData> updateNBT(ProtocolVersion version, NetworkDataCache cache) {
			RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
			PEInventoryCache invCache = cache.getPEDataCache().getInventoryCache();
			if (cache.getOpenedWindow() == WindowType.BEACON && invCache.getFakeContainers().hasFirst()) {
				NBTTagCompoundWrapper tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
				tag.setString("id", "beacon");
				tag.setInt("primary", primary);
				tag.setInt("secondary", secondary);
				packets.add(BlockTileUpdate.create(version, invCache.getFakeContainers().getFirst(), tag));
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
