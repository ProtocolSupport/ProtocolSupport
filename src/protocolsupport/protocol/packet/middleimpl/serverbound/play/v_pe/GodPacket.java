package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCreativeSetSlot;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleHeldSlot;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClick;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryTransaction;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.typeremapper.pe.PEInventory.PESource;
import protocolsupport.protocol.utils.types.GameMode;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.ArrayDequeMultiMap;
import protocolsupport.utils.ArrayDequeMultiMap.ChildDeque;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

/**
 * Lo and behold. The PE GodPacket. 
 * DKTAPPS THANK YOU FOR HELPING ME WITH THE VALUES! - This would be insufferable without him.
 * 
 * This packet handles among other things
 *  - Using items (Right clicking)
 *  - Interacting (left and right clicking mob)
 *  - Releasing items (shoot bow, eat food)
 *  - Getting creative items
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
	
	//Transactions
	protected InfTransaction[] transactions;
	//Complex Actions
	protected int actionId;
	protected int subTypeId = -1;
	protected ItemStackWrapper itemstack;
	protected int slot;
	protected Position position = new Position(0, 0, 0);
	protected float fromX, fromY, fromZ;
	protected float cX, cY, cZ;
	protected int face;
	protected int targetId;

	public static void bug(String bugger) {
		System.out.println(bugger);
	}
	
	@Override
	public void readFromClientData(ByteBuf clientdata) {
		System.out.println("NEEWWW GODPACKET! Hooraayy it's a god packet. Don't we all reeeaaally love godpackets? I do. IDDOODD I REALLY DO LOVE THEM. THTHISS IS THE BEST DAY IN MY LIFE.");
		actionId = VarNumberSerializer.readVarInt(clientdata);

		transactions = new InfTransaction[VarNumberSerializer.readVarInt(clientdata)];
		for(int i = 0; i < transactions.length; i++) {
			transactions[i] = InfTransaction.readFromStream(clientdata, cache.getLocale(), connection.getVersion());
		}

		switch(actionId) {
			case ACTION_USE_ITEM: {
				subTypeId = VarNumberSerializer.readVarInt(clientdata);
				PositionSerializer.readPEPositionTo(clientdata, position);
				face = VarNumberSerializer.readSVarInt(clientdata);
				slot = VarNumberSerializer.readSVarInt(clientdata);
				itemstack = ItemStackSerializer.readItemStack(clientdata, connection.getVersion(), cache.getLocale(), true);
				fromX = MiscSerializer.readLFloat(clientdata); fromY = MiscSerializer.readLFloat(clientdata); fromZ = MiscSerializer.readLFloat(clientdata);
				cX = MiscSerializer.readLFloat(clientdata); cY = MiscSerializer.readLFloat(clientdata); cZ = MiscSerializer.readLFloat(clientdata);
				break;
			}
			case ACTION_INTERACT: {
				targetId = (int) VarNumberSerializer.readVarLong(clientdata);
				subTypeId = VarNumberSerializer.readVarInt(clientdata);
				slot = VarNumberSerializer.readSVarInt(clientdata);
				itemstack = ItemStackSerializer.readItemStack(clientdata, connection.getVersion(), cache.getLocale(), true);
				fromX = MiscSerializer.readLFloat(clientdata); fromY = MiscSerializer.readLFloat(clientdata); fromZ = MiscSerializer.readLFloat(clientdata);
				cX = MiscSerializer.readLFloat(clientdata); cY = MiscSerializer.readLFloat(clientdata); cZ = MiscSerializer.readLFloat(clientdata);
				break;
			}
			case ACTION_RELEASE_ITEM: {
				subTypeId = VarNumberSerializer.readVarInt(clientdata);
				slot = VarNumberSerializer.readSVarInt(clientdata);
				itemstack = ItemStackSerializer.readItemStack(clientdata, connection.getVersion(), cache.getLocale(), true);
				fromX = MiscSerializer.readLFloat(clientdata); fromY = MiscSerializer.readLFloat(clientdata); fromZ = MiscSerializer.readLFloat(clientdata);
				break;
			}
			case ACTION_NORMAL:
			case ACTION_MISMATCH:
			default: {
				break;
			}
		}
		//BLEEHH!
		clientdata.readBytes(clientdata.readableBytes());
	}

	//Sources
	public static final int SOURCE_CONTAINER = 0;
	public static final int SOURCE_GLOBAL = 1;
	public static final int SOURCE_WORLD_INTERACTION = 2;
	public static final int SOURCE_CREATIVE = 3;
	public static final int SOURCE_TODO = 99999;
	//Actions
	public static final int ACTION_NORMAL = 0;
	public static final int ACTION_MISMATCH = 1;
	public static final int ACTION_USE_ITEM = 2;
	public static final int ACTION_INTERACT = 3;
	public static final int ACTION_RELEASE_ITEM = 4;
	//Action - Item Release
	public static final int RELEASE_RELEASE = 0;
	public static final int RELEASE_CONSUME = 1;
	//Action - Item Use
	public static final int USE_CLICK_BLOCK = 0;
	public static final int USE_CLICK_AIR = 1;
	public static final int USE_DIG_BLOCK = 2;
	//Action - Interact
	public static final int INTERACT_INTERACT = 0;
	public static final int INTERACT_ATTACK = 1;
	public static final int INTERACT_AT = 2;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		switch(actionId) {
			case ACTION_USE_ITEM: {
				packets.add(MiddleHeldSlot.create(slot));
				bug("CLICK! Face: " + face + "Subtype: " + subTypeId);
				switch(subTypeId) {
					case USE_CLICK_AIR:
						face = -1;
					case USE_CLICK_BLOCK: {
						packets.add(MiddleBlockPlace.create(position, face, 0, cX, cY, cZ));
						break;
					}
					case USE_DIG_BLOCK: {
						if(cache.getGameMode() == GameMode.CREATIVE) { //instabreak
							packets.add(MiddleBlockDig.create(MiddleBlockDig.Action.START_DIG, position, 0));
							packets.add(MiddleBlockDig.create(MiddleBlockDig.Action.FINISH_DIG, position, 0));
						}
						break;
					}
				}
				break;
			}
			case ACTION_INTERACT: {
				packets.add(MiddleHeldSlot.create(slot));
				switch(subTypeId) {
					case INTERACT_INTERACT: {
						NetworkEntity target = cache.getWatchedEntity(targetId);
						if(target == null || !target.isOfType(NetworkEntityType.ARMOR_STAND)) {
							packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.INTERACT, null, 0));
						}
						packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.INTERACT_AT, new Vector(cX, cY, cZ), 0));
						break;
					}
					case INTERACT_ATTACK: {
						packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.ATTACK, null, 0));
						break;
					}
					case INTERACT_AT: {
						packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.INTERACT_AT, new Vector(cX, cY, cZ), 0));
						break;
					}
				}
				break;
			}
			case ACTION_RELEASE_ITEM: {
				packets.add(MiddleHeldSlot.create(slot));
				switch(subTypeId) {
					case RELEASE_RELEASE: {
						bug("RELEASING!");
						packets.add(MiddleBlockDig.create(MiddleBlockDig.Action.FINISH_USE, new Position(0, -0, 0), 0));
						break;
					}
					case RELEASE_CONSUME: {
						bug("CONSUMING");
						break;
					}
				}
				break;
			}
			case ACTION_NORMAL: { //Normal inventory transaction.
				for(InfTransaction transaction : transactions) {
					cache.getInfTransactions().cacheTransaction(cache, transaction);
				}
				packets.addAll(cache.getInfTransactions().process(cache));
				break;
			}
			case ACTION_MISMATCH:
			default: {
				break;
			}
		}
		return packets;
	}

	public static class InfTransaction {

		private int sourceId;
		private int inventoryId;
		private int action;
		private int slot;
		private ItemStackWrapper oldItem;
		private ItemStackWrapper newItem;

		private static InfTransaction readFromStream(ByteBuf from, String locale, ProtocolVersion version) {
			InfTransaction transaction = new InfTransaction();
			transaction.sourceId = VarNumberSerializer.readVarInt(from);
			switch(transaction.sourceId) {
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
			System.out.println("Inv transaction read: sId: " + transaction.sourceId + " wId: " + transaction.inventoryId + " action: " + transaction.action + " slot: " + transaction.slot 
					+ " oldItem: " + transaction.oldItem.toString() + " newItem: " + transaction.newItem.toString());
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
		private List<ServerBoundPacketData> misc = new ArrayList<>();
		
		public void clear() {
			surplusDeque.clear();
			deficitDeque.clear();
			misc.clear();
		}
		
		public void customCursorSurplus(NetworkDataCache cache, ItemStackWrapper itemstack) {
			if ((!itemstack.isNull()) && !((cache.getGameMode() == GameMode.CREATIVE) && (cache.getOpenedWindow() == WindowType.PLAYER))) {
				clear();
				surplusDeque.put(new ItemStackWrapperKey(itemstack), new SlotWrapperValue(-1, itemstack.getAmount(), itemstack.getAmount()));
			}
		}
		
		public void cacheTransaction(NetworkDataCache cache, InfTransaction transaction) {
			ItemStackWrapperKey oldItemKey = new ItemStackWrapperKey(transaction.getOldItem());
			ItemStackWrapperKey newItemKey = new ItemStackWrapperKey(transaction.getNewItem());
			int pcSlot = transformSlot(cache, transaction.getInventoryId(), transaction.getSlot());
			
			bug("Going through cache stuff with slot: " + pcSlot);
			
			//Signifies death. We don't want unmapped slots to mess it all up.
			if (pcSlot == -666) {
				return;
			}
			
			//Creative transactions.
			if ((cache.getGameMode() == GameMode.CREATIVE) && (cache.getOpenedWindow() == WindowType.PLAYER)) {
				if ((transaction.getSourceId() != SOURCE_CREATIVE) && (pcSlot != -1)) {
					//Creative transaction use -1 not for cursor but throwing items, cursoritems are actually deleted on serverside.
					misc.add(MiddleCreativeSetSlot.create(cache.getLocale(), (pcSlot == -999 ? -1 : pcSlot), transaction.getNewItem()));
				}
				return;
			}
			
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
					if (transaction.getInventoryId() == PESource.POCKET_CRAFTING_GRID_REMOVE && deficitDeque.containsKey(oldItemKey)) {
						//Weird case for the crafting table. Mojang thought it a bright idea to split the adding and removing in there.
						for (SlotWrapperValue deficit : deficitDeque.get(oldItemKey)) {
							if(deficit.slot() == pcSlot) {
								int newDeficit = deficit.amount() - transaction.getOldItem().getAmount();
								deficit.setAmount(newDeficit <= 0 ? 0 : newDeficit);
								if(newDeficit < 0) {
									deficitDeque.get(oldItemKey).remove(deficit);
									surplusDeque.put(oldItemKey, new SlotWrapperValue(pcSlot, transaction.getOldItem().getAmount(), -newDeficit));
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
					if (transaction.getInventoryId() == PESource.POCKET_CRAFTING_GRID_ADD && surplusDeque.containsKey(newItemKey)) {
						//Weird case for the crafting table. Mojang thought it a bright idea to split the adding and removing in there.
						for (SlotWrapperValue surplus : surplusDeque.get(newItemKey)) {
							if(surplus.slot() == pcSlot) {
								int newSurplus = surplus.amount() - transaction.getNewItem().getAmount();
								surplus.setAmount(newSurplus);
								if (newSurplus < 0) {
									surplusDeque.get(newItemKey).remove(surplus);
									deficitDeque.put(newItemKey, new SlotWrapperValue(pcSlot, 0, -newSurplus));
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
			cache.lockInventory();
			
			//Send misc data. (Creative inventory, anvils, etc)
			if(!misc.isEmpty()) {
				packets.addAll(misc);
				misc.clear();
			}
			
			//Match surpluses with defcitis.
			deficitDeque.cycleUp((item, deficits) -> {
				ChildDeque<SlotWrapperValue> surpluses = surplusDeque.get(item);
				if(surpluses != null) {
					Fin finish = new Fin();
					while(!finish.fin()) {
						deficits.cycleUp(deficit -> {
							surpluses.cycleDown(surplus -> {
								//Early copout, because Mojang sometimes really screws up.
								if (surplus.isEmpty()) { return true; }
								//We want to get all we can, but not more than we need.
								int toPay = deficit.amount() < surplus.amount() ? deficit.amount() : surplus.amount();
								if (!surplus.isCursor() && !deficit.isToUseInTable() && !(deficit.isCursor() && deficit.hasStack() && surplus.isCraftingResult(cache))) {
									//Unless the surplus is already in the cursor, (or this is the second crafting click where we only want one) we need to get it.
									packets.addAll(Click.LEFT.create(cache, surplus.slot(), item.get(surplus.slotAmount())));
								}
								if (deficit.isCursor() && !surplus.isCursor()) {
									if (deficit.hasStack()) {
										//We already had contents in the cursor, so just one left click won't cut it.
										packets.addAll(Click.LEFT.create(cache, surplus.slot(), item.get(surplus.slotAmount() + deficit.slotAmount())));
									}
									//Put back the items from the cursor that we don't want.
									for (int i = 0; i < (surplus.slotAmount() - toPay); i++) {
										packets.addAll(Click.RIGHT.create(cache, surplus.slot(), (i == 0) ? ItemStackWrapper.NULL : item.get(i)));
									}
								} else if (!deficit.isCursor() && !deficit.isToUseInTable()) {
									if (deficit.amount() == surplus.slotAmount()) {
										if (!((surplusDeque.getVeryLast() != null) && (deficit.slot() == surplusDeque.getVeryLast().slot()))) {
											//Unless we're swapping (then only one left click is sent on next deficit), we can payout the stack in full (left-click)
											packets.addAll(Click.LEFT.create(cache, deficit.slot(), (!deficit.hasStack()) ? ItemStackWrapper.NULL : item.get(deficit.slotAmount())));
										} else {
										}
									} else {
										//We need to pay what we need to pay.
										for (int i = 0; i < toPay; i++) {
											packets.addAll(Click.RIGHT.create(cache, deficit.slot(), ((deficit.slotAmount() + i) == 0) ? ItemStackWrapper.NULL : item.get(deficit.slotAmount() + i)));
										}
									}
								}
								//Payout the trackers.
								surplus.pay(toPay); 
								deficit.receive(toPay);
								//Last but not least, put back our access surplus and or containing stack.
								if(!surplus.isCursor() && !deficit.isToUseInTable() && !deficit.isCursor() && surplus.hasStack()) {
									packets.addAll(Click.LEFT.create(cache, surplus.slot(), ItemStackWrapper.NULL));
								}
								return surplus.isEmpty();
							});
							finish.setFin(true);
							if (surpluses.isEmpty()) {
								surplusDeque.remove(item);
							} else {
								if(surpluses.size() == 1 && deficits.size() == 1 && deficit.isEmpty()) {
									//If we have just one item left, add it as deficit to the cursor so we end up with that in our hands.
									//This ensures that some left-drag actions having a remainder that doesn't change (e.g. 19/2*9 or 3*6) 
									//still puts items in the cursor. (Because PE ONLY sends changes)
									deficits.addFirst(new SlotWrapperValue(-1, 0, surpluses.getLast().amount()));
									finish.setFin(false);
								}
							}
							//If there's still something in the cursor we want it to be accessed next round.
							if(deficit.isCursor() && deficit.hasStack()) {
								surplusDeque.put(item, deficit.setAmount(deficit.slotAmount()), true);
								return true;
							}
							return deficit.isEmpty();
						});
					}
				}
				return deficits.isEmpty();
			});
			bug("Sending " + packets.size() + " packets.... :S"); 
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
			return slot() == 0 && cache.getOpenedWindow() == WindowType.CRAFTING_TABLE;
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
		switch(cache.getOpenedWindow()) {
			case PLAYER: {
				switch(peInventoryId) {
					case PESource.POCKET_CLICKED_SLOT: {
						return -1;
					}
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
					case PESource.POCKET_INVENTORY: {
						if (peSlot < 9) {
							return peSlot + 36;
						} else {
							return peSlot;
						}
					}
					case PESource.POCKET_OFFHAND: {
						return 45;
					}
					case PESource.POCKET_FAUX_DROP: {
						return peInventoryId;
					}
					case PESource.POCKET_CRAFTING_GRID_USE_INGREDIENT:  {
						return -333;
					}
					default: {
						return -666;
					}
				}
			}
			case BREWING: {
				if(peInventoryId == cache.getOpenedWindowId()) {
					if(peSlot == 0) {
						return 3;
					} else if (peSlot >= 1 && peSlot <= 3) {
						return peSlot - 1;
					}
				}
				return invSlotToContainerSlot(peInventoryId, 5, peSlot);
			}
			case CRAFTING_TABLE: {
				switch(peInventoryId) {
					case PESource.POCKET_CLICKED_SLOT: {
						return -1;
					}
					case PESource.POCKET_CRAFTING_RESULT: {
						return 0;
					}
					case PESource.POCKET_CRAFTING_GRID_ADD:
					case PESource.POCKET_CRAFTING_GRID_REMOVE: {
						return peSlot + 1;
					}
					case PESource.POCKET_INVENTORY: {
						if (peSlot < 9) {
							return peSlot + 37;
						} else {
							return peSlot + 1;
						}
					}
					case PESource.POCKET_FAUX_DROP: {
						return peInventoryId;
					}
					case PESource.POCKET_CRAFTING_GRID_USE_INGREDIENT:  {
						return -333;
					}
					default: {
						return -666;
					}
				}
			}
			default: {
				int wSlots = cache.getOpenedWindowSlots();
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
			int actionNumber = cache.getActionNumber();
			packets.add(MiddleInventoryClick.create(cache.getLocale(), cache.getOpenedWindowId(), slot, button, actionNumber, mode, item));
			if(!item.isNull() && item.getTag() != null && !item.getTag().isNull()) {
				System.out.println("My apologies??!!?!?!");
				packets.add(MiddleInventoryTransaction.create(cache.getOpenedWindowId(), actionNumber, false));
			}
			return packets;
		}
		
	}

}
