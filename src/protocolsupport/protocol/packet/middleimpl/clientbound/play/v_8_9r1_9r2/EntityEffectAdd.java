package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEffectAdd;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EntityEffectAdd extends MiddleEntityEffectAdd {

	public EntityEffectAdd(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entityeffectadd = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_EFFECT_ADD);
		VarNumberCodec.writeVarInt(entityeffectadd, entity.getId());
		entityeffectadd.writeByte(effectId);
		entityeffectadd.writeByte(amplifier);
		VarNumberCodec.writeVarInt(entityeffectadd, duration);
		entityeffectadd.writeBoolean((flags & 0x01) != 0);
		codec.writeClientbound(entityeffectadd);
	}

}
