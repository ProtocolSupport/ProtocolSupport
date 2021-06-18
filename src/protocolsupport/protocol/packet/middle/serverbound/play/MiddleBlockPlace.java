package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
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
			ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_USE_ITEM);
			MiscDataCodec.writeVarIntEnum(creator, hand);
			PositionCodec.writePosition(creator, position);
			VarNumberCodec.writeVarInt(creator, face);
			creator.writeFloat(cX);
			creator.writeFloat(cY);
			creator.writeFloat(cZ);
			creator.writeBoolean(insideblock);
			return creator;
		} else {
			ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_BLOCK_PLACE);
			MiscDataCodec.writeVarIntEnum(creator, hand);
			return creator;
		}
	}

}
