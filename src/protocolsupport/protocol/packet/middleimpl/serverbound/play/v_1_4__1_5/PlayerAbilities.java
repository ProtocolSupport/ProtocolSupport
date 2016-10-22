package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePlayerAbilities;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class PlayerAbilities extends MiddlePlayerAbilities {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		flags = serializer.readUnsignedByte();
		flySpeed = serializer.readUnsignedByte() / 255.0F;
		walkSpeed = serializer.readUnsignedByte() / 255.0F;
	}

}
