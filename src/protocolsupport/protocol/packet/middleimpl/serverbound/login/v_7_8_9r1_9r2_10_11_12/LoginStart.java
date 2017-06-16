package protocolsupport.protocol.packet.middleimpl.serverbound.login.v_7_8_9r1_9r2_10_11_12;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.login.MiddleLoginStart;
import protocolsupport.protocol.serializer.StringSerializer;

public class LoginStart extends MiddleLoginStart {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		name = StringSerializer.readString(clientdata, version, 16);
	}

}
