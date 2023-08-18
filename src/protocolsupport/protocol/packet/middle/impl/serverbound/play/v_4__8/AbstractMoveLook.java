package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__8;

import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleMoveLook;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleTeleportAccept;
import protocolsupport.protocol.storage.netcache.MovementCache;

public abstract class AbstractMoveLook extends ServerBoundMiddlePacket {

	protected AbstractMoveLook(IMiddlePacketInit init) {
		super(init);
	}

	protected final MovementCache movementCache = cache.getMovementCache();

	protected double x;
	protected double y;
	protected double z;
	protected float yaw;
	protected float pitch;
	protected boolean onGround;

	@Override
	protected void write() {
		int teleportId = movementCache.tryTeleportConfirm(x, y, z);
		if (teleportId == -1) {
			io.writeServerbound(MiddleMoveLook.create(x, y, z, yaw, pitch, onGround));
		} else {
			io.writeServerbound(MiddleTeleportAccept.create(teleportId));
			io.writeServerbound(MiddleMoveLook.create(x, y, z, yaw, pitch, onGround));
		}
	}

}
