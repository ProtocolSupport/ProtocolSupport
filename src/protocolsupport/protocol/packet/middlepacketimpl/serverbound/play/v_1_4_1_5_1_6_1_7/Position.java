package protocolsupport.protocol.packet.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.serverbound.play.MiddlePosition;

public class Position extends MiddlePosition {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		x = serializer.readDouble();
		y = serializer.readDouble();
		serializer.readDouble();
		z = serializer.readDouble();
		onGround = serializer.readBoolean();
	}

}
