package protocolsupport.protocol.packet.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleResourcePack<T> extends ClientBoundMiddlePacket<T> {

	protected String url;
	protected String hash;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		url = serializer.readString(Short.MAX_VALUE);
		hash = serializer.readString(Short.MAX_VALUE);
	}

}
