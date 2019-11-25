package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityPassengers;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class EntityPassengers extends MiddleEntityPassengers {

	public EntityPassengers(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData entitypassengers = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_ENTITY_PASSENGERS);
		VarNumberSerializer.writeVarInt(entitypassengers, vehicleId);
		ArraySerializer.writeVarIntVarIntArray(entitypassengers, passengersIds);
		codec.write(entitypassengers);
	}

}
