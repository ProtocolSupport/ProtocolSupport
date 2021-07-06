package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.storage.netcache.window.WindowCache;
import protocolsupport.protocol.types.NetworkItemStack;

public abstract class MiddleInventoryClick extends ServerBoundMiddlePacket {

	protected final WindowCache windowCache = cache.getWindowCache();

	protected MiddleInventoryClick(MiddlePacketInit init) {
		super(init);
	}

	protected byte windowId;
	protected int stateId;
	protected int mode;
	protected int button;
	protected int slot;
	protected SlotItem[] modifiedSlots;
	protected NetworkItemStack clickedItem;

	@Override
	protected void write() {
		codec.writeServerbound(create(windowId, stateId, mode, button, slot, modifiedSlots, clickedItem));
	}

	public static final int MODE_CLICK = 0;
	public static final int MODE_SHIFT_CLICK = 1;
	public static final int MODE_NUMBER_KEY = 2;
	public static final int MODE_MIDDLE_CLICK = 3;
	public static final int MODE_DROP = 4;
	public static final int MODE_DRAG = 5;
	public static final int MODE_DOUBLE_CLICK = 6;

	public static ServerBoundPacketData create(int windowId, int stateId, int mode, int button, int slot, SlotItem[] modifiedSlot, NetworkItemStack clickedItem) {
		ServerBoundPacketData inventoryclickPacket = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_WINDOW_CLICK);
		inventoryclickPacket.writeByte(windowId);
		VarNumberCodec.writeVarInt(inventoryclickPacket, stateId);
		inventoryclickPacket.writeShort(slot);
		inventoryclickPacket.writeByte(button);
		VarNumberCodec.writeVarInt(inventoryclickPacket, mode);
		ArrayCodec.writeVarIntTArray(inventoryclickPacket, modifiedSlot, (slotitemTo, slotitem) -> {
			slotitemTo.writeShort(slotitem.getSlot());
			ItemStackCodec.writeItemStack(slotitemTo, slotitem.getItemStack());
		});
		ItemStackCodec.writeItemStack(inventoryclickPacket, clickedItem);
		return inventoryclickPacket;
	}

	protected static class SlotItem {

		protected final int slot;
		protected final NetworkItemStack itemstack;

		public SlotItem(int slot, NetworkItemStack itemstack) {
			this.slot = slot;
			this.itemstack = itemstack;
		}

		public int getSlot() {
			return slot;
		}

		public NetworkItemStack getItemStack() {
			return itemstack;
		}

	}

}
