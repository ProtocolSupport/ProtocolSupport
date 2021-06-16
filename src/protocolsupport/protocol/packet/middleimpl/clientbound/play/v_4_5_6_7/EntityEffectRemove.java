package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEffectRemove;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EntityEffectRemove extends MiddleEntityEffectRemove {

	public EntityEffectRemove(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entityeffectremove = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_ENTITY_EFFECT_REMOVE);
		entityeffectremove.writeInt(entityId);
		entityeffectremove.writeByte(effectId);
		codec.writeClientbound(entityeffectremove);
	}

}
