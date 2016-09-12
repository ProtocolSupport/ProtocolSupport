package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePosition;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class Position extends MiddlePosition {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		x = serializer.readDouble();
		y = serializer.readDouble();
		serializer.readDouble();
		z = serializer.readDouble();
		onGround = serializer.readBoolean();
	}

}
