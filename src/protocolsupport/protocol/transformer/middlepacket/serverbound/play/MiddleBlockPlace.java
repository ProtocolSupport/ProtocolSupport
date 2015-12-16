package protocolsupport.protocol.transformer.middlepacket.serverbound.play;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;

import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.utils.PacketCreator;

public abstract class MiddleBlockPlace extends ServerBoundMiddlePacket {

	protected BlockPosition position;
	protected int face;
	protected ItemStack itemstack;
	protected int cX;
	protected int cY;
	protected int cZ;

	@Override
	public Collection<Packet<?>> toNative() throws Exception {
		PacketCreator creator = new PacketCreator(ServerBoundPacket.PLAY_BLOCK_PLACE.get());
		creator.writePosition(position);
		creator.writeByte(face);
		creator.writeItemStack(itemstack);
		creator.writeByte(cX);
		creator.writeByte(cY);
		creator.writeByte(cZ);
		return Collections.<Packet<?>>singletonList(creator.create());
	}

}
