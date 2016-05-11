package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import net.minecraft.server.v1_9_R2.ItemStack;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public abstract class MiddleEntityEquipment<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected int slot;
	protected ItemStack itemstack;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		entityId = serializer.readVarInt();
		slot = serializer.readVarInt();
		itemstack = serializer.readItemStack();
	}

}
