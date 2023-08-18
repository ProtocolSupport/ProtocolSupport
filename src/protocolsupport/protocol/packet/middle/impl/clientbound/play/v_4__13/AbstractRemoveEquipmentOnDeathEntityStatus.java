package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13;

import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__20.EntityStatus;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractRemoveEquipmentOnDeathEntityStatus extends EntityStatus {

	protected AbstractRemoveEquipmentOnDeathEntityStatus(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		if (
			(status == STATUS_LIVING_DEATH) &&
			(entity.getType() == NetworkEntityType.PLAYER) &&
			(entity != entityCache.getSelf())
		) {
			writeEquipmentRemove();
		}

		super.write();
	}

	protected abstract void writeEquipmentRemove();

}
