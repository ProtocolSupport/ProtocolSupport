package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__20;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleHeldSlot;
import protocolsupport.protocol.storage.netcache.ClientCache;

public abstract class AbstractCachedHeldSlot extends MiddleHeldSlot {

	protected AbstractCachedHeldSlot(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void handle() {
		clientCache.setHeldSlot(slot);
	}

}
