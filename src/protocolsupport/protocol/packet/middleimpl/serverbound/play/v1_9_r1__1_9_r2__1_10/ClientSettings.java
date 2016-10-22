package protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientSettings;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class ClientSettings extends MiddleClientSettings {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		locale = serializer.readString(7);
		viewDist = serializer.readByte();
		chatMode = serializer.readByte();
		chatColors = serializer.readBoolean();
		skinFlags = serializer.readUnsignedByte();
		mainHand = serializer.readVarInt();
	}

}
