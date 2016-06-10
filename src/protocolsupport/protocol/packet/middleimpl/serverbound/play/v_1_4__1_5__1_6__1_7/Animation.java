package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleAnimation;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class Animation extends MiddleAnimation {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		serializer.readInt();
		serializer.readByte();
	}

}
