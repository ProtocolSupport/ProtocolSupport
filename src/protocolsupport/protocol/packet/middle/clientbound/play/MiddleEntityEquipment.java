package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.types.ItemStackWrapper;

public abstract class MiddleEntityEquipment<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected int slot;
	protected ItemStackWrapper itemstack;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		entityId = serializer.readVarInt();
		slot = serializer.readVarInt();
		itemstack = serializer.readItemStack();
	}

}
