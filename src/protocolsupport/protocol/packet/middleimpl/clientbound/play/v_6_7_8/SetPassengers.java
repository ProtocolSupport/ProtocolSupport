package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7_8;

import gnu.trove.map.hash.TIntIntHashMap;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetPassengers;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SetPassengers extends MiddleSetPassengers {

	private final TIntIntHashMap vehiclePassenger = new TIntIntHashMap();
	private int passengerId;

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_LEASH_ID, connection.getVersion());
		serializer.writeInt(passengerId);
		serializer.writeInt(passengersIds.length == 0 ? -1 : vehicleId);
		serializer.writeBoolean(false);
		return RecyclableSingletonList.create(serializer);
	}

	@Override
	public boolean postFromServerRead() {
		if (passengersIds.length == 0) {
			passengerId = vehiclePassenger.remove(vehicleId);
		} else {
			passengerId = passengersIds[0];
			vehiclePassenger.put(vehicleId, passengerId);
		}
		return true;
	}

}
