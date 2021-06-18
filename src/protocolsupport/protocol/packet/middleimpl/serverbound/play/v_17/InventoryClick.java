package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_17;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClick;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.NoSuchSlotException;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.WindowSlot;

public class InventoryClick extends MiddleInventoryClick {

	public InventoryClick(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		windowId = clientdata.readByte();
		slot = clientdata.readShort();
		button = clientdata.readUnsignedByte();
		mode = VarNumberCodec.readVarInt(clientdata);
		modifiedSlots = ArrayCodec.readVarIntTArray(clientdata, SlotItem.class, slotitemFrom -> new SlotItem(clientdata.readShort(), ItemStackCodec.readItemStack(clientdata, version)));
		clickedItem = ItemStackCodec.readItemStack(clientdata, version);
	}

	@Override
	protected void handle() {
		try {
			WindowSlot windowSlot = windowCache.getOpenedWindowRemapper().fromClientSlot(windowId, slot);
			windowId = windowSlot.getWindowId();
			slot = windowSlot.getSlot();
		} catch (NoSuchSlotException e) {
			throw CancelMiddlePacketException.INSTANCE;
		}

		if ((button == 0) && (mode == MODE_SHIFT_CLICK)) {
			modifiedSlots = new SlotItem[0];
		}
	}

}
