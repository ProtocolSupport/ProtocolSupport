package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractEntityTeleportEntityRelMoveLook;

public class EntityRelMoveLook extends AbstractEntityTeleportEntityRelMoveLook {

	public EntityRelMoveLook(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeTeleport(double x, double y, double z, byte yaw, byte pitch, boolean onGround) {
		codec.writeClientbound(EntityTeleport.create(entity.getId(), x, y, z, yaw, pitch));
	}

}
