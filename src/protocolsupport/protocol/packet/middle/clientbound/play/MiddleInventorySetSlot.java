package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.types.NetworkItemStack;

public abstract class MiddleInventorySetSlot extends ClientBoundMiddlePacket {

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

	@Override
	public boolean postFromServerRead() {
		return cache.getWindowCache().isValidWindowId(windowId);
	}

}
