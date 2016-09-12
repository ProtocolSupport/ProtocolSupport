package protocolsupport.protocol.packet.middle.clientbound.status;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddlePong<T> extends ClientBoundMiddlePacket<T> {

	protected long pingId;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		pingId = serializer.readLong();
	}

}
