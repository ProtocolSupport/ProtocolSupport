package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4_1_5_1_6_1_7;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTabComplete;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class TabComplete extends MiddleTabComplete {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		string = serializer.readString();
	}

}
