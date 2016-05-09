package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4_1_5_1_6;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientCommand;

public class ClientCommand extends MiddleClientCommand {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		serializer.readByte();
	}

}
