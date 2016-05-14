package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEntityAction;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class EntityAction extends MiddleEntityAction {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		entityId = serializer.readVarInt();
		actionId = serializer.readVarInt();
		jumpBoost = serializer.readVarInt();
		if (actionId == 6) {
			actionId = 7;
		}
	}

}
