package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCombatEvent;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CombatEvent extends MiddleCombatEvent {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_COMBAT_EVENT_ID, version);
		MiscSerializer.writeEnum(serializer, type);
		switch (type) {
			case ENTER_COMBAT: {
				break;
			}
			case END_COMBAT: {
				VarNumberSerializer.writeVarInt(serializer, duration);
				serializer.writeInt(entityId);
				break;
			}
			case ENTITY_DEAD: {
				VarNumberSerializer.writeVarInt(serializer, playerId);
				serializer.writeInt(entityId);
				StringSerializer.writeString(serializer, version, message);
				break;
			}
		}
		return RecyclableSingletonList.create(serializer);
	}

}
