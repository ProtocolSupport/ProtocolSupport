package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleBlockDig;

public class BlockDig extends MiddleBlockDig {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		status = serializer.readUnsignedByte();
		position = serializer.readPosition();
		face = serializer.readUnsignedByte();
	}

}
