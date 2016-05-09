package protocolsupport.protocol.packet.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleCollectEffect<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected int collectorId;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		entityId = serializer.readVarInt();
		collectorId = serializer.readVarInt();
	}

}
