package protocolsupport.protocol.storage.netcache;

import protocolsupport.protocol.typeremapper.pe.inventory.PETransactionRemapper;
import protocolsupport.protocol.typeremapper.pe.inventory.fakes.FakeBeacon;
import protocolsupport.protocol.typeremapper.pe.inventory.fakes.FakeEnchanting;
import protocolsupport.protocol.typeremapper.pe.inventory.fakes.FakeVillager;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.Utils;
import protocolsupport.utils.ArrayDequeMultiMap.ChildDeque;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class PEInventoryCache {

	private ItemStackWrapper itemInHand = ItemStackWrapper.NULL;
	private final PETransactionRemapper transactionRemapper = new PETransactionRemapper();
	private final FakeBeacon fakeBeacon = new FakeBeacon();
	private final FakeEnchanting fakeEnchanting = new FakeEnchanting();
	private final FakeVillager fakeVillager = new FakeVillager();
	private ChildDeque<Position> fakeContainers = new ChildDeque<Position>();
	private long inventoryLockMillis = 0;
	private long inventoryUpdateMillis = 0;
	private int fuelTime = 0;
	private int smeltTime = 200;
	private int selectedSlot = 0;
	private int PEActionNumber = 0;

	public ItemStackWrapper getItemInHand() {
		return itemInHand; 
	}

	public void setItemInHand(ItemStackWrapper itemInHand) {
		this.itemInHand = itemInHand;
	}

	public PETransactionRemapper getTransactionRemapper() {
		return transactionRemapper;
	}

	public FakeBeacon getFakeBeacon() {
		return fakeBeacon;
	}

	public FakeEnchanting getFakeEnchanting() {
		return fakeEnchanting;
	}

	public FakeVillager getFakeVillager() {
		return fakeVillager;
	}

	public void setFakeContainers(ChildDeque<Position> fakeContainers) {
		this.fakeContainers = fakeContainers;
	}

	public ChildDeque<Position> getFakeContainers() {
		return fakeContainers;
	}

	public void lockInventory() {
		inventoryLockMillis = System.currentTimeMillis();
	}

	public boolean isInventoryLocked() {
		return System.currentTimeMillis() - inventoryLockMillis < 230;
	}

	public void lockInventoryUpdate() {
		inventoryUpdateMillis = System.currentTimeMillis();
	}

	public boolean shouldSendUpdate() {
		return System.currentTimeMillis() - inventoryUpdateMillis > 200;
	}

	public int getFuelTime() {
		return fuelTime;
	}

	public int getSmeltTime() {
		return smeltTime;
	}

	public void setFuelTime(int fuelTime) {
		this.fuelTime = fuelTime;
	}

	public void setSmeltTime(int smeltTime) {
		this.smeltTime = smeltTime;
	}

	public int getSelectedSlot() {
		return selectedSlot;
	}

	public void setSelectedSlot(int selectedSlot) {
		this.selectedSlot = selectedSlot;
	}

	public int getActionNumber() {
		return PEActionNumber++;
	}

	public void resetActionNumber() {
		PEActionNumber = 0;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
