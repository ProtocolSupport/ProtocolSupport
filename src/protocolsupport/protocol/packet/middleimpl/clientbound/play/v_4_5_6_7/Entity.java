package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntity;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class Entity extends MiddleEntity {

	public Entity(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData entitynoop = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_ENTITY_NOOP);
		entitynoop.writeInt(entityId);
		codec.write(entitynoop);
	}

}
