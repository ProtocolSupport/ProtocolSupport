package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleEntityStatus<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected int status;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		entityId = serializer.readInt();
		status = serializer.readUnsignedByte();
	}

}
