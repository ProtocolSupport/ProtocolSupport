package protocolsupport.protocol.transformer.middlepacket.clientbound.status;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddlePong<T> extends ClientBoundMiddlePacket<T> {

	protected long pingId;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		pingId = serializer.readLong();
	}

}
