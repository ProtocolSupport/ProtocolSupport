package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.storage.netcache.window.WindowCache;
import protocolsupport.protocol.types.NetworkItemStack;

public abstract class MiddleInventorySetSlot extends ClientBoundMiddlePacket {

	protected static final byte WINDOW_ID_PLAYER_HOTBAR = 0;
	protected static final byte WINDOW_ID_PLAYER_CURSOR = -1;
	protected static final byte WINDOW_ID_PLAYER_INVENTORY = -2;

	protected final WindowCache windowCache = cache.getWindowCache();

	protected MiddleInventorySetSlot(MiddlePacketInit init) {
		super(init);
	}

	protected byte windowId;
	protected int slot;
	protected NetworkItemStack itemstack;

	@Override
	protected void decode(ByteBuf serverdata) {
		windowId = serverdata.readByte();
		slot = serverdata.readShort();
		itemstack = ItemStackSerializer.readItemStack(serverdata);
	}

}
