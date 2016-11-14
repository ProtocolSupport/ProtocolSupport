package protocolsupport.protocol.packet.middleimpl.serverbound.status.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11;

import protocolsupport.protocol.packet.middle.serverbound.status.MiddlePing;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class Ping extends MiddlePing {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		pingId = serializer.readLong();
	}

}
