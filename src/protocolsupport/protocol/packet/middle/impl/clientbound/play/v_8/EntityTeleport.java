package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__20.AbstractLocationOffsetEntityTeleport;

public class EntityTeleport extends AbstractLocationOffsetEntityTeleport implements IClientboundMiddlePacketV8 {

	public EntityTeleport(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeClientbound(create(entity.getId(), x, y, z, yaw, pitch, onGround));
	}

	public static ClientBoundPacketData create(int entityId, double x, double y, double z, byte yaw, byte pitch, boolean onGround) {
		ClientBoundPacketData entityteleport = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_TELEPORT);
		VarNumberCodec.writeVarInt(entityteleport, entityId);
		entityteleport.writeInt((int) (x * 32));
		entityteleport.writeInt((int) (y * 32));
		entityteleport.writeInt((int) (z * 32));
		entityteleport.writeByte(yaw);
		entityteleport.writeByte(pitch);
		entityteleport.writeBoolean(onGround);
		return entityteleport;
	}

}
