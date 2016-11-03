package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEntityAction;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class EntityAction extends MiddleEntityAction {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		entityId = serializer.readVarInt();
		actionId = serializer.readVarInt();
		jumpBoost = serializer.readVarInt();
		if (serializer.getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_8) && (actionId == 6)) {
			actionId = 7;
		}
	}

}
