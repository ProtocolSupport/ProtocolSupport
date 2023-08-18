package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__15;

import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleEntityAction;
import protocolsupport.protocol.storage.netcache.ClientCache;

public abstract class AbstractSneakingCacheEntityAction extends MiddleEntityAction {

	protected AbstractSneakingCacheEntityAction(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void handle() {
		switch (action) {
			case START_SNEAK: {
				clientCache.setSneaking(true);
				break;
			}
			case STOP_SNEAK: {
				clientCache.setSneaking(false);
				break;
			}
			default: {
				break;
			}
		}
	}

}
