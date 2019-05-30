package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import org.bukkit.util.Vector;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.unsafe.pemetadata.PEMetaProviderSPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleVehiclePassengers;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.WatchedEntityCache;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEDataValues.PEEntityData.RiderInfo;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class VehiclePassengers extends MiddleVehiclePassengers {

	public VehiclePassengers(ConnectionImpl connection) {
		super(connection);
	}

	protected static final int UNLINK = 0;
	protected static final int LINK = 1;

	protected final Int2ObjectOpenHashMap<IntOpenHashSet> passengers = new Int2ObjectOpenHashMap<>();

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		WatchedEntityCache wecache = cache.getWatchedEntityCache();
		ProtocolVersion version = connection.getVersion();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		NetworkEntity vehicle = wecache.getWatchedEntity(vehicleId);
		if (vehicle != null) {
			IntOpenHashSet prevPassengersIds = passengers.get(vehicleId);
			if (prevPassengersIds == null) {
				prevPassengersIds = new IntOpenHashSet();
			}
			IntOpenHashSet newPassengersIds = new IntOpenHashSet(passengersIds);
			for (int passengerId : passengersIds) {
				NetworkEntity passenger = wecache.getWatchedEntity(passengerId);
				if (passenger != null) {
					NetworkEntityDataCache data = passenger.getDataCache();
					if (data.isRiding() && (data.getVehicleId() != vehicleId)) {
						//In case we are jumping from vehicle to vehicle.
						packets.add(create(version, data.getVehicleId(), passengerId, UNLINK));
					}
					RiderInfo rideInfo = PEDataValues.getEntityData(vehicle.getType()).getRiderInfo();
					if (rideInfo != null) {
						if (passenger.getType() == NetworkEntityType.PLAYER) {
							float vehicleSize = PEMetaProviderSPI.getProvider().getSizeScale(vehicle.getUUID(), vehicle.getId(), vehicle.getType().getBukkitType()) * vehicle.getDataCache().getSizeModifier();
							data.setRiderPosition(rideInfo.getPosition().multiply(new Vector(vehicleSize, vehicleSize, vehicleSize)));
						} else {
							data.setRiderPosition(new Vector(0, -0.125, 0));
						}
						data.setRotationLock(rideInfo.getRotationLock());
						data.setVehicleId(vehicleId);
					}
					packets.add(EntityMetadata.createFaux(version, cache.getAttributesCache().getLocale(), passenger));
					packets.add(create(version, vehicleId, passengerId, LINK));
				}
			}
			IntIterator prevPassengersIdsIter = prevPassengersIds.iterator();
			while (prevPassengersIdsIter.hasNext()) {
				int passengerId = prevPassengersIdsIter.nextInt();
				if (!newPassengersIds.contains(passengerId)) {
					NetworkEntity passenger = wecache.getWatchedEntity(passengerId);
					if (passenger != null) {
						passenger.getDataCache().setVehicleId(0);
						packets.add(EntityMetadata.createFaux(version, cache.getAttributesCache().getLocale(), passenger));
						packets.add(create(version, vehicleId, passengerId, UNLINK));
					}
				}
			}
			passengers.put(vehicleId, newPassengersIds);
		}
		return packets;
	}

	public static ClientBoundPacketData create(ProtocolVersion version, int vehicleId, int passengerId, int action) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_LINK);
		VarNumberSerializer.writeSVarLong(serializer, vehicleId);
		VarNumberSerializer.writeSVarLong(serializer, passengerId);
		serializer.writeByte(action);
		return serializer;
	}

}
