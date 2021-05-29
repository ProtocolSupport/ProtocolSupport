package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8;

import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleMoveLook;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTeleportAccept;
import protocolsupport.protocol.storage.netcache.MovementCache;

public abstract class AbstractMoveLook extends ServerBoundMiddlePacket {

	protected AbstractMoveLook(MiddlePacketInit init) {
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
			codec.writeServerbound(MiddleMoveLook.create(x, y, z, yaw, pitch, onGround));
		} else {
			codec.writeServerbound(MiddleTeleportAccept.create(teleportId));
			codec.writeServerbound(MiddleMoveLook.create(x, y, z, yaw, pitch, onGround));
		}
	}

}
