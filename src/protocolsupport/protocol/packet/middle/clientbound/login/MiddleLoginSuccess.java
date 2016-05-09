package protocolsupport.protocol.packet.middle.clientbound.login;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleLoginSuccess<T> extends ClientBoundMiddlePacket<T> {

	protected String uuidstring;
	protected String name;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		uuidstring = serializer.readString(36);
		name = serializer.readString(16);
	}

}
