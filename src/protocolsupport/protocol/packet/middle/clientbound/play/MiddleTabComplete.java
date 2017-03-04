package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleTabComplete extends ClientBoundMiddlePacket {

	protected String[] matches;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		matches = new String[serializer.readVarInt()];
		for (int i = 0; i < matches.length; i++) {
			matches[i] = serializer.readString();
		}
	}

}
