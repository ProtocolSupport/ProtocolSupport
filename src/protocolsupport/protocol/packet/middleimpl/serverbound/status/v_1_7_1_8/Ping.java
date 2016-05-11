package protocolsupport.protocol.packet.middleimpl.serverbound.status.v_1_7_1_8;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.status.MiddlePing;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public class Ping extends MiddlePing {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		pingId = serializer.readLong();
	}

}
