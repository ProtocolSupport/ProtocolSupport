package protocolsupport.protocol.transformer.middlepacketimpl.v_1_6_1_7.serverbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddlePositionLook;

public class PositionLook extends MiddlePositionLook {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		 x = serializer.readDouble();
		 y = serializer.readDouble();
		 serializer.readDouble();
		 z = serializer.readDouble();
		 yaw = serializer.readFloat();
		 pitch = serializer.readFloat();
		 onGround = serializer.readBoolean();
	}

}
