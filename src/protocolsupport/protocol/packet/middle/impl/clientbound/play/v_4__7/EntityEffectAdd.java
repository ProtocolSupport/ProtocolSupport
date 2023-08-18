package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__7;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityEffectAdd;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;

public class EntityEffectAdd extends MiddleEntityEffectAdd implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7
{

	public EntityEffectAdd(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entityeffectadd = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_EFFECT_ADD);
		entityeffectadd.writeInt(entity.getId());
		entityeffectadd.writeByte(effectId);
		entityeffectadd.writeByte(amplifier);
		entityeffectadd.writeShort(duration);
		io.writeClientbound(entityeffectadd);
	}

}
