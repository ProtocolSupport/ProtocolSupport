package protocolsupport.protocol.packet.middlepacketimpl.serverbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.serverbound.play.MiddleClientSettings;

public class ClientSettings extends MiddleClientSettings {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		locale = serializer.readString(7);
		viewDist = serializer.readByte();
		chatMode = serializer.readByte();
		chatColors = serializer.readBoolean();
		skinFlags = serializer.readUnsignedByte();
	}

}
