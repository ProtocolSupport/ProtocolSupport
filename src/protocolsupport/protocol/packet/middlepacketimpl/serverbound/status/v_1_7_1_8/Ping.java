package protocolsupport.protocol.packet.middlepacketimpl.serverbound.status.v_1_7_1_8;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.serverbound.status.MiddlePing;

public class Ping extends MiddlePing {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		pingId = serializer.readLong();
	}

}
