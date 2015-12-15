package protocolsupport.protocol.transformer.middlepacketimpl.v_1_7.serverbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleClientSettings;

public class ClientSettings extends MiddleClientSettings {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		locale = serializer.readString(7);
		viewDist = serializer.readByte();
		chatMode = serializer.readByte();
		chatColors = serializer.readBoolean();
		serializer.readByte();
		serializer.readBoolean();
	}

}
