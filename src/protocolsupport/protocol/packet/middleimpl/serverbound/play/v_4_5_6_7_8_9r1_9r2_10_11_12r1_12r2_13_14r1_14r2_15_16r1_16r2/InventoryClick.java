package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClick;
import protocolsupport.protocol.storage.netcache.InventoryTransactionCache;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.NoSuchSlotException;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.WindowSlot;

public class InventoryClick extends MiddleInventoryClick {

	public InventoryClick(MiddlePacketInit init) {
		super(init);
		modifiedSlots = new SlotItem[0];
	}

	protected final InventoryTransactionCache transactioncache = cache.getTransactionCache();

	@Override
	protected void read(ByteBuf clientdata) {
		windowId = clientdata.readByte();
		slot = clientdata.readShort();
		button = clientdata.readUnsignedByte();
		clientdata.readShort(); //action number
		mode = clientdata.readUnsignedByte();
		clickedItem = ItemStackCodec.readItemStack(clientdata, version);
	}

	@Override
	protected void handle() {
		stateId = transactioncache.getInventoryStateServerId();

		try {
			WindowSlot windowSlot = windowCache.getOpenedWindowRemapper().fromClientSlot(windowId, slot);
			windowId = windowSlot.getWindowId();
			slot = windowSlot.getSlot();
		} catch (NoSuchSlotException e) {
			throw CancelMiddlePacketException.INSTANCE;
		}

		if ((button == 0) && (mode == MODE_SHIFT_CLICK)) {
			clickedItem = null;
		}
	}

}
