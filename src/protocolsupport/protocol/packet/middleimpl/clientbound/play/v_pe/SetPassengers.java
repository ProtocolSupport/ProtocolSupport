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
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.protocol.utils.types.NetworkEntity.DataCache;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class SetPassengers extends MiddleSetPassengers {

	private final TIntObjectHashMap<TIntHashSet> passengers = new TIntObjectHashMap<>();

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
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
					packets.add(create(version, vehicleId, (cache.isSelf(passengerId)) ? 0 : passengerId, true));
					
					//Update rider positions too.
					DataCache data = passenger.getDataCache();
					if(vehicle.isOfType(NetworkEntityType.PIG)) data.rider = data.new Rider(new Vector(0.0, 2.83, 0.0), false);
					if(vehicle.isOfType(NetworkEntityType.BASE_HORSE)) data.rider = data.new Rider(new Vector(0.0, 2.3, -0.2), true, 180f, -180f);
					else data.rider = data.new Rider(true);
					cache.updateWatchedDataCache(passengerId, data);
					packets.add(EntityMetadata.create(cache.getWatchedEntity(passengerId), version));
				}
			}
			prevPassengersIds.forEach(new TIntProcedure() {
				@Override
				public boolean execute(int passengerId) {
					if (!newPassengersIds.contains(passengerId)) {
						packets.add(create(version, vehicleId, (cache.isSelf(passengerId)) ? 0 : passengerId, false));
						
						//Also update meta.
						DataCache data = cache.getWatchedEntity(passengerId).getDataCache();
						data.rider = data.new Rider(false);
						cache.updateWatchedDataCache(passengerId, data);
						packets.add(EntityMetadata.create(cache.getWatchedEntity(passengerId), version));
					}
					return true;
				}
			});
			passengers.put(vehicleId, newPassengersIds);
		}
		return packets;
	}

	public static ClientBoundPacketData create(ProtocolVersion version, int vehicleId, int passengerId, boolean add) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_LINK, version);
		VarNumberSerializer.writeVarLong(serializer, vehicleId);
		VarNumberSerializer.writeVarLong(serializer, passengerId);
		serializer.writeBoolean(add);
		return serializer;
	}

}
