package protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.serverbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleUseEntity;

public class UseEntity extends MiddleUseEntity {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		serializer.readInt();
		entityId = serializer.readInt();
		action = serializer.readBoolean() ? 1 : 0;
	}

}
