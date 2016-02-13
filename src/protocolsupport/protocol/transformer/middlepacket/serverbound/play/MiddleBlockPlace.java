package protocolsupport.protocol.transformer.middlepacket.serverbound.play;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleBlockPlace extends ServerBoundMiddlePacket {

	protected BlockPosition position;
	protected int face;
	protected ItemStack itemstack;
	protected int cX;
	protected int cY;
	protected int cZ;

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_BLOCK_PLACE.get());
		creator.writePosition(position);
		creator.writeByte(face);
		creator.writeItemStack(itemstack);
		creator.writeByte(cX);
		creator.writeByte(cY);
		creator.writeByte(cZ);
		return RecyclableSingletonList.create(creator.create());
	}

}
