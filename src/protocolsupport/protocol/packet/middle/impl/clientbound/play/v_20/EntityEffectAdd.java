package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityEffectAdd;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class EntityEffectAdd extends MiddleEntityEffectAdd implements
IClientboundMiddlePacketV20 {

	public EntityEffectAdd(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData effectaddPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_EFFECT_ADD);
		VarNumberCodec.writeVarInt(effectaddPacket, entity.getId());
		effectaddPacket.writeByte(effectId);
		effectaddPacket.writeByte(amplifier);
		VarNumberCodec.writeVarInt(effectaddPacket, duration);
		effectaddPacket.writeByte(flags);
		OptionalCodec.writeOptional(effectaddPacket, factor, ItemStackCodec::writeDirectTag);
		io.writeClientbound(effectaddPacket);
	}

}
