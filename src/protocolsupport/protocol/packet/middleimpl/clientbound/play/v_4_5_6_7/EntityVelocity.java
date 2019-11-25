package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityVelocity;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EntityVelocity extends MiddleEntityVelocity {

	public EntityVelocity(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData entityvelocity = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_ENTITY_VELOCITY);
		entityvelocity.writeInt(entityId);
		entityvelocity.writeShort(motX);
		entityvelocity.writeShort(motY);
		entityvelocity.writeShort(motZ);
		codec.write(entityvelocity);
	}

}
