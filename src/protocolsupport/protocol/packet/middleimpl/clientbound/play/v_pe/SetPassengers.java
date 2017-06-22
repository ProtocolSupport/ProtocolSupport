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
import protocolsupport.protocol.utils.types.NetworkEntity.DataCache;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class SetPassengers extends MiddleSetPassengers {

	private final TIntObjectHashMap<TIntHashSet> passengers = new TIntObjectHashMap<>();

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		TIntHashSet prevPassengersIds = passengers.get(vehicleId);
		if (prevPassengersIds == null) {
			prevPassengersIds = new TIntHashSet();
		}
		TIntHashSet newPassengersIds = new TIntHashSet(passengersIds);
		for (int passengerId : passengersIds) {
			if (!prevPassengersIds.contains(passengerId)) {
				packets.add(create(version, vehicleId, passengerId, true));
				
				//TODO: Find correct values for ofset etc. This is an example:
				DataCache data = cache.getWatchedEntity(passengerId).getDataCache();
				data.rider.riding = true;
				switch(cache.getWatchedEntity(vehicleId).getType()) {
				case BOAT:
					data.rider.position = new Vector(0, 1, 0);
					data.rider.rotationLocked = false;
					data.rider.rotationMax = 180f;
					data.rider.rotationMin = -180f;
				break;
				default:
					data.rider.position = new Vector(0, 0, 0);
					data.rider.rotationLocked = false;
					data.rider.rotationMax = 180f;
					data.rider.rotationMin = -180f;
				break;
				}
				cache.updateWatchedDataCache(passengerId, data);
				//Update meta too.
				packets.add(EntityMetadata.create(cache.getWatchedEntity(passengerId), version));
			}
		}
		prevPassengersIds.forEach(new TIntProcedure() {
			@Override
			public boolean execute(int passengerId) {
				if (!newPassengersIds.contains(passengerId)) {
					packets.add(create(version, vehicleId, passengerId, false));
					DataCache data = cache.getWatchedEntity(passengerId).getDataCache();
					data.rider.riding = false;
					cache.updateWatchedDataCache(passengerId, data);
					//Also update meta.
					packets.add(EntityMetadata.create(cache.getWatchedEntity(passengerId), version));
				}
				return true;
			}
		});
		passengers.put(vehicleId, newPassengersIds);
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
