package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEntityAction;
import protocolsupport.protocol.storage.netcache.ClientCache;

public abstract class AbstractSneakingCacheEntityAction extends MiddleEntityAction {

	public AbstractSneakingCacheEntityAction(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void handleReadData() {
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
