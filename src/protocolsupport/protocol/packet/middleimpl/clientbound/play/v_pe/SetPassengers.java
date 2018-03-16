package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import org.bukkit.util.Vector;

import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.set.hash.TIntHashSet;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.unsafe.pemetadata.PEMetaProviderSPI;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetPassengers;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.WatchedEntityCache;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.minecraftdata.PocketData;
import protocolsupport.protocol.utils.minecraftdata.PocketData.PocketEntityData.PocketRiderInfo;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntity.DataCache;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class SetPassengers extends MiddleSetPassengers {

	protected static final int UNLINK = 0;
	protected static final int LINK = 1;

	protected final TIntObjectHashMap<TIntHashSet> passengers = new TIntObjectHashMap<>();

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		WatchedEntityCache wecache = cache.getWatchedEntityCache();
		ProtocolVersion version = connection.getVersion();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		NetworkEntity vehicle = wecache.getWatchedEntity(vehicleId);
		if (vehicle != null) {
			TIntHashSet prevPassengersIds = passengers.get(vehicleId);
			if (prevPassengersIds == null) {
				prevPassengersIds = new TIntHashSet();
			}
			TIntHashSet newPassengersIds = new TIntHashSet(passengersIds);
			for (int passengerId : passengersIds) {
				NetworkEntity passenger = wecache.getWatchedEntity(passengerId);
				if (passenger != null) {
					DataCache data = passenger.getDataCache();
					if (data.isRiding() && (data.getVehicleId() != vehicleId)) {
						//In case we are jumping from vehicle to vehicle.
						packets.add(create(version, data.getVehicleId(), passengerId, UNLINK));
					}
					PocketRiderInfo rideInfo = PocketData.getPocketEntityData(vehicle.getType()).getRiderInfo();
					if (rideInfo != null) {
						if (passenger.getType() == NetworkEntityType.PLAYER) {
							float vehicleSize = PEMetaProviderSPI.getProvider().getSizeScale(vehicle.getUUID(), vehicle.getId(), vehicle.getType().getBukkitType()) * vehicle.getDataCache().getSizeModifier();
							data.setRiderPosition(rideInfo.getPosition().multiply(new Vector(vehicleSize, vehicleSize, vehicleSize)));
						} else {
							data.setRiderPosition(new Vector(0, -0.125, 0));
						}
						data.setRotationlock(rideInfo.getRotationLock());
						data.setVehicleId(vehicleId);
					}
					packets.add(EntityMetadata.createFaux(passenger, cache.getAttributesCache().getLocale(), version));
					packets.add(create(version, vehicleId, passengerId, LINK));
				}
			}
			prevPassengersIds.forEach(new TIntProcedure() {
				@Override
				public boolean execute(int passengerId) {
					if (!newPassengersIds.contains(passengerId)) {
						NetworkEntity passenger = wecache.getWatchedEntity(passengerId);
						if (passenger != null) {
							//Also update meta.
							passenger.getDataCache().setVehicleId(0);
							packets.add(EntityMetadata.createFaux(passenger, cache.getAttributesCache().getLocale(), version));
							packets.add(create(version, vehicleId, passengerId, UNLINK));
						}
					}
					return true;
				}
			});
			passengers.put(vehicleId, newPassengersIds);
		}
		return packets;
	}

	public static ClientBoundPacketData create(ProtocolVersion version, int vehicleId, int passengerId, int action) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_LINK, version);
		VarNumberSerializer.writeSVarLong(serializer, vehicleId);
		VarNumberSerializer.writeSVarLong(serializer, passengerId);
		serializer.writeByte(action);
		return serializer;
	}

}
