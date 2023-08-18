package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__15;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.types.NetworkItemStack;

public abstract class AbstractSingleEntityEquipment extends MiddleEntityEquipment {

	protected AbstractSingleEntityEquipment(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		for (Entry entry : entries) {
			writeToClientSingle(entry.getSlot(), entry.getItemStack());
		}
	}

	protected abstract void writeToClientSingle(Slot slot, NetworkItemStack itemstack);

}
