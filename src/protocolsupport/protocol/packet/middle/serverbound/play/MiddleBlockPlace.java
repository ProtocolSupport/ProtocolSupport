package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleBlockPlace extends ServerBoundMiddlePacket {

	protected Position position;
	protected int face;
	protected int usedHand;
	protected float cX;
	protected float cY;
	protected float cZ;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		if (face != -1) {
			ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_USE_ITEM);
			creator.writePosition(position);
			creator.writeVarInt(face);
			creator.writeVarInt(usedHand);
			creator.writeFloat(cX);
			creator.writeFloat(cY);
			creator.writeFloat(cZ);
			return RecyclableSingletonList.create(creator);
		} else {
			ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_BLOCK_PLACE);
			creator.writeVarInt(usedHand);
			return RecyclableSingletonList.create(creator);
		}
	}

}
