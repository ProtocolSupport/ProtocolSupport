package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_7;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleClientCommand;

public class ClientCommand extends MiddleClientCommand {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		command = serializer.readVarInt();
	}

}
