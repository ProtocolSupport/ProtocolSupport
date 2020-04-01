package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

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
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_NOOP);
		serializer.writeInt(entityId);
		codec.write(serializer);
	}

}
