package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4_1_5_1_6_1_7;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleAnimation;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public class Animation extends MiddleAnimation {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		serializer.readInt();
		serializer.readByte();
	}

}
