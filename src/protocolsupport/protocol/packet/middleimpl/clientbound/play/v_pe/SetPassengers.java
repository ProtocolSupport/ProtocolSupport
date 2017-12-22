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
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.minecraftdata.PocketData;
import protocolsupport.protocol.utils.minecraftdata.PocketData.PocketEntityData.PocketRiderInfo;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntity.DataCache;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class SetPassengers extends MiddleSetPassengers {

	public static final int UNLINK = 0;
	public static final int LINK = 1;

	private final TIntObjectHashMap<TIntHashSet> passengers = new TIntObjectHashMap<>();

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		NetworkEntity vehicle = cache.getWatchedEntity(vehicleId);
		if(vehicle != null) {
			TIntHashSet prevPassengersIds = passengers.get(vehicleId);
			if (prevPassengersIds == null) {
				prevPassengersIds = new TIntHashSet();
			}
			TIntHashSet newPassengersIds = new TIntHashSet(passengersIds);
			for (int passengerId : passengersIds) {
				NetworkEntity passenger = cache.getWatchedEntity(passengerId);
				if (passenger != null) {
					//MOJANG.... WHYYYYY?!
					if (vehicle.isOfType(NetworkEntityType.PIG)) {
						packets.add(EntitySetAttributes.create(version, vehicle, EntitySetAttributes.createAttribute("minecraft:horse.jump_strength", 0.432084373616155))); 
					}
					
					DataCache data = passenger.getDataCache();
					if (data.isRiding() && data.getVehicleId() != vehicleId) {
						//In case we are jumping from vehicle to vehicle.
						packets.addAll(link(version, data.getVehicleId(), passengerId, UNLINK));
					}
					PocketRiderInfo rideInfo = PocketData.getPocketEntityData(vehicle.getType()).getRiderInfo();
					if (rideInfo != null) {
						System.out.println("Passenger Type: " + passenger.getType().toString() + "Veh pos + " + rideInfo.getPosition());
						if (passenger.getType() == NetworkEntityType.PLAYER) {
							float vehicleSize = PEMetaProviderSPI.getProvider().getSizeScale(vehicle.getUUID(), vehicle.getId(), vehicle.getType().getBukkitType()) * vehicle.getDataCache().getSizeModifier();
							data.setRiderPosition(rideInfo.getPosition().multiply(new Vector(vehicleSize, vehicleSize, vehicleSize)));
						} else {
							data.setRiderPosition(new Vector(0, -0.125, 0));
						}
						data.setRotationlock(rideInfo.getRotationLock());
						data.setVehicleId(vehicleId);
					}
					packets.add(EntityMetadata.createFaux(passenger, cache.getLocale(), version));
					packets.addAll(link(version, vehicleId, passengerId, LINK));
				}
			}
			prevPassengersIds.forEach(new TIntProcedure() {
				@Override
				public boolean execute(int passengerId) {
					if (!newPassengersIds.contains(passengerId)) {
						NetworkEntity passenger = cache.getWatchedEntity(passengerId);
						if (passenger != null) {
							//Also update meta.
							passenger.getDataCache().setVehicleId(0);
							packets.add(EntityMetadata.createFaux(passenger, cache.getLocale(), version));
							packets.addAll(link(version, vehicleId, passengerId, UNLINK));
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
	
	public RecyclableArrayList<ClientBoundPacketData> link(ProtocolVersion version, int vehicleId, int passengerId, int action) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		packets.add(create(version, vehicleId, passengerId, action));
		if(cache.isSelf(passengerId)) {
			packets.add(create(version, vehicleId, 0, action));
		}
		return packets;
	}

}
