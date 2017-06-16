package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
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
			PositionSerializer.writePosition(creator, position);
			VarNumberSerializer.writeVarInt(creator, face);
			VarNumberSerializer.writeVarInt(creator, usedHand);
			creator.writeFloat(cX);
			creator.writeFloat(cY);
			creator.writeFloat(cZ);
			return RecyclableSingletonList.create(creator);
		} else {
			ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_BLOCK_PLACE);
			VarNumberSerializer.writeVarInt(creator, usedHand);
			return RecyclableSingletonList.create(creator);
		}
	}

}
