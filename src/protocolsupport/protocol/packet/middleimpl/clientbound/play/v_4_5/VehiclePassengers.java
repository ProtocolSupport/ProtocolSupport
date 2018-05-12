package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleVehiclePassengers;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntity;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

import java.util.HashSet;
import java.util.Set;

public class VehiclePassengers extends MiddleVehiclePassengers {

	protected final Int2IntOpenHashMap vehiclePassenger = new Int2IntOpenHashMap();
	protected final Set<Integer> passengers = new HashSet<>();
	protected int passengerId;

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_LEASH_ID);
		serializer.writeInt(passengerId);
		serializer.writeInt(passengersIds.length == 0 ? -1 : vehicleId);
		return RecyclableSingletonList.create(serializer);
	}

	@Override
	public boolean postFromServerRead() {
		if (passengersIds.length == 0) {
			passengerId = vehiclePassenger.remove(vehicleId);
			passengers.remove(passengerId);
		} else {
			passengerId = passengersIds[0];
			vehiclePassenger.put(vehicleId, passengerId);
		}
		NetworkEntity vehicle = cache.getWatchedEntityCache().getWatchedEntity(vehicleId);
		NetworkEntity passenger = cache.getWatchedEntityCache().getWatchedEntity(passengerId);
		if (vehicle != null && passenger != null && passengersIds.length != 0) {
			if (passengers.contains(passengerId)) {
				return false;
			} else {
				passengers.add(passengerId);
			}
		}
		return true;
	}

}
