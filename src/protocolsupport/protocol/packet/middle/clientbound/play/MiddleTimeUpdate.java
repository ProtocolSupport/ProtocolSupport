package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleTimeUpdate extends ClientBoundMiddlePacket {

	protected long worldAge;
	protected long timeOfDay;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		worldAge = serializer.readLong();
		timeOfDay = serializer.readLong();
	}

	@Override
	public void handle() {
	}

}
