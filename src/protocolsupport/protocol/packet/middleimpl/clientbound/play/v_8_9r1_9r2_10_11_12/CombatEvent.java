package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12;

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
