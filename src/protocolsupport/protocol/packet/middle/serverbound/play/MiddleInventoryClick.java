package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.storage.netcache.WindowCache;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.SlotDoesntExistException;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleInventoryClick extends ServerBoundMiddlePacket {

	protected final WindowCache windowCache = cache.getWindowCache();

	public MiddleInventoryClick(ConnectionImpl connection) {
		super(connection);
	}

	protected byte windowId;
	protected int actionNumber;
	protected int mode;
	protected int button;
	protected int slot;
	protected NetworkItemStack itemstack;

	@Override
	public RecyclableCollection<? extends IPacketData> toNative() {
		try {
			return RecyclableSingletonList.create(create(
				windowId, actionNumber, mode, button, windowCache.getOpenedWindowRemapper().fromWindowSlot(windowId, slot), itemstack
			));
		} catch (SlotDoesntExistException e) {
			return RecyclableEmptyList.get();
		}
	}

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
