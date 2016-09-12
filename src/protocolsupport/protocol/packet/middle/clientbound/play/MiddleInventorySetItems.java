package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;
import java.util.ArrayList;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.types.ItemStackWrapper;

public abstract class MiddleInventorySetItems<T> extends ClientBoundMiddlePacket<T> {

	protected int windowId;
	protected ArrayList<ItemStackWrapper> itemstacks = new ArrayList<>();

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		windowId = serializer.readUnsignedByte();
		int count = serializer.readShort();
		itemstacks.clear();
		for (int i = 0; i < count; i++) {
			itemstacks.add(serializer.readItemStack());
		}
	}

}
