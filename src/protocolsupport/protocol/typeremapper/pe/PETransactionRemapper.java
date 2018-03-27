package protocolsupport.protocol.typeremapper.pe;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClick;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryTransaction;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.GodPacket.InvTransaction;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.ArrayDequeMultiMap;
import protocolsupport.utils.ArrayDequeMultiMap.ChildDeque;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

//See [Documentation](https://github.com/ProtocolSupport/ProtocolSupport/wiki/PSPE:-Inventory-Managing)
public class PETransactionRemapper {
	
	private ArrayDequeMultiMap<ItemKey, SlotBudget> surpluses = new ArrayDequeMultiMap<>();
	private ArrayDequeMultiMap<ItemKey, SlotBudget> deficits = new ArrayDequeMultiMap<>();
	
	//See [Documentation](https://github.com/ProtocolSupport/ProtocolSupport/wiki/PSPE:-Inventory-Managing#surplus--deficit)
	public void cacheTransaction(InvTransaction transaction) {
		if (transaction.isValid()) {
			ItemKey oldItem = new ItemKey(transaction.getOldItem());
			ItemKey newItem = new ItemKey(transaction.getNewItem());
			if (oldItem.equals(newItem) && !transaction.isCursor()) {
				int money = transaction.getOldItem().getAmount() - transaction.getNewItem().getAmount();
				if (money > 0) {
					bug("Surplus added: " + transaction.getOldItem().toString() + " on: " + transaction.getSlot() + " amount: " + money);
					surpluses.put(oldItem, new SlotBudget(transaction.getSlot(), transaction.getOldItem().getAmount(), money), transaction.isCursor());
				} else if (money < 0) {
					bug("Deficit added: " + transaction.getOldItem().toString() + " on: " + transaction.getSlot() + " amount: " + -money);
					deficits.put(oldItem, new SlotBudget(transaction.getSlot(), transaction.getOldItem().getAmount(), -money), transaction.isCursor());
				}
			} else {
				if (!transaction.getOldItem().isNull() && !transaction.isCursor()) {
					rectifyCraftRemove(transaction, oldItem);
					int money = transaction.getOldItem().getAmount();
					bug("Surplus WN added: " + transaction.getOldItem().toString() + " on: " + transaction.getSlot() + " amount: " + money);
					surpluses.put(oldItem, new SlotBudget(transaction.getSlot(), transaction.getOldItem().getAmount(), money), transaction.isCursor());
				}
				if (!transaction.getNewItem().isNull()) {
					rectifyCraftAdd(transaction, newItem);
					int money = transaction.getNewItem().getAmount();
					bug("Deficit WN added: " + transaction.getNewItem().toString() + " on: " + transaction.getSlot() + " amount: " + money);
					deficits.put(newItem, new SlotBudget(transaction.getSlot(), 0, money), transaction.isCursor());
				}
			}
		}
	}

	public void clear() {
		deficits.clear();
		surpluses.clear();
	}

	public void customCursor(ItemStackWrapper item) {
		clear();
		if(!item.isNull()) {
			surpluses.put(new ItemKey(item), new SlotBudget(InvTransaction.CURSOR, item.getAmount(), item.getAmount()), true);
		}
		
	}
	
	private static boolean godlyDebug = true;
	//TODO: Remove debug (can delete all lines starting with "bug(") if all is well.
	public static void bug(String bugger) {
		if(godlyDebug) { System.out.println(bugger); }
	}
	
