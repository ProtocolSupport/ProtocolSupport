package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17.AbstractKnownEntityStatus;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractRemoveEquipmentOnDeathEntityStatus extends AbstractKnownEntityStatus {

	protected AbstractRemoveEquipmentOnDeathEntityStatus(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		if (
			(status == STATUS_LIVING_DEATH) &&
			(entity.getType() == NetworkEntityType.PLAYER) &&
			(entity.getId() != entityCache.getSelfId())
		) {
			writeEquipmentRemove();
		}

		ClientBoundPacketData entitystatus = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_STATUS);
		entitystatus.writeInt(entityId);
		entitystatus.writeByte(status);
		codec.writeClientbound(entitystatus);
	}

	protected abstract void writeEquipmentRemove();

}
