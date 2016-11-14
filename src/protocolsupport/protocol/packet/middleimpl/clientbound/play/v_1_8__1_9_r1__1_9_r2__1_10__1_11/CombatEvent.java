package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCombatEvent;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CombatEvent extends MiddleCombatEvent<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_COMBAT_EVENT, version);
		serializer.writeEnum(type);
		switch (type) {
			case ENTER_COMBAT: {
				break;
			}
			case END_COMBAT: {
				serializer.writeVarInt(duration);
				serializer.writeInt(entityId);
				break;
			}
			case ENTITY_DEAD: {
				serializer.writeVarInt(playerId);
				serializer.writeInt(entityId);
				serializer.writeString(message);
				break;
			}
		}
		return RecyclableSingletonList.create(serializer);
	}

}
