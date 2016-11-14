package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class BlockPlace extends MiddleBlockPlace {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		position = serializer.readLegacyPositionB();
		face = serializer.readByte();
		serializer.readItemStack();
		cX = serializer.readUnsignedByte() / 16.0F;
		cY = serializer.readUnsignedByte() / 16.0F;
		cZ = serializer.readUnsignedByte() / 16.0F;
	}

}
