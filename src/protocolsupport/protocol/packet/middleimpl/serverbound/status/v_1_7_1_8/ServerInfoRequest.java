package protocolsupport.protocol.packet.middleimpl.serverbound.status.v_1_7_1_8;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.status.MiddleServerInfoRequest;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public class ServerInfoRequest extends MiddleServerInfoRequest {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
	}

}
