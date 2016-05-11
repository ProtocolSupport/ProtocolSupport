package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4_1_5;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEntityAction;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public class EntityAction extends MiddleEntityAction {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		entityId = serializer.readInt();
		actionId = serializer.readByte() - 1;
	}

}
