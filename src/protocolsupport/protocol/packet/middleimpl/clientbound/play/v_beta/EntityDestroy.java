package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityDestroy;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EntityDestroy extends MiddleEntityDestroy {

	public EntityDestroy(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		for (int entityId : entityIds) {
			ClientBoundPacketData entitydestory = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_DESTROY);
			entitydestory.writeInt(entityId);
			codec.write(entitydestory);
		}
	}

}
