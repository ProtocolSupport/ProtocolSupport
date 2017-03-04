package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public abstract class MiddleInventorySetSlot extends ClientBoundMiddlePacket {

	protected int windowId;
	protected int slot;
	protected ItemStackWrapper itemstack;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		windowId = serializer.readUnsignedByte();
		slot = serializer.readShort();
		itemstack = serializer.readItemStack();
	}

}
