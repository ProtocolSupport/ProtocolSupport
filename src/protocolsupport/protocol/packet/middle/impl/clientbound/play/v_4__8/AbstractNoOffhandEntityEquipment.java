package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__15.AbstractSingleEntityEquipment;
import protocolsupport.protocol.types.NetworkItemStack;

public abstract class AbstractNoOffhandEntityEquipment extends AbstractSingleEntityEquipment {

	protected AbstractNoOffhandEntityEquipment(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClientSingle(Slot slot, NetworkItemStack itemstack) {
		if (slot != Slot.OFF_HAND) {
			writeToClient0(slot, itemstack);
		}
	}

	protected abstract void writeToClient0(Slot slot, NetworkItemStack itemstack);


	public static final MiddleEntityEquipment.Slot[] SUPPORTED_SLOTS = {
		MiddleEntityEquipment.Slot.MAIN_HAND,
		MiddleEntityEquipment.Slot.BOOTS,
		MiddleEntityEquipment.Slot.LEGGINGS,
		MiddleEntityEquipment.Slot.CHESTPLATE,
		MiddleEntityEquipment.Slot.HELMET
	};

	public static int getSlotId(Slot slot) {
		return switch (slot) {
			case MAIN_HAND -> 0;
			case BOOTS -> 1;
			case LEGGINGS -> 2;
			case CHESTPLATE -> 3;
			case HELMET -> 4;
			default -> throw new IllegalArgumentException("Equipment slot " + slot + " doesn't exist on version <= 1.8");
		};
	}

}
