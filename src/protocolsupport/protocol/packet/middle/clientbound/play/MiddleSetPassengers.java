package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleSetPassengers extends ClientBoundMiddlePacket {

	protected int vehicleId;
	protected int[] passengersIds;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		vehicleId = VarNumberSerializer.readVarInt(serverdata);
		passengersIds = new int[VarNumberSerializer.readVarInt(serverdata)];
		for (int i = 0; i < passengersIds.length; i++) {
			passengersIds[i] = VarNumberSerializer.readVarInt(serverdata);
		}
	}

}
