package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientSettings;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class ClientSettings extends MiddleClientSettings {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		locale = serializer.readString(7);
		viewDist = serializer.readByte();
		int chatState = serializer.readByte();
		chatMode = chatState & 7;
		chatColors = (chatState & 8) == 8;
		serializer.readByte();
		serializer.readBoolean();
		skinFlags = 255;
		mainHand = 1;
	}

}
