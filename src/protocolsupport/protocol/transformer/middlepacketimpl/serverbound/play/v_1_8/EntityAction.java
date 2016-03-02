package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleEntityAction;

public class EntityAction extends MiddleEntityAction {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		entityId = serializer.readVarInt();
		actionId = serializer.readVarInt();
		jumpBoost = serializer.readVarInt();
	}

}
