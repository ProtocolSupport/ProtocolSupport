package protocolsupport.protocol.storage.pe;

import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.GodPacket.InfTransactions;

public class PEInventoryCache {

	private long inventoryLockMillis = 0;
	private final InfTransactions infTransactions = new InfTransactions();
	private int fuelTime = 0;
	private int smeltTime = 200;

	public void lockInventory() {
		inventoryLockMillis = System.currentTimeMillis();
	}

	public boolean isInventoryLocked() {
		return System.currentTimeMillis() - inventoryLockMillis < 230;
	}

	public InfTransactions getInfTransactions() {
		return infTransactions;
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

}
