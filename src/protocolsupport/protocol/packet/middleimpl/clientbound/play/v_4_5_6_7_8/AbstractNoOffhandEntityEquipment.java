package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractSingleEntityEquipment;
import protocolsupport.protocol.types.NetworkItemStack;

public abstract class AbstractNoOffhandEntityEquipment extends AbstractSingleEntityEquipment {

	protected AbstractNoOffhandEntityEquipment(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClientSingle(Slot slot, NetworkItemStack itemstack) {
		if (slot != Slot.OFF_HAND) {
			writeToClient0(slot, itemstack);
		}
	}

	protected abstract void writeToClient0(Slot slot, NetworkItemStack itemstack);


	public static final MiddleEntityEquipment.Slot[] SUPPORTED_SLOTS = new MiddleEntityEquipment.Slot[] {
		MiddleEntityEquipment.Slot.MAIN_HAND,
		MiddleEntityEquipment.Slot.BOOTS,
		MiddleEntityEquipment.Slot.LEGGINGS,
		MiddleEntityEquipment.Slot.CHESTPLATE,
		MiddleEntityEquipment.Slot.HELMET
	};

	public static int getSlotId(Slot slot) {
		switch (slot) {
			case MAIN_HAND: {
				return 0;
			}
			case BOOTS: {
				return 1;
			}
			case LEGGINGS: {
				return 2;
			}
			case CHESTPLATE: {
				return 3;
			}
			case HELMET: {
				return 4;
			}
			default: {
				throw new IllegalArgumentException("Equipment slot " + slot + " doesn't exist on version <= 1.8");
			}
		}
	}

}
