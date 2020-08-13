package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7_8;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2.AbstractKnownEntityPassengers;

public class EntityPassengers extends AbstractKnownEntityPassengers {

	public EntityPassengers(ConnectionImpl connection) {
		super(connection);
	}

	protected final Int2IntOpenHashMap vehiclePassenger = new Int2IntOpenHashMap();
	{
		vehiclePassenger.defaultReturnValue(-1);
	}

	@Override
	protected void writeToClient() {
		if (passengersIds.length == 0) {
			int passengerId = vehiclePassenger.remove(vehicleId);
			if (passengerId != vehiclePassenger.defaultReturnValue()) {
				codec.write(create(passengerId, -1));
			}
		} else {
			int newPassengerId = passengersIds[0];
			int oldPassengerId = vehiclePassenger.put(vehicleId, newPassengerId);
			if (oldPassengerId == vehiclePassenger.defaultReturnValue()) {
				codec.write(create(newPassengerId, vehicleId));
			} else if (newPassengerId != oldPassengerId) {
				codec.write(create(oldPassengerId, -1));
				codec.write(create(newPassengerId, vehicleId));
			}
		}
	}

	protected static ClientBoundPacketData create(int passengerId, int vehicleId) {
		ClientBoundPacketData entitypassengers = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_LEASH);
		entitypassengers.writeInt(passengerId);
		entitypassengers.writeInt(vehicleId);
		entitypassengers.writeBoolean(false);
		return entitypassengers;
	}

}
