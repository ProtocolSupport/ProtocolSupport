package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

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
		codec.write(create(entityId, motX, motY, motZ));
	}

	public static ClientBoundPacketData create(int entityId, int motX, int motY, int motZ) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_VELOCITY);
		serializer.writeInt(entityId);
		serializer.writeShort(motX);
		serializer.writeShort(motY);
		serializer.writeShort(motZ);
		return serializer;
	}

}
