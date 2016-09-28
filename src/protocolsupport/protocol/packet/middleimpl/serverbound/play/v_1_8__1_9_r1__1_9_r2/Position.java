package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePosition;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class Position extends MiddlePosition {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		x = serializer.readDouble();
		y = serializer.readDouble();
		z = serializer.readDouble();
		onGround = serializer.readBoolean();
	}

}
