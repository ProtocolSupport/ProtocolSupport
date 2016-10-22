package protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class UseItem extends MiddleBlockPlace {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		usedHand = serializer.readVarInt();
		face = -1;
	}

}
