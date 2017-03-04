package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleCustomPayload extends ClientBoundMiddlePacket {

	protected String tag;
	protected ProtocolSupportPacketDataSerializer data;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		tag = serializer.readString(20);
		data = serializer;
	}

}
