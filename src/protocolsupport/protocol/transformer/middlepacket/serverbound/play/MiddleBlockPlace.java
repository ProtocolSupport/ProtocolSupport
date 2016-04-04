package protocolsupport.protocol.transformer.middlepacket.serverbound.play;

import net.minecraft.server.v1_9_R1.BlockPosition;
import net.minecraft.server.v1_9_R1.Packet;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleBlockPlace extends ServerBoundMiddlePacket {

	protected BlockPosition position;
	protected int face;
	protected int usedHand;
	protected int cX;
	protected int cY;
	protected int cZ;

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		if (face != -1) {
			PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_USE_ITEM.get());
			creator.writePosition(position);
			creator.writeVarInt(face);
			creator.writeVarInt(usedHand);
			creator.writeByte(cX);
			creator.writeByte(cY);
			creator.writeByte(cZ);
			return RecyclableSingletonList.create(creator.create());
		} else {
			PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_BLOCK_PLACE.get());
			creator.writeVarInt(usedHand);
			return RecyclableSingletonList.create(creator.create());
		}
	}

}
