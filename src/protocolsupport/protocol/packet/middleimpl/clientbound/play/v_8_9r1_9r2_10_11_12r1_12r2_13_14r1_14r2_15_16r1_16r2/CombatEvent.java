package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCombatEvent;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class CombatEvent extends MiddleCombatEvent {

	public CombatEvent(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData combatevent = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_COMBAT_EVENT);
		MiscSerializer.writeVarIntEnum(combatevent, type);
		switch (type) {
			case ENTER_COMBAT: {
				break;
			}
			case END_COMBAT: {
				VarNumberSerializer.writeVarInt(combatevent, duration);
				combatevent.writeInt(entityId);
				break;
			}
			case ENTITY_DEAD: {
				VarNumberSerializer.writeVarInt(combatevent, playerId);
				combatevent.writeInt(entityId);
				StringSerializer.writeVarIntUTF8String(combatevent, message);
				break;
			}
		}
	}

}
