package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleLook;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class Look extends MiddleLook {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		yaw = serializer.readFloat();
		pitch = serializer.readFloat();
		onGround = serializer.readBoolean();
	}

}
