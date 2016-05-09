package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.server.v1_9_R1.ItemStack;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleInventorySetItems<T> extends ClientBoundMiddlePacket<T> {

	protected int windowId;
	protected ArrayList<ItemStack> itemstacks = new ArrayList<ItemStack>();

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		windowId = serializer.readUnsignedByte();
		int count = serializer.readShort();
		itemstacks.clear();
		for (int i = 0; i < count; i++) {
			itemstacks.add(serializer.readItemStack());
		}
	}

}
