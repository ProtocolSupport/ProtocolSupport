package protocolsupport.protocol.transformer.middlepacket.serverbound.play;

import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketCreator;
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
