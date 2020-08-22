package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractNoOffhandEntityEquipment;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractRemoveEquipmentOnDeathEntityStatus;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.utils.i18n.I18NData;

public class EntityStatus extends AbstractRemoveEquipmentOnDeathEntityStatus {

	public EntityStatus(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeEquipmentRemove() {
		for (MiddleEntityEquipment.Slot slot : AbstractNoOffhandEntityEquipment.SUPPORTED_SLOTS) {
			codec.write(EntityEquipment.create(version, I18NData.DEFAULT_LOCALE, entityId, slot, NetworkItemStack.NULL));
		}
	}

}
