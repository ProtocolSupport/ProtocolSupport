package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__7;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__20.AbstractLocationOffsetEntityTeleport;

public class EntityTeleport extends AbstractLocationOffsetEntityTeleport implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7
{

	public EntityTeleport(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeClientbound(create(entity.getId(), x, y, z, yaw, pitch));
	}

	public static ClientBoundPacketData create(int entityId, double x, double y, double z, byte yaw, byte pitch) {
		ClientBoundPacketData entityteleport = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_TELEPORT);
		entityteleport.writeInt(entityId);
		entityteleport.writeInt((int) (x * 32));
		entityteleport.writeInt((int) (y * 32));
		entityteleport.writeInt((int) (z * 32));
		entityteleport.writeByte(yaw);
		entityteleport.writeByte(pitch);
		return entityteleport;
	}

}
