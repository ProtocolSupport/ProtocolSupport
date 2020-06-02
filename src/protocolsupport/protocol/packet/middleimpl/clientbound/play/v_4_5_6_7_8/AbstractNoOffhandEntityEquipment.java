package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEquipment;

public abstract class AbstractNoOffhandEntityEquipment extends MiddleEntityEquipment {

	public AbstractNoOffhandEntityEquipment(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		if (slot == Slot.OFF_HAND) {
			return;
		}
		writeToClient0();
	}

	protected abstract void writeToClient0();

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
