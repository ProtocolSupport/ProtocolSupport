package protocolsupport.protocol.packet.middle.serverbound.play;

import net.minecraft.server.v1_9_R2.BlockPosition;
import net.minecraft.server.v1_9_R2.Packet;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
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
