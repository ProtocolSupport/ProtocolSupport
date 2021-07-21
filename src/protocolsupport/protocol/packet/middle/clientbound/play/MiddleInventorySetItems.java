package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.window.WindowCache;
import protocolsupport.protocol.types.NetworkItemStack;

public abstract class MiddleInventorySetItems extends ClientBoundMiddlePacket {

	protected MiddleInventorySetItems(MiddlePacketInit init) {
		super(init);
	}

	public static final int WINDOW_ID_PLAYER_INVENTORY = 0;

	protected final WindowCache windowCache = cache.getWindowCache();

	protected byte windowId;
	protected int stateId;
	protected NetworkItemStack[] items;
	protected NetworkItemStack cursor;

	@Override
	protected void decode(ByteBuf serverdata) {
		windowId = serverdata.readByte();
		stateId = VarNumberCodec.readVarInt(serverdata);
		items = ArrayCodec.readVarIntTArray(serverdata, NetworkItemStack.class, ItemStackCodec::readItemStack);
		cursor = ItemStackCodec.readItemStack(serverdata);

		if (!windowCache.isValidWindowId(windowId) && (windowId != WINDOW_ID_PLAYER_INVENTORY)) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

}
