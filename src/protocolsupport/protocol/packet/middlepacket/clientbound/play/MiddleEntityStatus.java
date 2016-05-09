package protocolsupport.protocol.packet.middlepacket.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleEntityStatus<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected int status;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		entityId = serializer.readInt();
		status = serializer.readUnsignedByte();
	}

}
