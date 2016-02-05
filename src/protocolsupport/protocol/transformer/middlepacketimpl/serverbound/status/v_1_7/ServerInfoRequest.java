package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.status.v_1_7;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.status.MiddleServerInfoRequest;

public class ServerInfoRequest extends MiddleServerInfoRequest {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
	}

}
