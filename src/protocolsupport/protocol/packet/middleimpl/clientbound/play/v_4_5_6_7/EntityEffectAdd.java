package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEffectAdd;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EntityEffectAdd extends MiddleEntityEffectAdd {

	public EntityEffectAdd(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData entityeffectadd = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_ENTITY_EFFECT_ADD);
		entityeffectadd.writeInt(entityId);
		entityeffectadd.writeByte(effectId);
		entityeffectadd.writeByte(amplifier);
		entityeffectadd.writeShort(duration);
		codec.write(entityeffectadd);
	}

}
