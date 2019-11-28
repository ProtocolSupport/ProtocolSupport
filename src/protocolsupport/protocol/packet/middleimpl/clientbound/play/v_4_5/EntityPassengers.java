package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityPassengers;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EntityPassengers extends MiddleEntityPassengers {

	public EntityPassengers(ConnectionImpl connection) {
		super(connection);
	}

	protected final Int2IntOpenHashMap vehiclePassenger = new Int2IntOpenHashMap();
	{
		vehiclePassenger.defaultReturnValue(-1);
	}

	@Override
	public void writeToClient() {
		if (cache.getWatchedEntityCache().getWatchedEntity(vehicleId) == null) {
			return;
		}

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
		return entitypassengers;
	}

}
