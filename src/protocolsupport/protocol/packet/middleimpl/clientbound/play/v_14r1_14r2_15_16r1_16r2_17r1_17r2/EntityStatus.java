package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityStatus;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EntityStatus extends MiddleEntityStatus {

	public EntityStatus(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entitystatus = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_STATUS);
		entitystatus.writeInt(entity.getId());
		entitystatus.writeByte(status);
		codec.writeClientbound(entitystatus);
	}

}