	//See [Documentation](https://github.com/ProtocolSupport/ProtocolSupport/wiki/PSPE:-Inventory-Managing#the-real-stuff)
	public void processTransactions(NetworkDataCache cache, RecyclableArrayList<ServerBoundPacketData> packets) {
		deficits.cycleUp((item, itemDeficit) -> {
			ChildDeque<SlotBudget> itemSurplus = surpluses.get(item);
			if (itemSurplus != null) {
				itemDeficit.cycleUp(deficit -> {
					itemSurplus.cycleDown(surplus -> {
						int money = deficit.getAmount() < surplus.getAmount() ? deficit.getAmount() : surplus.getAmount();
						bug(item.getKeyItem().toString() + "-> [" + surplus.getSlot() + ", " + surplus.getSlotAmount() + ", " + surplus.getAmount() + "] - [" + deficit.getSlot() + ", " + deficit.getSlotAmount()+ ", " + deficit.getAmount() + "]");
						if (!surplus.isCursor() && !deficit.isToUseInTable() && !(deficit.isCursor() && deficit.hasStack() && surplus.isCraftingResult(cache))) {
							bug("Getting stack..");
							Click.LEFT.on(surplus.getSlot(), item.get(surplus.getSlotAmount()), cache, packets);
						}
						if (deficit.isCursor() && !surplus.isCursor()) {
							if (deficit.hasStack()) {
								bug("Deficit was in cursor and we already clicked so we need to pick it up again.");
								Click.LEFT.on(surplus.getSlot(), item.get(surplus.getSlotAmount() + deficit.getSlotAmount()), cache, packets);
							}
							bug("Paying back");
							for (int i = 0; i < (surplus.getSlotAmount() - money); i++) {
								Click.RIGHT.on(surplus.getSlot(), (i == 0) ? ItemStackWrapper.NULL : item.get(i), cache, packets);
							}
						} 
						if (!deficit.isCursor() && !deficit.isToUseInTable()) {
							if (money == surplus.getSlotAmount()) {
								if (!isSwapping(deficit)) { //not swapping.
									bug("Putting all on deficit!");
									Click.LEFT.on(deficit.getSlot(), (!deficit.hasStack()) ? ItemStackWrapper.NULL : item.get(deficit.getSlotAmount()), cache, packets);
								} else {
									bug("swapping!");
								}
							} else {
								bug("putting some on deficit!");
								for (int i = 0; i < money; i++) {
									Click.RIGHT.on(deficit.getSlot(), ((deficit.getSlotAmount() + i) == 0) ? ItemStackWrapper.NULL : item.get(deficit.getSlotAmount() + i), cache, packets);
								}
							}
						}
						surplus.pay(money);
						deficit.receive(money);
						if (!surplus.isCursor() && !deficit.isToUseInTable() && !deficit.isCursor() && surplus.hasStack()) {
							bug("Putting surplus back");
							Click.LEFT.on(surplus.getSlot(), ItemStackWrapper.NULL, cache, packets);
						}
						return surplus.isEmpty();
					});
					if (itemSurplus.isEmpty()) {
						surpluses.remove(item);
					} else if (itemSurplus.size() == 1 && itemDeficit.size() == 1 && deficit.isEmpty() && !deficit.isCursor()) {
						SlotBudget surplus = itemSurplus.getLast();
						if (!surplus.isEmpty() && !surplus.isCursor()) {
							bug("WRAPPING TO CURSOR!");
							int money = surplus.getSlotAmount() - surplus.getAmount();
							Click.LEFT.on(surplus.getSlot(), item.get(surplus.getSlotAmount()), cache, packets);
							for (int i = 0; i < money; i++) {
								Click.RIGHT.on(surplus.getSlot(), (i == 0) ? ItemStackWrapper.NULL : item.get(i), cache, packets);
							}
							itemSurplus.removeLast();
							surpluses.put(item, new SlotBudget(InvTransaction.CURSOR, surplus.getAmount(), surplus.getAmount()));
						}
					}
					if (deficit.isEmpty() && deficit.isCursor() && deficit.hasStack()) {
						bug("Something left in cursor!");
						surpluses.put(item, new SlotBudget(InvTransaction.CURSOR, deficit.getSlotAmount(), deficit.getSlotAmount()));
					}
					return deficit.isEmpty();
				});
			}
			return itemDeficit.isEmpty();
		});
	}
	
	public void processCreativeTransactions(NetworkDataCache cache, InvTransaction transaction, RecyclableArrayList<ServerBoundPacketData> packets) {
		if ((transaction.getSourceId() != SOURCE_CREATIVE) && !transaction.isCursor()) {
			//Creative transaction use -1 not for cursor but throwing items, cursoritems are actually deleted on serverside.
			packets.add(MiddleCreativeSetSlot.create(cache.getAttributesCache().getLocale(), (pcSlot == InvTransaction.DROP ? InvTransaction.CURSOR : pcSlot), transaction.getNewItem()));
			if (pcSlot == invCache.getSelectedSlot()) {
				invCache.setItemInHand(transaction.getNewItem());
			}
		}
	}
	
