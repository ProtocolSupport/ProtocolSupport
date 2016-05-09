package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleEntityAttach<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected int vehicleId;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		entityId = serializer.readInt();
		vehicleId = serializer.readInt();
	}

}
