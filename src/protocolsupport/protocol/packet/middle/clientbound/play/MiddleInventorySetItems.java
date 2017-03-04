package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.ArrayList;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public abstract class MiddleInventorySetItems extends ClientBoundMiddlePacket {

	protected int windowId;
	protected ArrayList<ItemStackWrapper> itemstacks = new ArrayList<>();

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		windowId = serverdata.readUnsignedByte();
		int count = serverdata.readShort();
		itemstacks.clear();
		for (int i = 0; i < count; i++) {
			itemstacks.add(ItemStackSerializer.readItemStack(serverdata, ProtocolVersion.getLatest()));
		}
	}

}
