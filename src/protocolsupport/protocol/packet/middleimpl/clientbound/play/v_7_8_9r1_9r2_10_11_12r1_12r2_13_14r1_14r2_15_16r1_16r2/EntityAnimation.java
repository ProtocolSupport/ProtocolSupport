package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityAnimation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class EntityAnimation extends MiddleEntityAnimation {

	public EntityAnimation(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData entityanimation = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_ANIMATION);
		VarNumberSerializer.writeVarInt(entityanimation, entityId);
		entityanimation.writeByte(animation.getId());
		codec.write(entityanimation);
	}

}
