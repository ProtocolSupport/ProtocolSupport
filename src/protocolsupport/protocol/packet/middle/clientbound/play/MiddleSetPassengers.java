package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleSetPassengers<T> extends ClientBoundMiddlePacket<T> {

	protected int vehicleId;
	protected int[] passengersIds;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		vehicleId = serializer.readVarInt();
		passengersIds = new int[serializer.readVarInt()];
		for (int i = 0; i < passengersIds.length; i++) {
			passengersIds[i] = serializer.readVarInt();
		}
	}

}
