package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCombatEvent;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CombatEvent extends MiddleCombatEvent {

	public CombatEvent(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_COMBAT_EVENT);
		MiscSerializer.writeVarIntEnum(serializer, type);
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
				StringSerializer.writeVarIntUTF8String(serializer, message);
				break;
			}
		}
		return RecyclableSingletonList.create(serializer);
	}

}
