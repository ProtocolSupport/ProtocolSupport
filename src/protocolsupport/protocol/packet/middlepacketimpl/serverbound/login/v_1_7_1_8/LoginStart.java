package protocolsupport.protocol.packet.middlepacketimpl.serverbound.login.v_1_7_1_8;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.serverbound.login.MiddleLoginStart;

public class LoginStart extends MiddleLoginStart {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		name = serializer.readString(16);
	}

}
