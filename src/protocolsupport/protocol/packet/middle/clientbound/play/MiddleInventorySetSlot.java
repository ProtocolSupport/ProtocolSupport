package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.types.NetworkItemStack;

public abstract class MiddleInventorySetSlot extends ClientBoundMiddlePacket {

	protected static final int WINDOW_ID_PLAYER_HOTBAR = 0;
	protected static final int WINDOW_ID_PLAYER_CURSOR = 255;
	protected static final int WINDOW_ID_PLAYER_INVENTORY = 254;

	public MiddleInventorySetSlot(ConnectionImpl connection) {
		super(connection);
	}

	protected int windowId;
	protected int slot;
	protected NetworkItemStack itemstack;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		windowId = serverdata.readUnsignedByte();
		slot = serverdata.readShort();
		itemstack = ItemStackSerializer.readItemStack(serverdata);
	}

}
