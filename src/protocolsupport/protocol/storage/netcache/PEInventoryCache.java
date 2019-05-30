package protocolsupport.protocol.storage.netcache;

import protocolsupport.protocol.typeremapper.pe.inventory.PETransactionRemapper;
import protocolsupport.protocol.typeremapper.pe.inventory.fakes.PEFakeBeacon;
import protocolsupport.protocol.typeremapper.pe.inventory.fakes.PEFakeEnchanting;
import protocolsupport.protocol.typeremapper.pe.inventory.fakes.PEFakeVillager;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.Position;
import protocolsupport.utils.Utils;
import protocolsupport.utils.ArrayDequeMultiMap.ChildDeque;

public class PEInventoryCache {

	private NetworkItemStack itemInHand = NetworkItemStack.NULL;
	private final PETransactionRemapper transactionRemapper = new PETransactionRemapper();
	private final PEFakeBeacon fakeBeacon = new PEFakeBeacon();
	private final PEFakeEnchanting fakeEnchanting = new PEFakeEnchanting();
	private final PEFakeVillager fakeVillager = new PEFakeVillager();
	private ChildDeque<Position> fakeContainers = new ChildDeque<Position>();
	private long inventoryLockMillis = 0;
	private long inventoryUpdateMillis = 0;
	private int fuelTime = 0;
	private int smeltTime = 200;
	private int selectedSlot = 0;
	private int PEActionNumber = 0;
	private int previousWindowId = 0;

	public NetworkItemStack getItemInHand() {
		return itemInHand;
	}

	public void setItemInHand(NetworkItemStack itemInHand) {
		this.itemInHand = itemInHand;
	}

	public PETransactionRemapper getTransactionRemapper() {
		return transactionRemapper;
	}

	public PEFakeBeacon getFakeBeacon() {
		return fakeBeacon;
	}

	public PEFakeEnchanting getFakeEnchanting() {
		return fakeEnchanting;
	}

	public PEFakeVillager getFakeVillager() {
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

	public int getPreviousWindowId() {
		return previousWindowId;
	}

	public void setPreviousWindowId(int previousWindowId) {
		this.previousWindowId = previousWindowId;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
