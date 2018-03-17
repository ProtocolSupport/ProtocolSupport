package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCreativeSetSlot;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClick;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryEnchant;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryTransaction;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.storage.netcache.PEInventoryCache;
import protocolsupport.protocol.storage.netcache.WindowCache;
import protocolsupport.protocol.typeremapper.pe.PEInventory.PESource;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.GameMode;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.ArrayDequeMultiMap;
import protocolsupport.utils.ArrayDequeMultiMap.ChildDeque;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

/**
 * Lo and behold. The PE GodPacket. 
 * DKTAPPS THANK YOU FOR HELPING ME WITH THE VALUES! - This would be insufferable without him.
 * 
 * This packet handles amongst other things
 *  - Using items (Right clicking)
 *  - Interacting (left and right clicking mob)
 *  - Releasing items (shoot bow, eat food)
 *  - Getting creative items
 *  - Renaming items in anvil
 *  - Enchanting items using hopper (FFS)
 *  - MANAGING INVENTORY
 *  
 *  Apart from managing inventories it is the usual packet deal,
 *  except that we have an extra layer of encapsulation.
 *  
 *  Managing inventories works very different in PE, it sends slots, like creative instead of clicks.
 *  To simulate clicks we use a complex system that calculates them.
 *  Debugging can be a pain in the butt, I hope the comments can provide some help.
 */
public class GodPacket extends ServerBoundMiddlePacket {

	private static boolean godlyDebug = true;
	//Wrapped packet
	protected static final int ACTION_NORMAL = 0;
	protected static final int ACTION_MISMATCH = 1;
	protected static final int ACTION_USE_ITEM = 2;
	protected static final int ACTION_USE_ENTITY = 3;
	protected static final int ACTION_RELEASE_ITEM = 4;

	protected UseItem useItemMiddlePacket = new UseItem();
	protected UseEntity useEntityMiddlePacket = new UseEntity();
	protected ReleaseItem releaseItemMiddlePacket = new ReleaseItem();

	@Override
	public void setConnection(Connection connection) {
		super.setConnection(connection);
		useItemMiddlePacket.setConnection(connection);
		useEntityMiddlePacket.setConnection(connection);
		releaseItemMiddlePacket.setConnection(connection);
	}

	@Override
	public void setSharedStorage(NetworkDataCache sharedstorage) {
		super.setSharedStorage(sharedstorage);
		useItemMiddlePacket.setSharedStorage(sharedstorage);
		useEntityMiddlePacket.setSharedStorage(sharedstorage);
		releaseItemMiddlePacket.setSharedStorage(sharedstorage);
	}

	protected int actionId;
	protected InvTransaction[] transactions;
	protected ServerBoundMiddlePacket simpleActionMiddlePacket;

	//TODO: Remove debug (can delete all lines starting with "bug(") if all is well.
	public static void bug(String bugger) {
		if(godlyDebug) { System.out.println(bugger); }
	}
	
	@Override
	public void readFromClientData(ByteBuf clientdata) {
		String locale = cache.getAttributesCache().getLocale();
		bug("NEEWWW GODPACKET! Hooraayy it's a god packet. Don't we all reeeaaally love godpackets? I do. IDDOODD I REALLY DO LOVE THEM. THTHISS IS THE BEST DAY IN MY LIFE.");
		actionId = VarNumberSerializer.readVarInt(clientdata);
		bug("ACTION: " + actionId);

		transactions = new InvTransaction[VarNumberSerializer.readVarInt(clientdata)];
		for(int i = 0; i < transactions.length; i++) {
			transactions[i] = InvTransaction.readFromStream(clientdata, locale, connection.getVersion());
		}

		switch (actionId) {
			case ACTION_USE_ITEM: {
				simpleActionMiddlePacket = useItemMiddlePacket;
				break;
			}
			case ACTION_USE_ENTITY: {
				simpleActionMiddlePacket = useEntityMiddlePacket;
				break;
			}
			case ACTION_RELEASE_ITEM: {
				simpleActionMiddlePacket = releaseItemMiddlePacket;
				break;
			}
			case ACTION_NORMAL:
			case ACTION_MISMATCH:
			default: {
				simpleActionMiddlePacket = null;
				break;
			}
		}

		if (simpleActionMiddlePacket != null) {
			simpleActionMiddlePacket.readFromClientData(clientdata);
		}
		clientdata.skipBytes(clientdata.readableBytes());
	}

