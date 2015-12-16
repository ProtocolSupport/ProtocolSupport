package protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.serverbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleClientCommand;

public class ClientCommand extends MiddleClientCommand {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		serializer.readByte();
	}

}
