package protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.serverbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleKeepAlive;

public class KeepAlive extends MiddleKeepAlive {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		keepAliveId = serializer.readInt();
	}

}
