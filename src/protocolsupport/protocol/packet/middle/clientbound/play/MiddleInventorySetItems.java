package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.ArrayList;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public abstract class MiddleInventorySetItems extends ClientBoundMiddlePacket {

	protected int windowId;
	protected ArrayList<NetworkItemStack> itemstacks = new ArrayList<>();

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		windowId = serverdata.readUnsignedByte();
		int count = serverdata.readShort();
		itemstacks.clear();
		for (int i = 0; i < count; i++) {
			itemstacks.add(ItemStackSerializer.readItemStack(serverdata, ProtocolVersionsHelper.LATEST_PC, cache.getAttributesCache().getLocale(), false));
		}
	}

}
