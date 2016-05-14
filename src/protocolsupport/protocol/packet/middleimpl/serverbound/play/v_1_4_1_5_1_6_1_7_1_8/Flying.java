package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4_1_5_1_6_1_7_1_8;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleFlying;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class Flying extends MiddleFlying {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		onGround = serializer.readBoolean();
	}

}
