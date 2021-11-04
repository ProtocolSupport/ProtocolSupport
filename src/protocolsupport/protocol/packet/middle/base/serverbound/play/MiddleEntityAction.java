package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddleEntityAction extends ServerBoundMiddlePacket {

	protected MiddleEntityAction(IMiddlePacketInit init) {
		super(init);
	}

	protected int entityId;
	protected Action action;
	protected int jumpBoost;

	@Override
	protected void write() {
		io.writeServerbound(MiddleEntityAction.create(entityId, action, jumpBoost));
	}

	public static ServerBoundPacketData create(int entityId, Action action, int jumpBoost) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_ENTITY_ACTION);
		VarNumberCodec.writeVarInt(creator, entityId);
		MiscDataCodec.writeVarIntEnum(creator, action);
		VarNumberCodec.writeVarInt(creator, jumpBoost);
		return creator;
	}

	protected enum Action {
		START_SNEAK, STOP_SNEAK, LEAVE_BED, START_SPRINT, STOP_SPRINT, START_JUMP, STOP_JUMP, OPEN_HORSE_INV, START_ELYTRA_FLY;
		public static final EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookup<>(Action.class);
	}

}
