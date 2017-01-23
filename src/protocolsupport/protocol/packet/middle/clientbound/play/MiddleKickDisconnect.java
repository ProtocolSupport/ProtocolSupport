package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleKickDisconnect<T> extends ClientBoundMiddlePacket<T> {

	protected String messageJson;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		messageJson = serializer.readString();
	}

}
