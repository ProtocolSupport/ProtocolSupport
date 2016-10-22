package protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class BlockPlace extends MiddleBlockPlace {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		position = serializer.readPosition();
		face = serializer.readVarInt();
		usedHand = serializer.readVarInt();
		cX = serializer.readUnsignedByte();
		cY = serializer.readUnsignedByte();
		cZ = serializer.readUnsignedByte();
	}

}
