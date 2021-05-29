package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.storage.netcache.window.WindowCache;
import protocolsupport.protocol.types.NetworkItemStack;

public abstract class MiddleInventoryClick extends ServerBoundMiddlePacket {

	protected final WindowCache windowCache = cache.getWindowCache();

	protected MiddleInventoryClick(MiddlePacketInit init) {
		super(init);
	}

	protected byte windowId;
	protected int actionNumber;
	protected int mode;
	protected int button;
	protected int slot;
	protected NetworkItemStack itemstack;

	@Override
	protected void write() {
		codec.writeServerbound(create(windowId, actionNumber, mode, button, slot, itemstack));
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
