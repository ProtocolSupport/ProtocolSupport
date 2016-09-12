package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleCollectEffect<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected int collectorId;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		entityId = serializer.readVarInt();
		collectorId = serializer.readVarInt();
	}

}
