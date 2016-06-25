package protocolsupport.protocol.packet.middle.clientbound.login;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleLoginDisconnect<T> extends ClientBoundMiddlePacket<T> {

	protected String messageJson;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		messageJson = serializer.readString();
	}

}
