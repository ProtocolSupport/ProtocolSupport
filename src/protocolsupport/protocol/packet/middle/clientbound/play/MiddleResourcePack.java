package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleResourcePack extends ClientBoundMiddlePacket {

	protected String url;
	protected String hash;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		url = serializer.readString();
		hash = serializer.readString();
	}

}
