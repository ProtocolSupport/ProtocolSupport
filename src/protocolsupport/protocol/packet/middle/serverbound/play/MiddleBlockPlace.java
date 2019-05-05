package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.UsedHand;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleBlockPlace extends ServerBoundMiddlePacket {

	public MiddleBlockPlace(ConnectionImpl connection) {
		super(connection);
	}

	protected UsedHand hand;
	protected final Position position = new Position(0, 0, 0);
	protected int face;
	protected float cX;
	protected float cY;
	protected float cZ;
	protected boolean insideblock;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(position, face, hand, cX, cY, cZ, insideblock));
	}

	public static ServerBoundPacketData create(Position position, int face, UsedHand hand, float cX, float cY, float cZ, boolean insideblock) {
		if (face != -1) {
			ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_USE_ITEM);
			MiscSerializer.writeVarIntEnum(creator, hand);
			PositionSerializer.writePosition(creator, position);
			VarNumberSerializer.writeVarInt(creator, face);
			creator.writeFloat(cX);
			creator.writeFloat(cY);
			creator.writeFloat(cZ);
			creator.writeBoolean(insideblock);
			return creator;
		} else {
			ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_BLOCK_PLACE);
			MiscSerializer.writeVarIntEnum(creator, hand);
			return creator;
		}
	}

}
