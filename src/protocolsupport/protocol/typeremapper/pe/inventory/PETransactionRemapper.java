package protocolsupport.protocol.typeremapper.pe.inventory;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCreativeSetSlot;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClick;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryTransaction;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.GodPacket;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.GodPacket.InvTransaction;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.types.GameMode;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.utils.ArrayDequeMultiMap;
import protocolsupport.utils.ArrayDequeMultiMap.ChildDeque;
import protocolsupport.utils.recyclable.RecyclableArrayList;

//See [Documentation](https://github.com/ProtocolSupport/ProtocolSupport/wiki/PSPE:-Inventory-Managing)
public class PETransactionRemapper {

	private ArrayDequeMultiMap<NetworkItemStack, SlotBudget> surpluses = new ArrayDequeMultiMap<>();
	private ArrayDequeMultiMap<NetworkItemStack, SlotBudget> deficits = new ArrayDequeMultiMap<>();

	//See [Documentation](https://github.com/ProtocolSupport/ProtocolSupport/wiki/PSPE:-Inventory-Managing#surplus--deficit)
	public void cacheTransaction(InvTransaction transaction) {
		if (transaction.isValid()) {
			NetworkItemStack oldItem = getAmount(transaction.getOldItem(), 1);
			NetworkItemStack newItem = getAmount(transaction.getNewItem(), 1);
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
					int money = transaction.getOldItem().getAmount();
					bug("Surplus WN added: " + transaction.getOldItem().toString() + " on: " + transaction.getSlot() + " amount: " + money);
					surpluses.put(oldItem, new SlotBudget(transaction.getSlot(), transaction.getOldItem().getAmount(), money), transaction.isCursor());
				}
				if (!transaction.getNewItem().isNull()) {
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

	public void customCursor(NetworkItemStack item) {
		clear();
		if (!item.isNull()) {
			bug("customCursor surpluses.put " + item);
			surpluses.put(getAmount(item, 1), new SlotBudget(InvTransaction.CURSOR, item.getAmount(), item.getAmount()), true);
		}

	}

	private static boolean godlyDebug = true;

	//TODO: Remove debug (can delete all lines starting with "bug(") if all is well.
	public static void bug(String bugger) {
		if (godlyDebug) {
			System.out.println(bugger);
		}
	}

	protected static NetworkItemStack getAmount(NetworkItemStack item, int amount) {
		if (item.isNull()) {
			return item;
		}
		NetworkItemStack out = item.cloneItemStack();
		out.setAmount(amount);
		return out;
	}

	//See [Documentation](https://github.com/ProtocolSupport/ProtocolSupport/wiki/PSPE:-Inventory-Managing#the-real-stuff)
	public void processTransactions(NetworkDataCache cache, RecyclableArrayList<ServerBoundPacketData> packets) {
		//bug("processTransactions deficits " + deficits);
		//bug("processTransactions surpluses " + surpluses);
		deficits.cycleUp((item, itemDeficit) -> {
			//bug("processTransactions, deficit item: " + item);
			ChildDeque<SlotBudget> itemSurplus = surpluses.get(item);
			//bug("processTransactions, itemSurplus: " + itemSurplus);
			if (itemSurplus != null) {
				itemDeficit.cycleUp(deficit -> {
					itemSurplus.cycleDown(surplus -> {
						int money = deficit.getAmount() < surplus.getAmount() ? deficit.getAmount() : surplus.getAmount();
						bug(item.toString() + "-> [" + surplus.getSlot() + ", " + surplus.getSlotAmount() + ", " + surplus.getAmount() + "] - [" + deficit.getSlot() + ", " + deficit.getSlotAmount() + ", " + deficit.getAmount() + "]");
						if (surplus.getSlot() != deficit.getSlot() && !deficit.isToUseInTable()) {
							if (!surplus.isCursor() && !(deficit.isCursor() && deficit.hasStack() && surplus.isCraftingResult(cache))) {
								bug("Getting stack..");
								Click.LEFT.on(surplus.getSlot(), getAmount(item, surplus.getSlotAmount()), cache, packets);
							}
							if (deficit.isCursor() && !surplus.isCursor()) {
								if (deficit.hasStack()) {
									bug("Deficit was in cursor and we already clicked so we need to pick it up again.");
									Click.LEFT.on(surplus.getSlot(), getAmount(item, surplus.getSlotAmount() + deficit.getSlotAmount()), cache, packets);
								}
								bug("Paying back");
								for (int i = 0; i < (surplus.getSlotAmount() - money); i++) {
									Click.RIGHT.on(surplus.getSlot(), (i == 0) ? NetworkItemStack.NULL : getAmount(item, i), cache, packets);
								}
							}
							if (!deficit.isCursor()) {
								if (money == surplus.getSlotAmount()) {
									if (!isSwapping(deficit)) { //not swapping.
										bug("Putting all on deficit!");
										Click.LEFT.on(deficit.getSlot(), (!deficit.hasStack()) ? NetworkItemStack.NULL : getAmount(item, deficit.getSlotAmount()), cache, packets);
									} else {
										bug("swapping!");
									}
								} else {
									bug("putting some on deficit!");
									for (int i = 0; i < money; i++) {
										Click.RIGHT.on(deficit.getSlot(), ((deficit.getSlotAmount() + i) == 0) ? NetworkItemStack.NULL : getAmount(item, deficit.getSlotAmount() + i), cache, packets);
									}
								}
							}
						}
						surplus.pay(money);
						deficit.receive(money);
						if (!surplus.isCursor() && !deficit.isToUseInTable() && !deficit.isCursor() && surplus.hasStack() && (surplus.getSlot() != deficit.getSlot())) {
							bug("Putting surplus back");
							Click.LEFT.on(surplus.getSlot(), NetworkItemStack.NULL, cache, packets);
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
							Click.LEFT.on(surplus.getSlot(), getAmount(item, surplus.getSlotAmount()), cache, packets);
							for (int i = 0; i < money; i++) {
								Click.RIGHT.on(surplus.getSlot(), (i == 0) ? NetworkItemStack.NULL : getAmount(item, i), cache, packets);
							}
							itemSurplus.removeLast();
							surpluses.put(item, new SlotBudget(InvTransaction.CURSOR, surplus.getAmount(), surplus.getAmount()));
						}
					}
					if (deficit.isEmpty() && deficit.isCursor() && deficit.hasStack()) {
						bug("Something left in cursor! " + item);
						surpluses.put(item, new SlotBudget(InvTransaction.CURSOR, deficit.getSlotAmount(), deficit.getSlotAmount()));
					}
					return deficit.isEmpty();
				});
			}
			return itemDeficit.isEmpty();
		});
	}

	public void processCreativeTransaction(NetworkDataCache cache, InvTransaction transaction, RecyclableArrayList<ServerBoundPacketData> packets) {
		if ((transaction.getSourceId() != GodPacket.SOURCE_CREATIVE) && !transaction.isCursor()) {
			//Creative transaction use -1 not for cursor but throwing items, cursoritems are actually deleted serverside.
			int slot = transaction.getSlot() == InvTransaction.DROP ? InvTransaction.CURSOR : transaction.getSlot();
			packets.add(MiddleCreativeSetSlot.create(slot, transaction.getNewItem()));
			if (slot == cache.getPEInventoryCache().getSelectedSlot()) {
				cache.getPEInventoryCache().setItemInHand(transaction.getNewItem());
			}
		}
	}

	public boolean isCreativeTransaction(NetworkDataCache cache) {
		return (cache.getAttributesCache().getPEGameMode() == GameMode.CREATIVE) &&
			(cache.getWindowCache().getOpenedWindow() == WindowType.PLAYER);
	}

	protected boolean isSwapping(SlotBudget deficit) {
		return (surpluses.getVeryLast() != null) && (deficit.getSlot() == surpluses.getVeryLast().getSlot());
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

		public void on(int slot, NetworkItemStack item, NetworkDataCache cache, RecyclableArrayList<ServerBoundPacketData> packets) {
			bug("CLICK " + toString() + " on: " + slot + " with: " + item.toString());
			int actionNumber = cache.getPEInventoryCache().getActionNumber();
			packets.add(MiddleInventoryClick.create(cache.getWindowCache().getOpenedWindowId(), slot, button, actionNumber, mode, item));
			if (!item.isNull() && item.getNBT() != null) {
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
			return (slot == 0) && (
					(cache.getWindowCache().getOpenedWindow() == WindowType.CRAFTING) ||
					(cache.getWindowCache().getOpenedWindow() == WindowType.PLAYER)
			);
		}

		public boolean isEmpty() {
			return amount <= 0;
		}

	}
}
