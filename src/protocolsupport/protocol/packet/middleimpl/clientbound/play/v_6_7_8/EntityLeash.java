package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7_8;

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
		ClientBoundPacketData entityleash = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_ENTITY_LEASH);
		entityleash.writeInt(entityId);
		entityleash.writeInt(vehicleId);
		entityleash.writeBoolean(true);
		codec.write(entityleash);
	}

}
