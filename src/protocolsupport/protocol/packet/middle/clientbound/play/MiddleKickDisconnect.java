package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleKickDisconnect<T> extends ClientBoundMiddlePacket<T> {

	protected String messageJson;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		messageJson = serializer.readString(Short.MAX_VALUE);
	}

}
