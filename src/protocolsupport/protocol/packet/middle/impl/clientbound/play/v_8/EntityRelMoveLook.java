package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8;

import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8.AbstractEntityTeleportEntityRelMoveLook;

public class EntityRelMoveLook extends AbstractEntityTeleportEntityRelMoveLook implements IClientboundMiddlePacketV8 {

	public EntityRelMoveLook(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeTeleport(double x, double y, double z, byte yaw, byte pitch, boolean onGround) {
		io.writeClientbound(EntityTeleport.create(entity.getId(), x, y, z, yaw, pitch, onGround));
	}

}
