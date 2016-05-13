package protocolsupport.protocol.packet.middle.serverbound.play;

import net.minecraft.server.v1_9_R2.ItemStack;
import net.minecraft.server.v1_9_R2.Packet;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleCreativeSetSlot extends ServerBoundMiddlePacket {

	protected int slot;
	protected ItemStack itemstack;

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_CREATIVE_SET_SLOT.get());
		creator.writeShort(slot);
		creator.writeItemStack(itemstack);
		return RecyclableSingletonList.create(creator.create());
	}

}
