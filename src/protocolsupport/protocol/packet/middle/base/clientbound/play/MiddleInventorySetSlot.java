package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.window.WindowCache;
import protocolsupport.protocol.types.NetworkItemStack;

public abstract class MiddleInventorySetSlot extends ClientBoundMiddlePacket {

	protected MiddleInventorySetSlot(IMiddlePacketInit init) {
		super(init);
	}

	public static final byte WINDOW_ID_PLAYER_HOTBAR = 0;
	public static final byte WINDOW_ID_PLAYER_CURSOR = -1;
	public static final byte WINDOW_ID_PLAYER_INVENTORY = -2;

	protected final WindowCache windowCache = cache.getWindowCache();

	protected byte windowId;
	protected int stateId;
	protected int slot;
	protected NetworkItemStack itemstack;

	@Override
	protected void decode(ByteBuf serverdata) {
		windowId = serverdata.readByte();
		stateId = VarNumberCodec.readVarInt(serverdata);
		slot = serverdata.readShort();
		itemstack = ItemStackCodec.readItemStack(serverdata);

		if (
			!windowCache.isValidWindowId(windowId) &&
			(windowId != WINDOW_ID_PLAYER_INVENTORY) &&
			(windowId != WINDOW_ID_PLAYER_CURSOR) &&
			(!((windowId == WINDOW_ID_PLAYER_HOTBAR) && (slot >= 36) && (slot < 45)))
		) {
			throw MiddlePacketCancelException.INSTANCE;
		}
	}

}
