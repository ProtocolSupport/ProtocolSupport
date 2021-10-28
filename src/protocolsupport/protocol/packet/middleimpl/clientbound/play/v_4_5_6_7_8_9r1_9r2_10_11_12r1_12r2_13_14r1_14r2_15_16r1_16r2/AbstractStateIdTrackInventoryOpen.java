package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.storage.netcache.InventoryTransactionCache;

public abstract class AbstractStateIdTrackInventoryOpen extends MiddleInventoryOpen {

	protected AbstractStateIdTrackInventoryOpen(MiddlePacketInit init) {
		super(init);
	}

	protected final InventoryTransactionCache transactionCache = cache.getTransactionCache();

	@Override
	protected void initWindow() {
		super.initWindow();
		transactionCache.openInventory();
	}

}
