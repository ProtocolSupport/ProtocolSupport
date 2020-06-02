package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityStatus;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EntityStatus extends MiddleEntityStatus {

	public EntityStatus(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData entitystatus = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_STATUS);
		entitystatus.writeInt(entity.getId());
		entitystatus.writeByte(status);
		codec.write(entitystatus);
	}

}
