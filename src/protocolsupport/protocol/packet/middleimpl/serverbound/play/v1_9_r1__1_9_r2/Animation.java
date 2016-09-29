package protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleAnimation;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class Animation extends MiddleAnimation {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		usedHand = serializer.readVarInt();
	}

}
