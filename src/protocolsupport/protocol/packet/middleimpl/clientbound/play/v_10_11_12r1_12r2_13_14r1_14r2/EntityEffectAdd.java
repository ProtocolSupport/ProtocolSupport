package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEffectAdd;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class EntityEffectAdd extends MiddleEntityEffectAdd {

	public EntityEffectAdd(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData effectadd = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_EFFECT_ADD);
		VarNumberSerializer.writeVarInt(effectadd, entityId);
		effectadd.writeByte(effectId);
		effectadd.writeByte(amplifier);
		VarNumberSerializer.writeVarInt(effectadd, duration);
		effectadd.writeByte(flags);
		codec.write(effectadd);
	}

}