	protected static final int SOURCE_CONTAINER = 0;
	protected static final int SOURCE_GLOBAL = 1;
	protected static final int SOURCE_WORLD_INTERACTION = 2;
	protected static final int SOURCE_CREATIVE = 3;
	protected static final int SOURCE_TODO = 99999;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		PEInventoryCache invCache = cache.getPEInventoryCache();
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		if (simpleActionMiddlePacket != null) {
			simpleActionMiddlePacket.toNative();
		} else if (actionId == ACTION_NORMAL) {
			for (InvTransaction transaction : transactions) {
				invCache.getInfTransactions().cacheTransaction(cache, transaction);
			}
			packets.addAll(invCache.getInfTransactions().process(cache));
		}
		if (invCache.shouldSendUpdate() && cache.getAttributesCache().getPEGameMode() != GameMode.CREATIVE) {
			//Trigger inventory update, ALWAYS since PE sometimes 'guesses' or doesn't trust the server, we generally want an inventory update scheduled.
			InternalPluginMessageRequest.receivePluginMessageRequest(connection, new InternalPluginMessageRequest.InventoryUpdateRequest(7));
			invCache.lockInventoryUpdate();
		}
		return packets;
	}

	protected static class InvTransaction {

		private int sourceId;
		private int inventoryId;
		private int action;
		private int slot;
		private ItemStackWrapper oldItem;
		private ItemStackWrapper newItem;

		private static InvTransaction readFromStream(ByteBuf from, String locale, ProtocolVersion version) {
			InvTransaction transaction = new InvTransaction();
			transaction.sourceId = VarNumberSerializer.readVarInt(from);
			switch (transaction.sourceId) {
				case SOURCE_CONTAINER: {
					transaction.inventoryId = VarNumberSerializer.readSVarInt(from);
					break;
				}
				case SOURCE_WORLD_INTERACTION: {
					transaction.inventoryId = PESource.POCKET_FAUX_DROP;
					transaction.action = VarNumberSerializer.readVarInt(from);
					break;
				}
				case SOURCE_TODO: {
					transaction.inventoryId = VarNumberSerializer.readSVarInt(from);
					transaction.action = 0;
					break;
				}
				case SOURCE_GLOBAL:
				case SOURCE_CREATIVE:
				default: {
					break;
				}
			}
			transaction.slot = VarNumberSerializer.readVarInt(from);
			transaction.oldItem = ItemStackSerializer.readItemStack(from, version, locale, true);
			transaction.newItem = ItemStackSerializer.readItemStack(from, version, locale, true);
			bug("Inv transaction read: sId: " + transaction.sourceId + " wId: " + transaction.inventoryId + " action: " + transaction.action + " slot: " + transaction.slot 
					+ " oldItem: " + transaction.oldItem.toString()  + ((!transaction.oldItem.isNull()) ? transaction.oldItem.getTag() : "") + " newItem: " + transaction.newItem.toString() + (!transaction.newItem.isNull() ? transaction.newItem.getTag() : ""));
			return transaction;
		}
		
		public int getSourceId() {
			return sourceId;
		}


		public int getInventoryId() {
			return inventoryId;
		}


		public int getAction() {
			return action;
		}


		public int getSlot() {
			return slot;
		}
		
		public ItemStackWrapper getOldItem() {
			return oldItem;
		}


		public ItemStackWrapper getNewItem() {
			return newItem;
		}

		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}

	}
	
	public static class InfTransactions {
		private ArrayDequeMultiMap<ItemStackWrapperKey, SlotWrapperValue> surplusDeque = new ArrayDequeMultiMap<>();
		private ArrayDequeMultiMap<ItemStackWrapperKey, SlotWrapperValue> deficitDeque = new ArrayDequeMultiMap<>();
		private List<ServerBoundPacketData> misc = new ArrayList<>(1);

		public void clear() {
			surplusDeque.clear();
			deficitDeque.clear();
			misc.clear();
		}

		public void customCursorSurplus(NetworkDataCache cache, ItemStackWrapper itemstack) {
			clear();
			if ((!itemstack.isNull()) && !((cache.getAttributesCache().getPEGameMode() == GameMode.CREATIVE) && (cache.getWindowCache().getOpenedWindow() == WindowType.PLAYER))) {
				bug("SERVER INTERVENTION ON CURSOR ITEM!! Clearing surplus deficit thingy!");
				//TODO removing comparing debug code when are all kinks are worked out.
				ByteBuf screwThis = Unpooled.buffer();
				ItemStackSerializer.writeItemStack(screwThis, ProtocolVersion.MINECRAFT_PE, cache.getAttributesCache().getLocale(), itemstack, true);
				ItemStackWrapper remapped = ItemStackSerializer.readItemStack(screwThis, ProtocolVersion.MINECRAFT_PE, cache.getAttributesCache().getLocale(), true);
				if(!itemstack.isNull()) {
					System.out.println("COMPARING");
					System.out.println(itemstack.getTag());
					System.out.println(remapped.getTag());
				}
				surplusDeque.put(new ItemStackWrapperKey(remapped), new SlotWrapperValue(-1, itemstack.getAmount(), itemstack.getAmount()));
			}
		}

		public void cacheTransaction(NetworkDataCache cache, InvTransaction transaction) {
			WindowCache winCache = cache.getWindowCache();
			PEInventoryCache invCache = cache.getPEInventoryCache();
			int pcSlot = transformSlot(cache, transaction.getInventoryId(), transaction.getSlot());
			
			bug("Going through cache stuff with slot: " + pcSlot);
			
			//Signifies death. We don't want unmapped slots to mess it all up.
			if (pcSlot == -666) {
				return;
			}
			
			//Creative transactions.
			if ((cache.getAttributesCache().getPEGameMode() == GameMode.CREATIVE) && (winCache.getOpenedWindow() == WindowType.PLAYER)) {
				if ((transaction.getSourceId() != SOURCE_CREATIVE) && (pcSlot != -1)) {
					if(!transaction.getNewItem().isNull()) System.out.println(transaction.getNewItem().getTag());
					//Creative transaction use -1 not for cursor but throwing items, cursoritems are actually deleted on serverside.
					misc.add(MiddleCreativeSetSlot.create(cache.getAttributesCache().getLocale(), (pcSlot == -999 ? -1 : pcSlot), transaction.getNewItem()));
					if (pcSlot == invCache.getSelectedSlot()) {
						invCache.setItemInHand(transaction.getNewItem());
					}
				}
				return;
			}
			
			//Anvil naming is only done and known based on the clicked item.
			if (pcSlot == 2 && winCache.getOpenedWindow() == WindowType.ANVIL && !transaction.getOldItem().isNull()) {
				NBTTagCompoundWrapper tag = transaction.getOldItem().getTag();
				if (tag.hasKeyOfType("display", NBTTagType.COMPOUND)) {
					NBTTagCompoundWrapper display = tag.getCompound("display");
					if (display.hasKeyOfType("Name", NBTTagType.STRING)) {
						misc.add(anvilName(display.getString("Name")));
					}
				}
			}
			
			//Enchantment via hoppers. Yes, it's a hack but it's only possible this way.
			if (winCache.getOpenedWindow() == WindowType.ENCHANT) {
				if (pcSlot == 0) {
					invCache.getEnchantHopper().setInputOutputStack(transaction.getNewItem());
				} else if (pcSlot == 1 && (transaction.getNewItem().isNull() || (transaction.getNewItem().getType() == Material.INK_SACK && transaction.getNewItem().getData() == 4))) {
					invCache.getEnchantHopper().setLapisStack(transaction.getNewItem());
				} else if (pcSlot > 1 && pcSlot <= 4 && transaction.getInventoryId() != PESource.POCKET_INVENTORY) { //If and only if on of the three fake hopper option slots are clicked proceed with the enchanting.
					misc.add(MiddleInventoryEnchant.create(winCache.getOpenedWindowId(), pcSlot - 2));
					return;
				}
			}
			
			//"Normal" transactions.
			ItemStackWrapperKey oldItemKey = new ItemStackWrapperKey(transaction.getOldItem());
			ItemStackWrapperKey newItemKey = new ItemStackWrapperKey(transaction.getNewItem());
			
			if (oldItemKey.equals(newItemKey) && pcSlot != -1) {
				int money = transaction.getOldItem().getAmount() - transaction.getNewItem().getAmount();
				if(money > 0) {
					bug("CACHING SURPLUS C - " + oldItemKey.toString() + " - " + money);
					//If there's some money to spend, put it in the suitcase.
					surplusDeque.put(oldItemKey, new SlotWrapperValue(pcSlot, transaction.getOldItem().getAmount(), money));
				} else {
					bug("CACHING DEFICIT C - " + oldItemKey.toString() + " - " + money);
					//Unless its the cursor (which is already in the system) add the debt.
					deficitDeque.put(newItemKey, new SlotWrapperValue(pcSlot, transaction.getOldItem().getAmount(), -money));	
				}
			} else {
				if((!transaction.getOldItem().isNull()) && (pcSlot != -1)) {
					if (
						(transaction.getInventoryId() == PESource.POCKET_CRAFTING_GRID_REMOVE ||
						 transaction.getInventoryId() == PESource.POCKET_ANVIL_INPUT || 
						 transaction.getInventoryId() == PESource.POCKET_ANVIL_MATERIAL
						) && (deficitDeque.containsKey(oldItemKey))) {
						//Weird case for the crafting table. Mojang thought it a bright idea to split the adding and removing in there.
						for (SlotWrapperValue deficit : deficitDeque.get(oldItemKey)) {
							if(deficit.slot() == pcSlot) {
								int newDeficit = deficit.amount() - transaction.getOldItem().getAmount();
								deficit.setAmount(newDeficit <= 0 ? 0 : newDeficit);
								if (newDeficit <= 0) {
									deficitDeque.get(oldItemKey).remove(deficit);
									if(newDeficit < 0) { surplusDeque.put(oldItemKey, new SlotWrapperValue(pcSlot, transaction.getOldItem().getAmount(), -newDeficit)); }
								}
								return;
							}
						}
					}
					//Unless its the cursor (which is already in the system) add the debt.
					bug("CACHING SURPLUS N - " + oldItemKey.toString() + " - " + transaction.getOldItem().getAmount());
					surplusDeque.put(oldItemKey, new SlotWrapperValue(pcSlot, transaction.getOldItem().getAmount(), transaction.getOldItem().getAmount()));
				}
				if(!transaction.getNewItem().isNull()) {
					if (
					    (
					     transaction.getInventoryId() == PESource.POCKET_CRAFTING_GRID_ADD ||
					     transaction.getInventoryId() == PESource.POCKET_ANVIL_INPUT ||
					     transaction.getInventoryId() == PESource.POCKET_ANVIL_MATERIAL
					    ) && (surplusDeque.containsKey(newItemKey))) {
						//Weird case for the crafting table. Mojang thought it a bright idea to split the adding and removing in there.
						for (SlotWrapperValue surplus : surplusDeque.get(newItemKey)) {
							if(surplus.slot() == pcSlot) {
								int newSurplus = surplus.amount() - transaction.getNewItem().getAmount();
								surplus.setAmount(newSurplus);
								if (newSurplus <= 0) {
									surplusDeque.get(newItemKey).remove(surplus);
									if (newSurplus < 0) { deficitDeque.put(newItemKey, new SlotWrapperValue(pcSlot, 0, -newSurplus)); }
								}
								return;
							}
						}
					}
					//If there's some money to spend, put it in the suitcase.
					bug("CACHING DEFICIT N - " + newItemKey.toString() + " - " + transaction.getNewItem().getAmount());
					deficitDeque.put(newItemKey, new SlotWrapperValue(pcSlot, 0, transaction.getNewItem().getAmount()), pcSlot == -1);
				}
			}
			
		}
		
		public RecyclableArrayList<ServerBoundPacketData> process(NetworkDataCache cache) {
			RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
			//Since pocket simulates most things clientside, 
			//we stop any incoming inventory traffic when processing slots
			//until the manual override kicks in.
			cache.getPEInventoryCache().lockInventory();
			
			bug("PROCESSING MISC");
			
			//Send misc data. (Creative inventory, anvils, etc)
			if(!misc.isEmpty()) {
				packets.addAll(misc);
				misc.clear();
			}
			
			bug("PROCESSING DEBT SURPLUS");
			
			//Match surpluses with defcitis.
			deficitDeque.cycleUp((item, deficits) -> {
				bug("HIT UP for DEFITCITTTT " + item + " SIZE: " + deficits.size());
				ChildDeque<SlotWrapperValue> surpluses = surplusDeque.get(item);
				if(surpluses != null) {
					Fin finish = new Fin();
					while(!finish.fin()) {
						deficits.cycleUp(deficit -> {
							bug(item + " defict: " + deficit.slot() + " - " + deficit.amount());
							finish.setFin(true);
							surpluses.cycleDown(surplus -> {
								bug("Surplus by deficit: " + surplus.slot() + " - " + surplus.amount());
								//Early copout, because Mojang sometimes really screws up.
								if (surplus.isEmpty()) { return true; }
								//We want to get all we can, but not more than we need.
								int toPay = deficit.amount() < surplus.amount() ? deficit.amount() : surplus.amount();
								bug("Needing to pay: " + toPay);
								//The real stuff:
								if (!surplus.isCursor() && !deficit.isToUseInTable() && !(deficit.isCursor() && deficit.hasStack() && surplus.isCraftingResult(cache))) {
									bug("Need to get the surplus from inventory");
									//Unless the surplus is already in the cursor, (or this is the second crafting click where we only want one) we need to get it.
									packets.addAll(Click.LEFT.create(cache, surplus.slot(), item.get(surplus.slotAmount())));
								}
								if (deficit.isCursor() && !surplus.isCursor()) {
									if (deficit.hasStack()) {
										bug("Deficit is in cursor and we already clicked so we need to pick it up again.");
										//We already had contents in the cursor, so just one left click won't cut it.
										packets.addAll(Click.LEFT.create(cache, surplus.slot(), item.get(surplus.slotAmount() + deficit.slotAmount())));
									}
									bug("Clicking until we have the correct number of items in our cursor.");
									//Put back the items from the cursor that we don't want.
									for (int i = 0; i < (surplus.slotAmount() - toPay); i++) {
										packets.addAll(Click.RIGHT.create(cache, surplus.slot(), (i == 0) ? ItemStackWrapper.NULL : item.get(i)));
									}
								} else if (!deficit.isCursor() && !deficit.isToUseInTable()) {
									if (deficit.amount() == surplus.slotAmount()) {
										if (!((surplusDeque.getVeryLast() != null) && (deficit.slot() == surplusDeque.getVeryLast().slot()))) {
											bug("Paying out full leftoverstack");
											//Unless we're swapping (then only one left click is sent on next deficit), we can payout the stack in full (left-click)
											packets.addAll(Click.LEFT.create(cache, deficit.slot(), (!deficit.hasStack()) ? ItemStackWrapper.NULL : item.get(deficit.slotAmount())));
										} else {
											bug("Swapping?");
										}
									} else {
										bug("Paying out in complements.");
										//We need to pay what we need to pay.
										for (int i = 0; i < toPay; i++) {
											packets.addAll(Click.RIGHT.create(cache, deficit.slot(), ((deficit.slotAmount() + i) == 0) ? ItemStackWrapper.NULL : item.get(deficit.slotAmount() + i)));
										}
									}
								}
								//Payout the trackers.
								surplus.pay(toPay); 
								deficit.receive(toPay);
								bug("Actually updated deficit & surplus values.");
								bug(item + " defict: " + deficit.slot() + " - " + deficit.amount());
								bug("Surplus by deficit: " + surplus.slot() + " - " + surplus.amount());
								//Last but not least, put back our access surplus and or containing stack.
								if(!surplus.isCursor() && !deficit.isToUseInTable() && !deficit.isCursor() && surplus.hasStack()) {
									bug("Putting back access surplus.");
									packets.addAll(Click.LEFT.create(cache, surplus.slot(), ItemStackWrapper.NULL));
								}
								return surplus.isEmpty();
							});
							if (surpluses.isEmpty()) {
								bug("Removing surplus deque.");
								surplusDeque.remove(item);
							} else {
								if (surpluses.size() == 1 && deficits.size() == 1 && deficit.isEmpty()) {
									bug("Wrapping surpluss to deficit to accomadate for remainder.");
									//If we have just one item left, add it as deficit to the cursor so we end up with that in our hands.
									//This ensures that some left-drag actions having a remainder that doesn't change (e.g. 19/2*9 or 3*6) 
									//still puts items in the cursor. (Because PE ONLY sends changes)
									deficits.addFirst(new SlotWrapperValue(-1, 0, surpluses.getLast().amount()));
									finish.setFin(false);
								}
							}
							//If there's still something in the cursor we want it to be accessed next round.
							if(deficit.isCursor() && deficit.hasStack()) {
								bug("Putting access cursor to surplus for next round.");
								surplusDeque.put(item, deficit.setAmount(deficit.slotAmount()), true);
								return true;
							}
							return deficit.isEmpty();
						});
					}
				}
				return deficits.isEmpty();
			});
			
			//Don't worry this is just debug.
			if (godlyDebug) {
				deficitDeque.cycleDown((item, deficits) -> {
					bug("ITEM LEFT: " + item + " DEFICITS:");
					deficits.cycleDown(deficit -> {bug("D" + deficit.slot() + " - " + deficit.amount()); return false;});
					return false;
				});
				surplusDeque.cycleDown((item, deficits) -> {
					bug("ITEM LEFT: " + item + " SURPLUSSES:");
					deficits.cycleDown(deficit -> {bug("S" + deficit.slot() + " - " + deficit.amount()); return false;});
					return false;
				});
				bug("Sending " + packets.size() + " packets.... :S"); 
			}
			return packets;
		}
		
	}
	

	protected static class Fin {
		
		private boolean fin = false;
		
		public boolean fin() {
			return fin;
		}
		
		public void setFin(boolean fin) {
			this.fin = fin;
		}
		
	}
	
	
	protected static class SlotWrapperValue {
		
		private int slotValue;
		
		public SlotWrapperValue(int slot, int slotAmount, int amount) {
			slotValue = (slot << 16 | slotAmount << 8 | amount);
		}
		
		public int slot() {
			return slotValue >> 16;
		}
		
		public int slotAmount() {
			return (slotValue >> 8) & 0xFF;
		}
		
		public int amount() {
			return slotValue & 0xFF;
		}
		
		public SlotWrapperValue setAmount(int amount) {
			slotValue = (slotValue & ~(0xFF)) | amount;
			return this;
		}
		
		public void pay(int amount) {
			slotValue -= (amount << 8 | amount);
		}
		
		public void receive(int amount) {
			slotValue += ((amount << 8) - amount);
		}
		
		public boolean hasStack() {
			return slotAmount() != 0;
		}
		
		public boolean isCursor() {
			return slot() == -1;
		}
		
		public boolean isToUseInTable() {
			return slot() == -333;
		}
		
		public boolean isCraftingResult(NetworkDataCache cache) {
			return slot() == 0 && cache.getWindowCache().getOpenedWindow() == WindowType.CRAFTING_TABLE;
		}
		
		public boolean isEmpty() {
			return amount() <= 0;
		}
		
	}
	
	protected static class ItemStackWrapperKey {
		
		private ItemStackWrapper keyItem = ItemStackWrapper.NULL;
		private int hash = 0;
		
		public ItemStackWrapperKey(ItemStackWrapper keyItem) {
			if(!keyItem.isNull()) {
				this.keyItem = keyItem;
				hash = 41 * hash + keyItem.getTypeId();
				hash = 41 * hash + keyItem.getData();
				hash = 41 * hash + keyItem.getTag().hashCode();
			}
		}
		
		@Deprecated
		public ItemStackWrapper getKeyItem() {
			return keyItem;
		}
		
		public ItemStackWrapper get(int amount) {
			ItemStackWrapper item = getKeyItem().cloneItemStack();
			item.setAmount(amount);
			return item;
		}
		
		@Override
		public int hashCode() {
			return hash;
		}
		
		@Override
		public boolean equals(Object object) {
			if(object instanceof ItemStackWrapperKey) {
				ItemStackWrapperKey wrapper = (ItemStackWrapperKey) object;
				if(wrapper.getKeyItem().isNull() || this.getKeyItem().isNull()) {
					return false;
				}
				return 
						(wrapper.getKeyItem().getTypeId() == this.getKeyItem().getTypeId()) &&
						(wrapper.getKeyItem().getData() == this.getKeyItem().getData()) &&
						(wrapper.getKeyItem().getTag().equals(this.getKeyItem().getTag()));
			}
			return false;
			
		}
		
		@Override
		public String toString() {
			return keyItem.toString();
		}
		
	}
	
	private static int transformSlot(NetworkDataCache cache, int peInventoryId, int peSlot) {
		WindowCache winCache = cache.getWindowCache();
		switch(winCache.getOpenedWindow()) {
			case PLAYER: {
				switch(peInventoryId) {
					case PESource.POCKET_CRAFTING_RESULT: {
						return 0;
					}
					case PESource.POCKET_CRAFTING_GRID_ADD:
					case PESource.POCKET_CRAFTING_GRID_REMOVE: {
						return peSlot + 1;
					}
					case PESource.POCKET_ARMOR_EQUIPMENT: {
						return peSlot + 5;
					}
					case PESource.POCKET_OFFHAND: {
						return 45;
					}
					case PESource.POCKET_CRAFTING_GRID_USE_INGREDIENT:  {
						return -333; //Fake id for craft tracking.
					}
					case PESource.POCKET_FAUX_DROP:
					case PESource.POCKET_CLICKED_SLOT:
					case PESource.POCKET_INVENTORY: {
						return invSlotToContainerSlot(peInventoryId, 9, peSlot);
					}
					default: {
						return -666;
					}
				}
			}
			case BREWING: {
				if (peInventoryId == winCache.getOpenedWindowId()) {
					if(peSlot == 0) {
						return 3;
					} else if (peSlot >= 1 && peSlot <= 3) {
						return peSlot - 1;
					}
				}
				return invSlotToContainerSlot(peInventoryId, 5, peSlot);
			}
			case ANVIL: {
				switch(peInventoryId) {
					case PESource.POCKET_ANVIL_INPUT: {
						return 0;
					}
					case PESource.POCKET_ANVIL_MATERIAL: {
						return 1;
					}
					case PESource.POCKET_ANVIL_RESULT:
					case PESource.POCKET_ANVIL_OUTPUT: {
						return 2;
					}
				}
				return invSlotToContainerSlot(peInventoryId, 3, peSlot);
			}
			case ENCHANT: {
				//We fake enchanting with hoppers, but the server slots are still 0 and 1 for the inventory.
				return invSlotToContainerSlot(peInventoryId, 2, peSlot);
			}
			case BEACON: {
				if (peInventoryId == PESource.POCKET_BEACON) {
					return 0;
				} else {
					return invSlotToContainerSlot(peInventoryId, 1, peSlot);
				}
			}
			case CRAFTING_TABLE: {
				switch(peInventoryId) {
					case PESource.POCKET_CRAFTING_RESULT: {
						return 0;
					}
					case PESource.POCKET_CRAFTING_GRID_ADD:
					case PESource.POCKET_CRAFTING_GRID_REMOVE: {
						return peSlot + 1;
					}
					case PESource.POCKET_CRAFTING_GRID_USE_INGREDIENT:  {
						return -333; //Fake id for tracking ingredients.
					}
					case PESource.POCKET_FAUX_DROP:
					case PESource.POCKET_CLICKED_SLOT:
					case PESource.POCKET_INVENTORY: {
						return invSlotToContainerSlot(peInventoryId, 10, peSlot);
					}
					default: {
						return -666;
					}
				}
			}
			case VILLAGER: {
				switch(peInventoryId) {
					case PESource.POCKET_TRADE_INPUT_1: {
						return 0;
					}
					case PESource.POCKET_TRADE_INPUT_2: {
						return 1;
					}
					case PESource.POCKET_TRADE_OUTPUT: {
						return 2;
					}
					case PESource.POCKET_FAUX_DROP:
					case PESource.POCKET_CLICKED_SLOT:
					case PESource.POCKET_INVENTORY: {
						return invSlotToContainerSlot(peInventoryId, 3, peSlot);
					}
					default: {
						return -666;
					}
				}
			}
			case HORSE: {
				if (peInventoryId == winCache.getOpenedWindowId()) {
					NetworkEntity horse = cache.getWatchedEntityCache().getWatchedEntity(winCache.getHorseId());
					if (horse != null) {
						switch(horse.getType()) {
							case DONKEY:
							case MULE: {
								if (peSlot == 0) {
									return 0;
								} else {
									return peSlot + 1;
								}
							}
							case LAMA: {
								if (peSlot == 0) {
									return 1;
								} else {
									return peSlot + 1;
								}
							}
							default: {
								break; //Fallthrough to defualt.
							}
						}
					}
				}
			}
			default: {
				int wSlots = winCache.getOpenedWindowSlots();
				if(wSlots > 16) { wSlots = wSlots / 9 * 9; }
				return invSlotToContainerSlot(peInventoryId, wSlots, peSlot);
			}
		}
	}
	
	private static int invSlotToContainerSlot(int peInventoryId, int start, int peSlot) {
		switch(peInventoryId) {
			case PESource.POCKET_CLICKED_SLOT: {
				return -1;
			}
			case PESource.POCKET_FAUX_DROP: {
				return peInventoryId;
			}
			case PESource.POCKET_INVENTORY: {
				if (peSlot < 9) {
					return peSlot + (27 + start);
				} else {
					return peSlot - (9 - start);
				}
			}
			default: {
				return peSlot;
			}
		}
	}
	
	private static ServerBoundPacketData anvilName(String name) {
		ByteBuf payload = Unpooled.buffer();
		StringSerializer.writeString(payload, ProtocolVersionsHelper.LATEST_PC, name);
		return MiddleCustomPayload.create("MC|ItemName", MiscSerializer.readAllBytes(payload));
	}
	
	protected enum Click {
		LEFT	(0, 0),
		RIGHT	(0, 1);
		
		private final int mode;
		private final int button;
		
		Click(int mode, int button) {
			this.mode = mode;
			this.button = button;
		}
		
		public RecyclableArrayList<ServerBoundPacketData> create(NetworkDataCache cache, int slot, ItemStackWrapper item) {
			RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
			int actionNumber = cache.getPEInventoryCache().getActionNumber();
			packets.add(MiddleInventoryClick.create(cache.getAttributesCache().getLocale(), cache.getWindowCache().getOpenedWindowId(), slot, button, actionNumber, mode, item));
			if(!item.isNull() && item.getTag() != null && !item.getTag().isNull()) {
				bug("My apologies??!!?!?!");
				packets.add(MiddleInventoryTransaction.create(cache.getWindowCache().getOpenedWindowId(), actionNumber, false));
			}
			return packets;
		}
		
	}

}
