package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.storage.netcache.window.WindowCache;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleInventoryClick extends ServerBoundMiddlePacket {

	protected final WindowCache windowCache = cache.getWindowCache();

	public MiddleInventoryClick(ConnectionImpl connection) {
		super(connection);
	}

	protected static final int MODE_NOOP = -1;

	protected byte windowId;
	protected int actionNumber;
	protected int mode;
	protected int button;
	protected int slot;
	protected NetworkItemStack itemstack;

	@Override
	public RecyclableCollection<? extends IPacketData> toNative() {
		if (mode != MODE_NOOP) {
			return RecyclableSingletonList.create(create(windowId, actionNumber, mode, button, slot, itemstack));
		} else {
			return RecyclableEmptyList.get();
		}
	}

	public static final int MODE_CLICK = 0;
	public static final int MODE_SHIFT_CLICK = 1;
	public static final int MODE_NUMBER_KEY = 2;
	public static final int MODE_MIDDLE_CLICK = 3;
	public static final int MODE_DROP = 4;
	public static final int MODE_DRAG = 5;
	public static final int MODE_DOUBLE_CLICK = 6;

	public static ServerBoundPacketData create(int windowId, int actionNumber, int mode, int button, int slot, NetworkItemStack itemstack) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_WINDOW_CLICK);
		creator.writeByte(windowId);
		creator.writeShort(slot);
		creator.writeByte(button);
		creator.writeShort(actionNumber);
		creator.writeByte(mode);
		ItemStackSerializer.writeItemStack(creator, itemstack);
		return creator;
	}

}
