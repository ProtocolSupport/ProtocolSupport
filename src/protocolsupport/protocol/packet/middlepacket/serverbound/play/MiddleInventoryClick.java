package protocolsupport.protocol.packet.middlepacket.serverbound.play;

import net.minecraft.server.v1_9_R1.ItemStack;
import net.minecraft.server.v1_9_R1.Packet;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleInventoryClick extends ServerBoundMiddlePacket {

	protected int windowId;
	protected int slot;
	protected int button;
	protected int actionNumber;
	protected int mode;
	protected ItemStack itemstack;

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_WINDOW_CLICK.get());
		creator.writeByte(windowId);
		creator.writeShort(slot);
		creator.writeByte(button);
		creator.writeShort(actionNumber);
		creator.writeByte(mode);
		creator.writeItemStack(itemstack);
		return RecyclableSingletonList.create(creator.create());
	}

}
