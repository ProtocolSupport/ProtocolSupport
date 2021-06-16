package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.window.WindowCache;
import protocolsupport.protocol.types.NetworkItemStack;

public abstract class MiddleInventoryClick extends ServerBoundMiddlePacket {

	protected final WindowCache windowCache = cache.getWindowCache();

	protected MiddleInventoryClick(MiddlePacketInit init) {
		super(init);
	}

	protected byte windowId;
	protected int mode;
	protected int button;
	protected int slot;
	protected SlotItem[] modifiedSlots;
	protected NetworkItemStack clickedItem;

	@Override
	protected void write() {
		codec.writeServerbound(create(windowId, mode, button, slot, modifiedSlots, clickedItem));
	}

	public static final int MODE_CLICK = 0;
	public static final int MODE_SHIFT_CLICK = 1;
	public static final int MODE_NUMBER_KEY = 2;
	public static final int MODE_MIDDLE_CLICK = 3;
	public static final int MODE_DROP = 4;
	public static final int MODE_DRAG = 5;
	public static final int MODE_DOUBLE_CLICK = 6;

	public static ServerBoundPacketData create(int windowId, int mode, int button, int slot, SlotItem[] modifiedSlot, NetworkItemStack clickedItem) {
		ServerBoundPacketData inventoryclickPacket = ServerBoundPacketData.create(ServerBoundPacketType.SERVERBOUND_PLAY_WINDOW_CLICK);
		inventoryclickPacket.writeByte(windowId);
		inventoryclickPacket.writeShort(slot);
		inventoryclickPacket.writeByte(button);
		VarNumberSerializer.writeVarInt(inventoryclickPacket, mode);
		ArraySerializer.writeVarIntTArray(inventoryclickPacket, modifiedSlot, (slotitemTo, slotitem) -> {
			slotitemTo.writeShort(slotitem.getSlot());
			ItemStackSerializer.writeItemStack(slotitemTo, slotitem.getItemStack());
		});
		ItemStackSerializer.writeItemStack(inventoryclickPacket, clickedItem);
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
