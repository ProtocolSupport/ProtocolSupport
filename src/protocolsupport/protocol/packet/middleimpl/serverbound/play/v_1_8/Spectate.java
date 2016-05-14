package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSpecate;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class Spectate extends MiddleSpecate {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		entityUUID = serializer.readUUID();
	}

}
