package protocolsupport.protocol.storage.pe;

import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.GodPacket.InfTransactions;
import protocolsupport.protocol.typeremapper.pe.PEInventory.BeaconTemple;
import protocolsupport.protocol.typeremapper.pe.PEInventory.EnchantHopper;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class PEInventoryCache {

	private ItemStackWrapper itemInHand = ItemStackWrapper.NULL;
	private final InfTransactions infTransactions = new InfTransactions();
	private final EnchantHopper enchantHopper = new EnchantHopper();
	private final BeaconTemple beaconTemple = new BeaconTemple();
	private Position[] fakeInvs = new Position[2];
	private long inventoryLockMillis = 0;
	private int fuelTime = 0;
	private int smeltTime = 200;
	private int selectedSlot = 0;

	public ItemStackWrapper getItemInHand() {
		return itemInHand; 
	}

	public void setItemInHand(ItemStackWrapper itemInHand) {
		this.itemInHand = itemInHand;
	}

	public InfTransactions getInfTransactions() {
		return infTransactions;
	}

	public EnchantHopper getEnchantHopper() {
		return enchantHopper;
	}

	public BeaconTemple getBeaconTemple() {
		return beaconTemple;
	}

	public Position[] getFakeInvs() {
		return fakeInvs;
	}

	public void lockInventory() {
		inventoryLockMillis = System.currentTimeMillis();
	}

	public boolean isInventoryLocked() {
		return System.currentTimeMillis() - inventoryLockMillis < 230;
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

}
