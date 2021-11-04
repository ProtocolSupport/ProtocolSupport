package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8_9r1_9r2;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityEffectAdd;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;

public class EntityEffectAdd extends MiddleEntityEffectAdd implements
IClientboundMiddlePacketV8,
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2 {

	public EntityEffectAdd(IMiddlePacketInit init) {
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
		io.writeClientbound(entityeffectadd);
	}

}
