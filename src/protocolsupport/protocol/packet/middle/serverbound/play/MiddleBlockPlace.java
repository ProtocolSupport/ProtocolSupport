package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleBlockPlace extends ServerBoundMiddlePacket {

	protected Position position;
	protected int face;
	protected int usedHand;
	protected int cX;
	protected int cY;
	protected int cZ;

	@Override
	public RecyclableCollection<PacketCreator> toNative() throws Exception {
		if (face != -1) {
			PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_USE_ITEM);
			creator.writePosition(position);
			creator.writeVarInt(face);
			creator.writeVarInt(usedHand);
			creator.writeByte(cX);
			creator.writeByte(cY);
			creator.writeByte(cZ);
			return RecyclableSingletonList.create(creator);
		} else {
			PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_BLOCK_PLACE);
			creator.writeVarInt(usedHand);
			return RecyclableSingletonList.create(creator);
		}
	}

}
