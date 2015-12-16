package protocolsupport.protocol.transformer.middlepacketimpl.v_1_5.serverbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddlePlayerAbilities;

public class PlayerAbilities extends MiddlePlayerAbilities {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		flags = serializer.readUnsignedByte();
		flySpeed = serializer.readUnsignedByte() / 255.0F;
		walkSpeed = serializer.readUnsignedByte() / 255.0F;
	}

}
