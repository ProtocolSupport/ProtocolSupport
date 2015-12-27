package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleCollectEffect<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected int collectorId;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		entityId = serializer.readVarInt();
		collectorId = serializer.readVarInt();
	}

}
