package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractLocationOffsetEntityTeleport;

public class EntityTeleport extends AbstractLocationOffsetEntityTeleport {

	public EntityTeleport(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData entityteleport = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_TELEPORT);
		entityteleport.writeInt(entityId);
		entityteleport.writeInt((int) (x * 32));
		entityteleport.writeInt((int) (y * 32));
		entityteleport.writeInt((int) (z * 32));
		entityteleport.writeByte(yaw);
		entityteleport.writeByte(pitch);
		codec.write(entityteleport);
	}

}
