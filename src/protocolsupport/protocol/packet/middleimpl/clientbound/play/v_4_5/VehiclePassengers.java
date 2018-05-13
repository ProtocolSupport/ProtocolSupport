package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleVehiclePassengers;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.storage.netcache.WatchedEntityCache;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class VehiclePassengers extends MiddleVehiclePassengers {

	protected final Int2IntOpenHashMap vehiclePassenger = new Int2IntOpenHashMap();
	protected final Int2IntOpenHashMap passengerToVehicle = new Int2IntOpenHashMap();
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
			passengerToVehicle.remove(passengerId);
		} else {
			passengerId = passengersIds[0];
			vehiclePassenger.put(vehicleId, passengerId);
		}
		WatchedEntityCache entityCache = cache.getWatchedEntityCache();
		if (passengersIds.length != 0 && entityCache.hasWatchedEntity(vehicleId) && entityCache.hasWatchedEntity(passengerId)) {
			int prevVehicleId = passengerToVehicle.getOrDefault(passengerId, -1);
			if (entityCache.hasWatchedEntity(prevVehicleId)) {
				return false;
			} else {
				passengerToVehicle.put(passengerId, vehicleId);
			}
		}
		return true;
	}

}
