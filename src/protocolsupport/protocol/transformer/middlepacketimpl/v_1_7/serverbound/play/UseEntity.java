package protocolsupport.protocol.transformer.middlepacketimpl.v_1_7.serverbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleUseEntity;

public class UseEntity extends MiddleUseEntity {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		entityId = serializer.readInt();
		action = serializer.readByte();
	}

}
