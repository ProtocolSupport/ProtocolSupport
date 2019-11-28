package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityTeleport;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class EntityTeleport extends MiddleEntityTeleport {

	public EntityTeleport(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData entityteleport = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_TELEPORT);
		VarNumberSerializer.writeVarInt(entityteleport, entityId);
		entityteleport.writeInt((int) (x * 32));
		entityteleport.writeInt((int) (y * 32));
		entityteleport.writeInt((int) (z * 32));
		entityteleport.writeByte(yaw);
		entityteleport.writeByte(pitch);
		entityteleport.writeBoolean(onGround);
		codec.write(entityteleport);
	}

}
