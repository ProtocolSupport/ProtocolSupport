package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ByteArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleSetPassengers extends ClientBoundMiddlePacket {

	protected int vehicleId;
	protected int[] passengersIds;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		vehicleId = VarNumberSerializer.readVarInt(serverdata);
		passengersIds = ByteArraySerializer.readVarIntVarIntArray(serverdata);
	}

}
