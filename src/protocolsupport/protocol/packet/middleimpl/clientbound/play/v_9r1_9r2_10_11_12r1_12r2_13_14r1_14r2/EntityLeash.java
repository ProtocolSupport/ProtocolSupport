package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityLeash;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EntityLeash extends MiddleEntityLeash {

	public EntityLeash(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData entityleash = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_LEASH);
		entityleash.writeInt(entityId);
		entityleash.writeInt(vehicleId);
		codec.write(entityleash);
	}

}
