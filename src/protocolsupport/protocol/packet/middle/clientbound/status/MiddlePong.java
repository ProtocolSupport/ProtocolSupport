package protocolsupport.protocol.packet.middle.clientbound.status;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddlePong extends ClientBoundMiddlePacket {

	protected long pingId;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		pingId = serializer.readLong();
	}

}
