package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleEntityLeash<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected int vehicleId;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		entityId = serializer.readInt();
		vehicleId = serializer.readInt();
	}

}
