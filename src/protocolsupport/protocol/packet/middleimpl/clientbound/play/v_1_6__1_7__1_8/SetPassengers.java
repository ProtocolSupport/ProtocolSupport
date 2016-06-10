package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_6__1_7__1_8;

import java.io.IOException;

import gnu.trove.map.hash.TIntIntHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetPassengers;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SetPassengers extends MiddleSetPassengers<RecyclableCollection<PacketData>> {

	private final TIntIntHashMap vehiclePassenger = new TIntIntHashMap();
	private int passengerId;

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_ENTITY_ATTACH_ID, version);
		serializer.writeInt(passengerId);
		serializer.writeInt(passengersIds.length == 0 ? -1 : vehicleId);
		serializer.writeBoolean(false);
		return RecyclableSingletonList.create(serializer);
	}

	@Override
	public void handle() {
		if (passengersIds.length == 0) {
			passengerId = vehiclePassenger.remove(vehicleId);
		} else {
			passengerId = passengersIds[0];
			vehiclePassenger.put(vehicleId, passengerId);
		}
	}

}
