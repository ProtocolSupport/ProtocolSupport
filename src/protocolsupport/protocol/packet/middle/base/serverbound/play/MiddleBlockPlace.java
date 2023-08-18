package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.UsedHand;

public abstract class MiddleBlockPlace extends ServerBoundMiddlePacket {

	protected MiddleBlockPlace(IMiddlePacketInit init) {
		super(init);
	}

	protected UsedHand hand;
	protected final Position position = new Position(0, 0, 0);
	protected int face;
	protected float cX;
	protected float cY;
	protected float cZ;
	protected boolean insideblock;
	protected int sequence;

	@Override
	protected void write() {
		io.writeServerbound(create(position, face, hand, cX, cY, cZ, insideblock, sequence));
	}

	public static ServerBoundPacketData create(Position position, int face, UsedHand hand, float cX, float cY, float cZ, boolean insideblock, int sequence) {
		if (face != -1) {
			ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_USE_ITEM);
			MiscDataCodec.writeVarIntEnum(creator, hand);
			PositionCodec.writePosition(creator, position);
			VarNumberCodec.writeVarInt(creator, face);
			creator.writeFloat(cX);
			creator.writeFloat(cY);
			creator.writeFloat(cZ);
			creator.writeBoolean(insideblock);
			VarNumberCodec.writeVarInt(creator, sequence);
			return creator;
		} else {
			ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_BLOCK_PLACE);
			MiscDataCodec.writeVarIntEnum(creator, hand);
			VarNumberCodec.writeVarInt(creator, sequence);
			return creator;
		}
	}

}
