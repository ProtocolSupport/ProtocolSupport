package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractRemoveEquipmentOnDeathEntityStatus;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractNoOffhandEntityEquipment;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.utils.i18n.I18NData;

public class EntityStatus extends AbstractRemoveEquipmentOnDeathEntityStatus implements IClientboundMiddlePacketV8 {

	public EntityStatus(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeEquipmentRemove() {
		for (MiddleEntityEquipment.Slot slot : AbstractNoOffhandEntityEquipment.SUPPORTED_SLOTS) {
			io.writeClientbound(EntityEquipment.create(version, I18NData.DEFAULT_LOCALE, entity.getId(), slot, NetworkItemStack.NULL));
		}
	}

}
