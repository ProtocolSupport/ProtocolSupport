package protocolsupport.protocol.packet.middle.clientbound.status;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleServerInfo extends ClientBoundMiddlePacket {

	protected String pingJson;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		pingJson = serializer.readString();
	}

}
