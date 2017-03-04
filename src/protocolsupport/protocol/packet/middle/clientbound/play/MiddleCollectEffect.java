package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleCollectEffect extends ClientBoundMiddlePacket {

	protected int entityId;
	protected int collectorId;
	protected int itemCount;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		entityId = serializer.readVarInt();
		collectorId = serializer.readVarInt();
		itemCount = serializer.readVarInt();
	}

}
