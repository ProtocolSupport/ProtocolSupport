package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__15;

import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.storage.netcache.ClientCache;

public abstract class AbstractSneakingCacheUseEntity extends MiddleUseEntity {

	protected AbstractSneakingCacheUseEntity(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void handle() {
		sneaking = clientCache.isSneaking();
	}

}
