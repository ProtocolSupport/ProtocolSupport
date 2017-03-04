package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public abstract class MiddleEntityEquipment extends ClientBoundMiddlePacket {

	protected int entityId;
	protected int slot;
	protected ItemStackWrapper itemstack;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		entityId = serializer.readVarInt();
		slot = serializer.readVarInt();
		itemstack = serializer.readItemStack();
	}

}
