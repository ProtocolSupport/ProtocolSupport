package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleSetPassengers<T> extends ClientBoundMiddlePacket<T> {

	protected int vehicleId;
	protected int[] passengersIds;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		vehicleId = serializer.readVarInt();
		passengersIds = new int[serializer.readVarInt()];
		for (int i = 0; i < passengersIds.length; i++) {
			passengersIds[i] = serializer.readVarInt();
		}
	}

}
