package protocolsupport.protocol.transformer.middlepacket.serverbound.play;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketCreator;
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
