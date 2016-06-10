package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEntityAction;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class EntityAction extends MiddleEntityAction {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		entityId = serializer.readInt();
		actionId = serializer.readByte() - 1;
	}

}
