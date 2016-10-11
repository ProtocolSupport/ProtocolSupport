package protocolsupport.protocol.packet.middleimpl.serverbound.login.v_1_7__1_8__1_9_r1__1_9_r2__1_10;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.login.MiddleLoginStart;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class LoginStart extends MiddleLoginStart {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		name = serializer.readString(16);
	}

}
