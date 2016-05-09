package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleCollectEffect<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected int collectorId;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		entityId = serializer.readVarInt();
		collectorId = serializer.readVarInt();
	}

}
