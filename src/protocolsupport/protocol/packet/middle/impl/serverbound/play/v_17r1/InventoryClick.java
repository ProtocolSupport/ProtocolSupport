package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_17r1;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleInventoryClick;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV17r1;
import protocolsupport.protocol.storage.netcache.InventoryTransactionCache;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.NoSuchSlotException;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.WindowSlot;

public class InventoryClick extends MiddleInventoryClick implements IServerboundMiddlePacketV17r1 {

	public InventoryClick(IMiddlePacketInit init) {
		super(init);
	}

	protected final InventoryTransactionCache transactioncache = cache.getTransactionCache();

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
		stateId = transactioncache.getInventoryStateServerId();

		try {
			WindowSlot windowSlot = windowCache.getOpenedWindowRemapper().fromClientSlot(windowId, slot);
			windowId = windowSlot.getWindowId();
			slot = windowSlot.getSlot();
		} catch (NoSuchSlotException e) {
			throw MiddlePacketCancelException.INSTANCE;
		}
	}

}
