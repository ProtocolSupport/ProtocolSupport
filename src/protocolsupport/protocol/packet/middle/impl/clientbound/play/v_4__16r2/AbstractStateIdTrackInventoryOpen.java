package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__16r2;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.storage.netcache.InventoryTransactionCache;

public abstract class AbstractStateIdTrackInventoryOpen extends MiddleInventoryOpen {

	protected AbstractStateIdTrackInventoryOpen(IMiddlePacketInit init) {
		super(init);
	}

	protected final InventoryTransactionCache transactionCache = cache.getTransactionCache();

	@Override
	protected void initWindow() {
		super.initWindow();
		transactionCache.openInventory();
	}

}
