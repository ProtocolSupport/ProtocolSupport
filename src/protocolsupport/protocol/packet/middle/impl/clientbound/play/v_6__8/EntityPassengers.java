package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_6__8;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractPassengerStackEntityPassengers;

public class EntityPassengers extends AbstractPassengerStackEntityPassengers implements
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7,
IClientboundMiddlePacketV8 {

	public EntityPassengers(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected ClientBoundPacketData createEntityVehicle(int passengerId, int vehicleId) {
		return create(passengerId, vehicleId);
	}

	public static ClientBoundPacketData create(int passengerId, int vehicleId) {
		ClientBoundPacketData entitypassengers = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_LEASH);
		entitypassengers.writeInt(passengerId);
		entitypassengers.writeInt(vehicleId);
		entitypassengers.writeBoolean(false);
		return entitypassengers;
	}

}
