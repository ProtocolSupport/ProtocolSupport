package protocolsupport.protocol.packet.middle.clientbound.login;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleLoginDisconnect extends ClientBoundMiddlePacket {

	protected String messageJson;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		messageJson = serializer.readString();
	}

}
