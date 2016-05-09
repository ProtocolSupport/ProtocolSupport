package protocolsupport.protocol.packet.middle.clientbound.login;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleLoginDisconnect<T> extends ClientBoundMiddlePacket<T> {

	protected String messageJson;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		messageJson = serializer.readString(Short.MAX_VALUE);
	}

}
