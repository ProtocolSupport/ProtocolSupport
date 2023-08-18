package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__16r2;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleInventoryHorseOpen;
import protocolsupport.protocol.storage.netcache.InventoryTransactionCache;

public abstract class AbstractStateIdTrackInventoryHorseOpen extends MiddleInventoryHorseOpen {

	protected AbstractStateIdTrackInventoryHorseOpen(IMiddlePacketInit init) {
		super(init);
	}

	protected final InventoryTransactionCache transactionCache = cache.getTransactionCache();

	@Override
	protected void handle() {
		super.handle();
		transactionCache.openInventory();
	}

}
