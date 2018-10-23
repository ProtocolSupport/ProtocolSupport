package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.UsedHand;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleBlockPlace extends ServerBoundMiddlePacket {

	public MiddleBlockPlace(ConnectionImpl connection) {
		super(connection);
	}

	protected final Position position = new Position(0, 0, 0);
	protected int face;
	protected UsedHand hand;
	protected float cX;
	protected float cY;
	protected float cZ;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(position, face, hand, cX, cY, cZ));
	}

	public static ServerBoundPacketData create(Position position, int face, UsedHand hand, float cX, float cY, float cZ) {
		if (face != -1) {
			ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_USE_ITEM);
			PositionSerializer.writePosition(creator, position);
			VarNumberSerializer.writeVarInt(creator, face);
			MiscSerializer.writeVarIntEnum(creator, hand);
			creator.writeFloat(cX);
			creator.writeFloat(cY);
			creator.writeFloat(cZ);
			return creator;
		} else {
			ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_BLOCK_PLACE);
			MiscSerializer.writeVarIntEnum(creator, hand);
			return creator;
		}
	}

}
