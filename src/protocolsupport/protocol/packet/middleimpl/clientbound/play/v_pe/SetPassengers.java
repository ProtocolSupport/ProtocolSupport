package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.set.hash.TIntHashSet;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetPassengers;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
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
				//TODO: set passenger offset metadata to fix stting position
			}
		}
		prevPassengersIds.forEach(new TIntProcedure() {
			@Override
			public boolean execute(int passengerId) {
				if (!newPassengersIds.contains(passengerId)) {
					packets.add(create(version, vehicleId, passengerId, false));
				}
				return true;
			}
		});
		passengers.put(vehicleId, newPassengersIds);
		return packets;
	}

	public static ClientBoundPacketData create(ProtocolVersion version, int vehicleId, int passengerId, boolean add) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(41, version);
		VarNumberSerializer.writeSVarLong(serializer, vehicleId);
		VarNumberSerializer.writeSVarLong(serializer, passengerId);
		serializer.writeBoolean(add);
		return serializer;
	}

}
