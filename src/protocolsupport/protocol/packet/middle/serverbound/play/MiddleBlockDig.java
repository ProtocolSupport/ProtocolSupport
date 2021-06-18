package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddleBlockDig extends ServerBoundMiddlePacket {

	protected MiddleBlockDig(MiddlePacketInit init) {
		super(init);
	}

	protected Action status;
	protected final Position position = new Position(0, 0, 0);
	protected int face;

	@Override
	protected void write() {
		codec.writeServerbound(create(status, position, face));
	}

	public static ServerBoundPacketData create(Action status, Position position, int face) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_BLOCK_DIG);
		MiscDataCodec.writeVarIntEnum(creator, status);
		PositionCodec.writePosition(creator, position);
		creator.writeByte(face);
		return creator;
	}

	protected enum Action {
		START_DIG, CANCEL_DIG, FINISH_DIG, DROP_ITEM_ALL, DROP_ITEM_SINGLE, FINISH_USE, SWAP_ITEMS;
		public static final EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookup<>(Action.class);
	}

}
