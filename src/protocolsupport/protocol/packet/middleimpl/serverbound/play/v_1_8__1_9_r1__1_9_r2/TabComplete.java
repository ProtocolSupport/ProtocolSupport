package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTabComplete;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class TabComplete extends MiddleTabComplete {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		string = serializer.readString();
		if (serializer.readBoolean()) {
			position = serializer.readPosition();
		}
	}

}
