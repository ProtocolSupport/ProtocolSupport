package protocolsupport.protocol.packet.middle.clientbound.status;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleServerInfo<T> extends ClientBoundMiddlePacket<T> {

	protected String pingJson;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		pingJson = serializer.readString(Short.MAX_VALUE);
	}

}
