package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientCommand;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class ClientCommand extends MiddleClientCommand {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		serializer.readByte();
	}

}
