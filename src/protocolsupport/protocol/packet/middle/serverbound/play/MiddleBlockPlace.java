package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.UsedHand;

public abstract class MiddleBlockPlace extends ServerBoundMiddlePacket {

	protected MiddleBlockPlace(MiddlePacketInit init) {
		super(init);
	}

	protected UsedHand hand;
	protected final Position position = new Position(0, 0, 0);
	protected int face;
	protected float cX;
	protected float cY;
	protected float cZ;
	protected boolean insideblock;

	@Override
	protected void write() {
		codec.writeServerbound(create(position, face, hand, cX, cY, cZ, insideblock));
	}

	public static ServerBoundPacketData create(Position position, int face, UsedHand hand, float cX, float cY, float cZ, boolean insideblock) {
		if (face != -1) {
			ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.SERVERBOUND_PLAY_USE_ITEM);
			MiscSerializer.writeVarIntEnum(creator, hand);
			PositionSerializer.writePosition(creator, position);
			VarNumberSerializer.writeVarInt(creator, face);
			creator.writeFloat(cX);
			creator.writeFloat(cY);
			creator.writeFloat(cZ);
			creator.writeBoolean(insideblock);
			return creator;
		} else {
			ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.SERVERBOUND_PLAY_BLOCK_PLACE);
			MiscSerializer.writeVarIntEnum(creator, hand);
			return creator;
		}
	}

}
