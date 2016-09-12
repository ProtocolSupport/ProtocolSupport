package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_7__1_8;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientCommand;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class ClientCommand extends MiddleClientCommand {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		command = serializer.readVarInt();
	}

}
