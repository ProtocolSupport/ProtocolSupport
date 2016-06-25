package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class UseEntity extends MiddleUseEntity {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		serializer.readInt();
		entityId = serializer.readInt();
		action = serializer.readBoolean() ? 1 : 0;
	}

}
