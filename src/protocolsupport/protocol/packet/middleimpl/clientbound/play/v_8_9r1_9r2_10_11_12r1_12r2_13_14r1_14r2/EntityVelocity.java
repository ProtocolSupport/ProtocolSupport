package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityVelocity;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class EntityVelocity extends MiddleEntityVelocity {

	public EntityVelocity(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData entityvelocity = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_ENTITY_VELOCITY);
		VarNumberSerializer.writeVarInt(entityvelocity, entityId);
		entityvelocity.writeShort(motX);
		entityvelocity.writeShort(motY);
		entityvelocity.writeShort(motZ);
		codec.write(entityvelocity);
	}

}
