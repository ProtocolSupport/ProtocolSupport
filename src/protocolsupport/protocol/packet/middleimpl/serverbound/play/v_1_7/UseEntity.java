package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_7;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public class UseEntity extends MiddleUseEntity {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		entityId = serializer.readInt();
		action = serializer.readByte();
	}

}
