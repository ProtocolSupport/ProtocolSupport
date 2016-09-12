package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.types.ItemStackWrapper;

public abstract class MiddleInventorySetSlot<T> extends ClientBoundMiddlePacket<T> {

	protected int windowId;
	protected int slot;
	protected ItemStackWrapper itemstack;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		windowId = serializer.readUnsignedByte();
		slot = serializer.readShort();
		itemstack = serializer.readItemStack();
	}

}
