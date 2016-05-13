package protocolsupport.protocol.packet.middle.clientbound.status;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public abstract class MiddleServerInfo<T> extends ClientBoundMiddlePacket<T> {

	protected String pingJson;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		pingJson = serializer.readString(Short.MAX_VALUE);
	}

}
