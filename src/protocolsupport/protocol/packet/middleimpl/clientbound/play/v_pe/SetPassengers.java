package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import org.bukkit.util.Vector;

import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.set.hash.TIntHashSet;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetPassengers;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntity.DataCache;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class SetPassengers extends MiddleSetPassengers {

	public static final int UNLINK = 0;
	public static final int LINK = 1;
	public static final int PASSENGER = 2;

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
					//TODO: Fix and Update this: Rider positions.
					DataCache data = passenger.getDataCache();
					if(vehicle.isOfType(NetworkEntityType.PIG)) {
						data.rider = new DataCache.Rider(new Vector(0.0, 2.8, 0.0), false);
						packets.add(EntitySetAttributes.create(version, cache.getSelfPlayerEntityId(), EntitySetAttributes.createAttribute("minecraft:horse.jump_strength", 0.432084373616155)));
					} else if(vehicle.isOfType(NetworkEntityType.BASE_HORSE)) {
						data.rider = new DataCache.Rider(new Vector(0.0, 2.3, -0.2), true, 180f, -180f);
						packets.add(EntitySetAttributes.create(version, cache.getSelfPlayerEntityId(), EntitySetAttributes.createAttribute("minecraft:horse.jump_strength", 0.966967527085333)));
					} else {
						data.rider = new DataCache.Rider(true);
					}
					packets.add(EntityMetadata.createFaux(passenger, cache.getLocale(), version));

					packets.add(create(version, vehicleId, passengerId, LINK));
					if(cache.isSelf(passengerId)) {
						packets.add(create(version, vehicleId, 0, LINK));
					}
				}
			}
			prevPassengersIds.forEach(new TIntProcedure() {
				@Override
				public boolean execute(int passengerId) {
					if (!newPassengersIds.contains(passengerId)) {
						NetworkEntity passenger = cache.getWatchedEntity(passengerId);
						if(passenger != null) {
							//Also update meta.
							passenger.getDataCache().rider = new DataCache.Rider(false);
							packets.add(EntityMetadata.createFaux(passenger, cache.getLocale(), version));
							packets.add(create(version, vehicleId, passengerId, UNLINK));
							if(cache.isSelf(passengerId)) {
								packets.add(create(version, vehicleId, 0, UNLINK));
							}
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
