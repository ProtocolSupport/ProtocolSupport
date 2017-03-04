package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public abstract class MiddleInventorySetSlot extends ClientBoundMiddlePacket {

	protected int windowId;
	protected int slot;
	protected ItemStackWrapper itemstack;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		windowId = serverdata.readUnsignedByte();
		slot = serverdata.readShort();
		itemstack = ItemStackSerializer.readItemStack(serverdata, ProtocolVersion.getLatest());
	}

}
