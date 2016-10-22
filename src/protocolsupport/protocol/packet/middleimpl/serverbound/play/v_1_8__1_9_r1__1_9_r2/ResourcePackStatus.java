package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleResourcePackStatus;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class ResourcePackStatus extends MiddleResourcePackStatus {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		serializer.readString(40);
		result = serializer.readVarInt();
	}

}
