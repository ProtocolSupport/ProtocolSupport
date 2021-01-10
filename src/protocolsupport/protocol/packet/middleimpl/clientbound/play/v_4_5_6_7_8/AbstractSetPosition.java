package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetPosition;
import protocolsupport.protocol.storage.netcache.MovementCache;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;

public abstract class AbstractSetPosition extends MiddleSetPosition {

	protected final MovementCache movementCache = cache.getMovementCache();

	public AbstractSetPosition(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void handle() {
		super.handle();

		NetworkEntityDataCache ecache = self.getDataCache();
		x = ecache.getX();
		y = ecache.getY();
		z = ecache.getZ();
		pitch = ecache.getPitch();
		yaw = ecache.getYaw();

		if (teleportConfirmId != 0) {
			movementCache.setTeleportLocation(x, y, z, teleportConfirmId);
		}
	}

}
