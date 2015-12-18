package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_7;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleUseEntity;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
public class UseEntity extends MiddleUseEntity {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		entityId = serializer.readInt();
		action = serializer.readByte();
	}

}
