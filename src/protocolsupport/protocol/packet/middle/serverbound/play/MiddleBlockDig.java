package protocolsupport.protocol.packet.middle.serverbound.play;

import net.minecraft.server.v1_9_R2.BlockPosition;
import net.minecraft.server.v1_9_R2.Packet;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleBlockDig extends ServerBoundMiddlePacket {

	protected int status;
	protected BlockPosition position;
	protected int face;

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_BLOCK_DIG.get());
		creator.writeByte(status);
		creator.writePosition(position);
		creator.writeByte(face);
		return RecyclableSingletonList.create(creator.create());
	}

}
