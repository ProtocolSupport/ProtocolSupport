package protocolsupport.protocol.packet.middleimpl.serverbound.status.v_1_7__1_8;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.status.MiddleServerInfoRequest;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class ServerInfoRequest extends MiddleServerInfoRequest {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
	}

}
