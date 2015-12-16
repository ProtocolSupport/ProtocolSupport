package protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.serverbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleClientSettings;

public class ClientSettings extends MiddleClientSettings {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		locale = serializer.readString(7);
		viewDist = serializer.readByte();
		int chatState = serializer.readByte();
		chatMode = chatState & 7;
		chatColors = (chatState & 8) == 8;
		serializer.readByte();
		serializer.readBoolean();
	}

}
