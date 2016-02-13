package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.ItemStack;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleEntityEquipment<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected int slot;
	protected ItemStack itemstack;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		entityId = serializer.readVarInt();
		slot = serializer.readShort();
		itemstack = serializer.readItemStack();
	}

}
