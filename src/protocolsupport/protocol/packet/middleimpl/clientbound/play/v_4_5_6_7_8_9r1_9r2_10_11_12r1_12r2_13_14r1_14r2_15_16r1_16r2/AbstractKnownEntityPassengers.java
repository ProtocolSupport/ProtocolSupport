package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityPassengers;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class AbstractKnownEntityPassengers extends MiddleEntityPassengers {

	protected AbstractKnownEntityPassengers(MiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	protected NetworkEntity vehicle;

	@Override
	protected void handle() {
		vehicle = entityCache.getEntity(vehicleId);

		if (vehicle == null) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

}
