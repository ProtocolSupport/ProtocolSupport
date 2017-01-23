package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_6__1_7;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePositionLook;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class PositionLook extends MiddlePositionLook {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		 x = serializer.readDouble();
		 y = serializer.readDouble();
		 serializer.readDouble();
		 z = serializer.readDouble();
		 yaw = serializer.readFloat();
		 pitch = serializer.readFloat();
		 onGround = serializer.readBoolean();
	}

}
