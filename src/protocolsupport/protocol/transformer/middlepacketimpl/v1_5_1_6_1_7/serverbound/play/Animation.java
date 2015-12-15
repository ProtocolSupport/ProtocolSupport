package protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.serverbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleAnimation;

public class Animation extends MiddleAnimation {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		serializer.readInt();
		serializer.readByte();
	}

}
