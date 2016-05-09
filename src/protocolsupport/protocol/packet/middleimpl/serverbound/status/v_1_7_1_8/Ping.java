package protocolsupport.protocol.packet.middleimpl.serverbound.status.v_1_7_1_8;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middle.serverbound.status.MiddlePing;

public class Ping extends MiddlePing {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		pingId = serializer.readLong();
	}

}
