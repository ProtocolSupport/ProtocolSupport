package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import net.minecraft.server.v1_9_R2.ItemStack;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleInventorySetSlot<T> extends ClientBoundMiddlePacket<T> {

	protected int windowId;
	protected int slot;
	protected ItemStack itemstack;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		windowId = serializer.readUnsignedByte();
		slot = serializer.readShort();
		itemstack = serializer.readItemStack();
	}

}
