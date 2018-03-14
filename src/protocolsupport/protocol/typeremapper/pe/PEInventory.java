package protocolsupport.protocol.typeremapper.pe;

import java.util.EnumMap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.utils.Any;
import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityDestroy;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventorySetItems;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SpawnLiving;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.storage.netcache.PEInventoryCache;
import protocolsupport.protocol.storage.netcache.WindowCache;
import protocolsupport.protocol.utils.types.MerchantData;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

//Auxiliary class for faking and using different inventories in PE.
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

	//Create matching block and tile change packets to fake inventories and store positions of to reset them later. 
	public static Position prepareFakeInventory(BaseComponent title, Connection connection, NetworkDataCache cache, RecyclableArrayList<ClientBoundPacketData> packets) {
		ProtocolVersion version = connection.getVersion();
		WindowCache winCache = cache.getWindowCache();
		PEInventoryCache invCache = cache.getPEInventoryCache();
		Any<Integer, TileEntityType> typeData = getContainerData(winCache.getOpenedWindow());
		Position position = new Position(0,0,0);
		if (typeData != null) {
			//Get position under client's feet.
			position.setX((int) cache.getMovementCache().getPEClientX() - 2);
			position.setY((int) cache.getMovementCache().getPEClientY() - 2);
			position.setZ((int) cache.getMovementCache().getPEClientZ());
			//If client is falling or extremely low, get above head.
			if (cache.getAttributesCache().isPEFlying() || cache.getMovementCache().getPEClientY() < 4) {
				position.modifyY(6);
			}
			invCache.getFakeContainers().addFirst(position);
			//Create fake inventory block.
			packets.add(BlockChangeSingle.create(version, position, typeData.getObj1()));
			//Set tile data for fake block.
			NBTTagCompoundWrapper tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
			tag.setString("CustomName", title.toLegacyText(cache.getAttributesCache().getLocale()));
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
				auxTag.setString("CustomName", title.toLegacyText(cache.getAttributesCache().getLocale()));
				auxTag.setString("id", typeData.getObj2().getRegistryId());
				auxTag.setInt("pairx", position.getX());
				auxTag.setInt("pairz", position.getZ());
				auxTag.setByte("pairlead", 0);
				packets.add(BlockTileUpdate.create(version, auxPos, auxTag));
				//Since we probably miss the first contents, request an update.
				InternalPluginMessageRequest.receivePluginMessageRequest(connection, new InternalPluginMessageRequest.InventoryUpdateRequest(4));	
			} else {
				packets.add(BlockTileUpdate.create(version, position, tag));
			}
			if (doDoubleChest(cache) || winCache.getOpenedWindow() == WindowType.BEACON) {
				//Schedule the double chest or beacon open on the server. The client needs time to settle in.
				InternalPluginMessageRequest.receivePluginMessageRequest(connection, new InternalPluginMessageRequest.InventoryOpenRequest(
						winCache.getOpenedWindowId(), winCache.getOpenedWindow(), position, -1)
				);
			}
		}
		return position;
	}

	//Check if player has / needs "fake" double chest.
	public static boolean doDoubleChest(NetworkDataCache cache) {
		return (cache.getWindowCache().getOpenedWindow() == WindowType.CHEST && cache.getWindowCache().getOpenedWindowSlots() > 27);
	}

	//Request reset for all fake container blocks.
	public static void destroyFakeContainers(Connection connection, NetworkDataCache cache) {
		cache.getPEInventoryCache().getFakeContainers().cycleDown(position -> {
			InternalPluginMessageRequest.receivePluginMessageRequest(connection, new InternalPluginMessageRequest.BlockUpdateRequest(position));
			return true;
		});
		if (cache.getPEInventoryCache().getTradeVillager().isSpawned()) {
			cache.getPEInventoryCache().getTradeVillager().despawnVillager(connection);
		}
	}

	//To store data to fake an entire beacon.
	public static class BeaconTemple {

		private static final int EMERALD_BLOCK = 133 << 4; 

		private int level = 0;
		private int primary = 0;
		private int secondary = 0;

		public void setLevel(int level) {
			this.level = level;
		}

		public void setPrimaryEffect(int effect) {
			this.primary = effect;
		}

		public void setSecondaryEffect(int effect) {
			this.secondary = effect;
		}

		public RecyclableArrayList<ClientBoundPacketData> updateTemple(ProtocolVersion version, NetworkDataCache cache) {
			RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
			PEInventoryCache invCache = cache.getPEInventoryCache();
			if (cache.getWindowCache().getOpenedWindow() == WindowType.BEACON && invCache.getFakeContainers().hasFirst()) {
				Position position = invCache.getFakeContainers().getFirst();
				for (int i = 1; i < level + 1; i++) {
					for (int x = -i; x < i + 1; x++) {
						for (int z = -i; z < i + 1; z++) {
							Position block = position.clone();
							block.mod(x, -i, z);
							invCache.getFakeContainers().addLast(block);
							packets.add(BlockChangeSingle.create(version, block, EMERALD_BLOCK));
						}
					}
				}
			}
			return packets;
		}

		public RecyclableArrayList<ClientBoundPacketData> updateNBT(ProtocolVersion version, NetworkDataCache cache) {
			RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
			PEInventoryCache invCache = cache.getPEInventoryCache();
			if (cache.getWindowCache().getOpenedWindow() == WindowType.BEACON && invCache.getFakeContainers().hasFirst()) {
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
			return InventorySetItems.create(version, cache.getAttributesCache().getLocale(), cache.getWindowCache().getOpenedWindowId(), contents);
		}

	}

	//To store data to fake trading using fake villager.
	public static class TradeVillager {

		private final long EID = (long) Integer.MAX_VALUE + 1l;
		private final int ETYPE = PEDataValues.getLivingEntityTypeId(NetworkEntityType.VILLAGER);
		private BaseComponent title;
		private boolean spawned = false;

		public boolean isSpawned() {
			return spawned;
		}

		public void setTitle(BaseComponent title) {
			this.title = title;
		}

		public ClientBoundPacketData spawnVillager(NetworkDataCache cache, ProtocolVersion version) {
			spawned = true;
			return SpawnLiving.createSimple(version, EID, 
					cache.getMovementCache().getPEClientX(), 
					cache.getMovementCache().getPEClientY() - 2, 
					cache.getMovementCache().getPEClientZ(), 
			ETYPE);
		}

		public ClientBoundPacketData updateTrade(NetworkDataCache cache, ProtocolVersion version, MerchantData data) {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.TRADE_UPDATE, version);
			MerchantDataSerializer.writePEMerchantData(serializer, 
					version, cache, EID, title.toLegacyText(cache.getAttributesCache().getLocale()), data
			);
			return serializer;
		}

		public void despawnVillager(Connection connection) {
			ByteBuf serializer = Unpooled.buffer();
			VarNumberSerializer.writeVarInt(serializer, PEPacketIDs.ENTITY_DESTROY);
			serializer.writeByte(0);
			serializer.writeByte(0);
			serializer.writeBytes(EntityDestroy.create(connection.getVersion(), EID));
			spawned = false;
			connection.sendRawPacket(MiscSerializer.readAllBytes(serializer));
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
