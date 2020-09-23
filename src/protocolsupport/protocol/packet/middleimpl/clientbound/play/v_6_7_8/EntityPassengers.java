package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7_8;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractPassengerStackEntityPassengers;

public class EntityPassengers extends AbstractPassengerStackEntityPassengers {

	public EntityPassengers(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected ClientBoundPacketData createEntityVehicle(int passengerId, int vehicleId) {
		return create(passengerId, vehicleId);
	}

	public static ClientBoundPacketData create(int passengerId, int vehicleId) {
		ClientBoundPacketData entitypassengers = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_LEASH);
		entitypassengers.writeInt(passengerId);
		entitypassengers.writeInt(vehicleId);
		entitypassengers.writeBoolean(false);
		return entitypassengers;
	}

}
