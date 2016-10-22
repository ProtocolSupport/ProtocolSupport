package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_10;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleResourcePackStatus;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class ResourcePackStatus extends MiddleResourcePackStatus {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		result = serializer.readVarInt();
	}

}
