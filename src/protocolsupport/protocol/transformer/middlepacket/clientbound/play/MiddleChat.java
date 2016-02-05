package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleChat<T> extends ClientBoundMiddlePacket<T> {

	protected String chatJson;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		chatJson = serializer.readString(Short.MAX_VALUE);
	}

}
