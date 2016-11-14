package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_11;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class BlockPlace extends MiddleBlockPlace {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		position = serializer.readPosition();
		face = serializer.readVarInt();
		usedHand = serializer.readVarInt();
		cX = serializer.readFloat();
		cY = serializer.readFloat();
		cZ = serializer.readFloat();
	}

}