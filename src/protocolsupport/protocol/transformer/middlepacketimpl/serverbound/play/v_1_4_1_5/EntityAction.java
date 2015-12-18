package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleEntityAction;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
public class EntityAction extends MiddleEntityAction {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		entityId = serializer.readInt();
		actionId = serializer.readByte() - 1;
	}

}
