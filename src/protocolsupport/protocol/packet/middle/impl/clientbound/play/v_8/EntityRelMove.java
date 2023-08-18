package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8;

import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractEntityTeleportEntityRelMove;

public class EntityRelMove extends AbstractEntityTeleportEntityRelMove implements IClientboundMiddlePacketV8 {

	public EntityRelMove(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeTeleport(double x, double y, double z, byte yaw, byte pitch, boolean onGround) {
		io.writeClientbound(EntityTeleport.create(entity.getId(), x, y, z, yaw, pitch, onGround));
	}

}
