package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSpecate;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class Spectate extends MiddleSpecate {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		entityUUID = serializer.readUUID();
	}

}
