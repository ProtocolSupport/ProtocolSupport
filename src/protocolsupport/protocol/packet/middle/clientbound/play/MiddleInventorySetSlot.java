package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public abstract class MiddleInventorySetSlot extends ClientBoundMiddlePacket {

	protected int windowId;
	protected int slot;
	protected NetworkItemStack itemstack;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		windowId = serverdata.readUnsignedByte();
		slot = serverdata.readShort();
		itemstack = ItemStackSerializer.readItemStack(serverdata, ProtocolVersionsHelper.LATEST_PC, cache.getAttributesCache().getLocale(), false);
	}

}
