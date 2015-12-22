package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_6_1_7;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddlePlayerAbilities;

public class PlayerAbilities extends MiddlePlayerAbilities {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		flags = serializer.readUnsignedByte();
		flySpeed = serializer.readFloat();
		walkSpeed = serializer.readFloat();
	}

}
