package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class BlockDig extends MiddleBlockDig {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		status = serializer.readUnsignedByte();
		position = serializer.readPosition();
		face = serializer.readUnsignedByte();
	}

}
