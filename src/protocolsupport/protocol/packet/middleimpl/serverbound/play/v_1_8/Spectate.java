package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSpecate;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public class Spectate extends MiddleSpecate {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		entityUUID = serializer.readUUID();
	}

}
