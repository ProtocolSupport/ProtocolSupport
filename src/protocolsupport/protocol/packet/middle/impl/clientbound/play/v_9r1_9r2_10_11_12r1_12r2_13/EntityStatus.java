package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractRemoveEquipmentOnDeathEntityStatus;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.EntityEquipment;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.utils.i18n.I18NData;

public class EntityStatus extends AbstractRemoveEquipmentOnDeathEntityStatus implements
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2,
IClientboundMiddlePacketV13 {

	public EntityStatus(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeEquipmentRemove() {
		for (MiddleEntityEquipment.Slot slot : EntityEquipment.SUPPORTED_SLOTS) {
			io.writeClientbound(EntityEquipment.create(version, I18NData.DEFAULT_LOCALE, entity.getId(), slot, NetworkItemStack.NULL));
		}
	}

}
