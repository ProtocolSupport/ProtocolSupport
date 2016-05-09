package protocolsupport.protocol.packet.middlepacketimpl.serverbound.play.v_1_7;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.serverbound.play.MiddleClientSettings;

public class ClientSettings extends MiddleClientSettings {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		locale = serializer.readString(7);
		viewDist = serializer.readByte();
		chatMode = serializer.readByte();
		chatColors = serializer.readBoolean();
		serializer.readByte();
		serializer.readBoolean();
		skinFlags = 255;
		mainHand = 1;
	}

}
