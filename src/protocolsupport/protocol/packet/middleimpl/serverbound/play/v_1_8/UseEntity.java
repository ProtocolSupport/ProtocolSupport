package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8;

import org.bukkit.util.Vector;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class UseEntity extends MiddleUseEntity {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		entityId = serializer.readVarInt();
		action = serializer.readEnum(Action.class);
		if (action == Action.INTERACT_AT) {
			interactedAt = new Vector(serializer.readFloat(), serializer.readFloat(), serializer.readFloat());
		}
	}

}
