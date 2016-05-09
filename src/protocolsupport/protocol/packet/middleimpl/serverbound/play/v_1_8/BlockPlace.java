package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;

public class BlockPlace extends MiddleBlockPlace  {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		position = serializer.readPosition();
		face = serializer.readByte();
		serializer.readItemStack();
		cX = serializer.readUnsignedByte();
		cY = serializer.readUnsignedByte();
		cZ = serializer.readUnsignedByte();
	}

}
