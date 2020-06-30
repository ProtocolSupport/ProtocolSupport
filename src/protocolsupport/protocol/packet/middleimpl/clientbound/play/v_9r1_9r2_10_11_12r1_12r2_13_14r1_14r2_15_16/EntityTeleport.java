package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16;

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
	protected void writeToClient() {
		ClientBoundPacketData entityteleport = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_TELEPORT);
		VarNumberSerializer.writeVarInt(entityteleport, entityId);
		entityteleport.writeDouble(x);
		entityteleport.writeDouble(y);
		entityteleport.writeDouble(z);
		entityteleport.writeByte(yaw);
		entityteleport.writeByte(pitch);
		entityteleport.writeBoolean(onGround);
		codec.write(entityteleport);
	}

}
