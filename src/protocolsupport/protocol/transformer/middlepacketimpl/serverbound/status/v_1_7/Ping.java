package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.status.v_1_7;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.status.MiddlePing;

public class Ping extends MiddlePing {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		pingId = serializer.readLong();
	}

}
