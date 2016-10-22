package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class BlockPlace extends MiddleBlockPlace {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		position = serializer.readPosition();
		face = serializer.readByte();
		serializer.readItemStack();
		cX = serializer.readUnsignedByte();
		cY = serializer.readUnsignedByte();
		cZ = serializer.readUnsignedByte();
	}

}
