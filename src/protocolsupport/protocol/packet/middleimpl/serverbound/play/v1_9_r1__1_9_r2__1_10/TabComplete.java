package protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTabComplete;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class TabComplete extends MiddleTabComplete {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		string = serializer.readString();
		assumecommand = serializer.readBoolean();
		if (serializer.readBoolean()) {
			position = serializer.readPosition();
		}
	}

}
