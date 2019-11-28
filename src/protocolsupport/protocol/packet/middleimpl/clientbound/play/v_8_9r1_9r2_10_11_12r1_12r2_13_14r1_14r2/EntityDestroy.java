package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityDestroy;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;

public class EntityDestroy extends MiddleEntityDestroy {

	public EntityDestroy(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData entitydestory = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_DESTROY);
		ArraySerializer.writeVarIntVarIntArray(entitydestory, entityIds);
		codec.write(entitydestory);
	}

}
