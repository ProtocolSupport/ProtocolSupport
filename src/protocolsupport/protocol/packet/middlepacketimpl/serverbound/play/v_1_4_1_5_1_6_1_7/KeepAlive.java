package protocolsupport.protocol.packet.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.serverbound.play.MiddleKeepAlive;

public class KeepAlive extends MiddleKeepAlive {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		keepAliveId = serializer.readInt();
	}

}
