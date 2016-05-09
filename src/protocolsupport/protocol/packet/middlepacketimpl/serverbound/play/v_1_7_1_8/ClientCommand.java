package protocolsupport.protocol.packet.middlepacketimpl.serverbound.play.v_1_7_1_8;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.serverbound.play.MiddleClientCommand;

public class ClientCommand extends MiddleClientCommand {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		command = serializer.readVarInt();
	}

}
