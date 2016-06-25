package protocolsupport.protocol.packet.middle.clientbound.login;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleLoginSuccess<T> extends ClientBoundMiddlePacket<T> {

	protected String uuidstring;
	protected String name;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		uuidstring = serializer.readString(36);
		name = serializer.readString(16);
	}

}
