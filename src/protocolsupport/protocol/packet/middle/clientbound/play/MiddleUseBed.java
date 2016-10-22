package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.types.Position;

public abstract class MiddleUseBed<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected Position bed;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		entityId = serializer.readVarInt();
		bed = serializer.readPosition();
	}

}
