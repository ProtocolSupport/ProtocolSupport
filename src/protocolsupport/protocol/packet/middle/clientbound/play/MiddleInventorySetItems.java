package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.storage.netcache.window.WindowCache;
import protocolsupport.protocol.types.NetworkItemStack;

public abstract class MiddleInventorySetItems extends ClientBoundMiddlePacket {

	protected static final int WINDOW_ID_PLAYER_INVENTORY = 0;

	protected final WindowCache windowCache = cache.getWindowCache();

	protected MiddleInventorySetItems(MiddlePacketInit init) {
		super(init);
	}

	protected byte windowId;
	protected NetworkItemStack[] items;

	@Override
	protected void decode(ByteBuf serverdata) {
		windowId = serverdata.readByte();
		items = ArraySerializer.readShortTArray(serverdata, NetworkItemStack.class, ItemStackSerializer::readItemStack);

		if (!windowCache.isValidWindowId(windowId) && (windowId != WINDOW_ID_PLAYER_INVENTORY)) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

}
