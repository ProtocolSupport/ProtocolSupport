package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.ArrayList;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.types.NetworkItemStack;

public abstract class MiddleInventorySetItems extends ClientBoundMiddlePacket {

	protected static final int WINDOW_ID_PLAYER_INVENTORY = 0;

	public MiddleInventorySetItems(ConnectionImpl connection) {
		super(connection);
	}

	protected int windowId;
	protected ArrayList<NetworkItemStack> itemstacks = new ArrayList<>();

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		windowId = serverdata.readUnsignedByte();
		int count = serverdata.readShort();
		itemstacks.clear();
		for (int i = 0; i < count; i++) {
			itemstacks.add(ItemStackSerializer.readItemStack(serverdata));
		}
	}

	@Override
	public boolean postFromServerRead() {
		return cache.getWindowCache().isValidWindowId(windowId) || (windowId == WINDOW_ID_PLAYER_INVENTORY);
	}

}
