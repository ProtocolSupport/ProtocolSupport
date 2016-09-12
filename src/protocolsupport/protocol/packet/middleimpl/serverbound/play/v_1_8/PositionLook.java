package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePositionLook;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class PositionLook extends MiddlePositionLook  {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		 x = serializer.readDouble();
		 y = serializer.readDouble();
		 z = serializer.readDouble();
		 yaw = serializer.readFloat();
		 pitch = serializer.readFloat();
		 onGround = serializer.readBoolean();
	}

}