	public boolean isCreativeTransaction(NetworkDataCache cache) {
		return (cache.getAttributesCache().getPEGameMode() == GameMode.CREATIVE) &&
			(winCache.getOpenedWindow() == WindowType.PLAYER);
	}

	protected boolean isSwapping(SlotBudget deficit) {
		return (surpluses.getVeryLast() != null) && (deficit.getSlot() == surpluses.getVeryLast().getSlot());
	}

	//See [Documentation](https://github.com/ProtocolSupport/ProtocolSupport/wiki/PSPE:-Inventory-Managing#crafting)
	protected void rectifyCraftRemove(InvTransaction transaction, ItemKey oldItem) {
		if (transaction.isCraftingRemove() && deficits.containsKey(oldItem)) {
			deficits.get(oldItem).stream()
			.filter(deficit -> deficit.getSlot() == transaction.getSlot())
			.forEach(deficit -> {
				int money = transaction.getOldItem().getAmount() - deficit.getAmount();
				deficit.setAmount(-money);
				if (money >= 0) {
					deficits.remove(oldItem, deficit);
					if (money > 0 && !transaction.isCursor()) { //No cursor surplus!
						surpluses.put(oldItem, new SlotBudget(transaction.getSlot(), transaction.getOldItem().getAmount(), money), transaction.isCursor());
					}
				}
			});
		}
	}

	//See [Documentation](https://github.com/ProtocolSupport/ProtocolSupport/wiki/PSPE:-Inventory-Managing#crafting)
	protected void rectifyCraftAdd(InvTransaction transaction, ItemKey newItem) {
		if (transaction.isCraftingAdd() && surpluses.containsKey(newItem)) {
			surpluses.get(newItem).stream()
			.filter(surplus -> surplus.getSlot() == transaction.getSlot())
			.forEach(surplus -> {
				int money = surplus.getAmount() - transaction.getNewItem().getAmount();
				surplus.setAmount(money);
				if (money <= 0) {
					surpluses.get(newItem).remove(surplus);
					if (money < 0) { 
						deficits.put(newItem, new SlotBudget(transaction.getSlot(), 0, -money), transaction.isCursor());
					}
				}
				return;
			});
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
		
		public void on(int slot, ItemStackWrapper item, NetworkDataCache cache, RecyclableArrayList<ServerBoundPacketData> packets) {
			bug("CLICK " + toString() + " on: " + slot + " with: " + item.toString());
			int actionNumber = cache.getPEInventoryCache().getActionNumber();
			packets.add(MiddleInventoryClick.create(cache.getAttributesCache().getLocale(), cache.getWindowCache().getOpenedWindowId(), slot, button, actionNumber, mode, item));
			if(!item.isNull() && item.getTag() != null && !item.getTag().isNull()) {
				packets.add(MiddleInventoryTransaction.create(cache.getWindowCache().getOpenedWindowId(), actionNumber, false));
			}
		}
		
	}

	protected static class SlotBudget {

		private int slot;
		private int slotAmount;
		private int amount;

		public SlotBudget(int slot, int slotAmount, int amount) {
			this.slot = slot;
			this.slotAmount = slotAmount;
			this.amount = amount;
		}

		public int getSlot() {
			return slot;
		}

		public int getSlotAmount() {
			return slotAmount;
		}

		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}

		public void pay(int amount) {
			this.slotAmount -= amount;
			this.amount -= amount;
		}

		public void receive(int amount) {
			this.slotAmount += amount;
			this.amount -= amount;
		}

		public boolean hasStack() {
			return slotAmount != 0;
		}

		public boolean isCursor() {
			return slot == InvTransaction.CURSOR;
		}

		public boolean isToUseInTable() {
			return slot == InvTransaction.TABLE;
		}

		public boolean isCraftingResult(NetworkDataCache cache) {
			return slot == 0 && cache.getWindowCache().getOpenedWindow() == WindowType.CRAFTING_TABLE;
		}

		public boolean isEmpty() {
			return amount <= 0;
		}

	}

	protected static class ItemKey {

		private ItemStackWrapper keyItem = ItemStackWrapper.NULL;
		private int hash = 0;

		public ItemKey(ItemStackWrapper keyItem) {
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
			if(object instanceof ItemKey) {
				ItemKey wrapper = (ItemKey) object;
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

}
