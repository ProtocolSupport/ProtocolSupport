package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityStatus;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class AbstractKnownEntityStatus extends MiddleEntityStatus {

	public AbstractKnownEntityStatus(MiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	protected NetworkEntity entity;

	@Override
	protected void handle() {
		entity = entityCache.getEntity(entityId);

		if (entity == null) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

}
