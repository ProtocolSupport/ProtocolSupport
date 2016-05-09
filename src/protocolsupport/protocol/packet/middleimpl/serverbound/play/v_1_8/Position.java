package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePosition;

public class Position extends MiddlePosition {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		x = serializer.readDouble();
		y = serializer.readDouble();
		z = serializer.readDouble();
		onGround = serializer.readBoolean();
	}

}
