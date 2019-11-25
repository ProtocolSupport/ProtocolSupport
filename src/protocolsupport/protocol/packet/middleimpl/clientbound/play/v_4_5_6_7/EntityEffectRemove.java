package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEffectRemove;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EntityEffectRemove extends MiddleEntityEffectRemove {

	public EntityEffectRemove(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData entityeffectremove = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_ENTITY_EFFECT_REMOVE);
		entityeffectremove.writeInt(entityId);
		entityeffectremove.writeByte(effectId);
		codec.write(entityeffectremove);
	}

}
