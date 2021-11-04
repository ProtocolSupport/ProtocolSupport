package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleInventoryClick;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r2;
import protocolsupport.protocol.storage.netcache.InventoryTransactionCache;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.NoSuchSlotException;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.WindowSlot;
import protocolsupport.protocol.types.NetworkItemStack;

public class InventoryClick extends MiddleInventoryClick implements
IServerboundMiddlePacketV4,
IServerboundMiddlePacketV5,
IServerboundMiddlePacketV6,
IServerboundMiddlePacketV7,
IServerboundMiddlePacketV8,
IServerboundMiddlePacketV9r1,
IServerboundMiddlePacketV9r2,
IServerboundMiddlePacketV10,
IServerboundMiddlePacketV11,
IServerboundMiddlePacketV12r1,
IServerboundMiddlePacketV12r2,
IServerboundMiddlePacketV13,
IServerboundMiddlePacketV14r1,
IServerboundMiddlePacketV14r2,
IServerboundMiddlePacketV15,
IServerboundMiddlePacketV16r1,
IServerboundMiddlePacketV16r2 {

	public InventoryClick(IMiddlePacketInit init) {
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
			throw MiddlePacketCancelException.INSTANCE;
		}

		if ((button == 0) && (mode == MODE_SHIFT_CLICK)) {
			clickedItem = NetworkItemStack.NULL;
		}
	}

}
