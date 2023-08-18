package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__7;

import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractEntityTeleportEntityRelMoveLook;

public class EntityRelMoveLook extends AbstractEntityTeleportEntityRelMoveLook implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7
{

	public EntityRelMoveLook(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeTeleport(double x, double y, double z, byte yaw, byte pitch, boolean onGround) {
		io.writeClientbound(EntityTeleport.create(entity.getId(), x, y, z, yaw, pitch));
	}

}
